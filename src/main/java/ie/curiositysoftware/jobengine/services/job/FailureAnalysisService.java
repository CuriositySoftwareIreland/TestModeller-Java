package ie.curiositysoftware.jobengine.services.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.curiositysoftware.jobengine.dto.job.RunResultAnalysisJobResult;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.utils.JobExecutor;
import ie.curiositysoftware.utils.RestService;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joor.Reflect;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FailureAnalysisService extends RestService {

    private final TestGenerationService testGenerationService;
    private final CodeGenerationService codeGenerationService;
    private final JobExecutor jobExecutor;
    private final ObjectMapper mapper = new ObjectMapper();

    private Long jobTimeout;
    private String serverName;
    private Long templateId;

    private List<Class<?>> newTests;

    public FailureAnalysisService(ConnectionProfile profile) {
        super(profile);
        testGenerationService = new TestGenerationService(profile);
        codeGenerationService = new CodeGenerationService(profile);
        jobExecutor = new JobExecutor(profile);
    }

    public FailureAnalysisService(ConnectionProfile profile, Long jobTimeout, String serverName, Long templateId) {
        this(profile);
        this.jobTimeout = jobTimeout;
        this.serverName = serverName;
        this.templateId = templateId;
    }

    public Long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Boolean analyseFailures(Long profileId) {
        if(getServerName() == null || getServerName().isEmpty()) {
            this.errorMessage = "No server name provided";
            return false;
        }

        if(getTemplateId() == null || getTemplateId() <= 0) {
            this.errorMessage = "No template ID provided";
            return false;
        }

        try {
            Long testGenJob = testGenerationService.startAnalysisAndGenerationJob(profileId);
            if(testGenJob == null) {
                this.errorMessage = "Test Generation failed: " + testGenerationService.getErrorMessage();
                return false;
            }

            String testGenOutputFile = jobResultPath(testGenJob).toString();
            if (!jobExecutor.waitForJob(testGenJob, testGenOutputFile, getJobTimeout())) {
                this.errorMessage = "Test Generation failed: " + jobExecutor.getErrorMessage();
                return false;
            }

            String resultJson = FileUtils.readFileToString(new File(testGenOutputFile), StandardCharsets.UTF_8);
            RunResultAnalysisJobResult result = mapper.readValue(resultJson, RunResultAnalysisJobResult.class);
            Long codeGenJob = codeGenerationService.startTestCaseCodeGenerationJob(result.getNewSuiteId(), getServerName(), getTemplateId());
            if(codeGenJob == null) {
                this.errorMessage = "Code Generation failed: " + codeGenerationService.getErrorMessage();
                return false;
            }

            Path codeGenOutputFile = jobResultPath(codeGenJob);
            if (!jobExecutor.waitForJob(codeGenJob, codeGenOutputFile.toString(), getJobTimeout())) {
                this.errorMessage = "Code Generation failed: " + jobExecutor.getErrorMessage();
                return false;
            }

            newTests = newTestsFromResultFile(codeGenOutputFile);
        } catch (IOException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Path jobResultPath(Long jobId) {
        return Paths.get(System.getProperty("user.home"), ".testmodeller", "jobresults", jobId + ".result");
    }

    private List<Class<?>> newTestsFromResultFile(Path resultFile) throws IOException {
        String resultFileName = FilenameUtils.removeExtension(resultFile.getFileName().toString());
        ZipFile zipFile = new ZipFile(resultFile.toString());
        String destDir = Paths.get(resultFile.getParent().toString(), resultFileName).toString();
        try {
            zipFile.extractAll(destDir);
        } catch (ZipException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        List<Class<?>> tests = new ArrayList<>();
        for(final File javaFile : Objects.requireNonNull(Paths.get(destDir, "code").toFile().listFiles())) {
            if(FilenameUtils.getExtension(javaFile.getName()).equals("java")) {
                String content = new String(Files.readAllBytes(javaFile.toPath()), StandardCharsets.UTF_8);
                String className = parseQualifiedClassName(content, FilenameUtils.removeExtension(javaFile.getName()));
                Class<?> test = Reflect.compile(className, content).type();
                tests.add(test);
            }
        }
        return tests;
    }

    private String parseQualifiedClassName(String fileContent, String className) {
        Pattern pattern = Pattern.compile("\\bpackage\\s+((?:\\w|_|$|.)+);");
        Matcher matcher = pattern.matcher(fileContent);
        String packagePrefix = matcher.find() ? matcher.group(1) + "." : "";
        return packagePrefix + className;
    }

    public List<Class<?>> getNewTests() {
        return newTests;
    }
}
