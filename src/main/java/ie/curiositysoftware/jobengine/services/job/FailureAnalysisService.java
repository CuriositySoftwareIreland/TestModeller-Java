package ie.curiositysoftware.jobengine.services.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.curiositysoftware.jobengine.dto.job.RunResultAnalysisJobResult;
import ie.curiositysoftware.jobengine.dto.job.TestCoverageEnum;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.utils.JobExecutor;
import ie.curiositysoftware.utils.RestService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joor.Reflect;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FailureAnalysisService extends RestService {

    private final TestGenerationService testGenerationService;
    private final CodeGenerationService codeGenerationService;
    private final JobExecutor jobExecutor;
    private final ObjectMapper mapper = new ObjectMapper();

    private Long jobTimeout = 60000L;
    private String serverName;
    private Long templateId = CodeGenerationService.DEFAULT_JAVA_TEMPLATE_ID;
    private Boolean includeOldTests = true;
    private TestCoverageEnum targetCoverage = TestCoverageEnum.Exhaustive;

    private List<Class<?>> newTests;

    public FailureAnalysisService(ConnectionProfile profile) {
        super(profile);
        testGenerationService = new TestGenerationService(profile);
        codeGenerationService = new CodeGenerationService(profile);
        jobExecutor = new JobExecutor(profile);
    }

    public FailureAnalysisService(ConnectionProfile profile, Long jobTimeout, String serverName, Long templateId,
                                  Boolean includeOldTests, TestCoverageEnum targetCoverage) {
        this(profile);
        this.jobTimeout = jobTimeout;
        this.serverName = serverName;
        this.templateId = templateId;
        this.includeOldTests = includeOldTests;
        this.targetCoverage = targetCoverage;
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

    public Boolean getIncludeOldTests() {
        return includeOldTests;
    }

    public TestCoverageEnum getTargetCoverage() {
        return targetCoverage;
    }

    public void setTargetCoverage(TestCoverageEnum targetCoverage) {
        this.targetCoverage = targetCoverage;
    }

    public void setIncludeOldTests(Boolean includeOldTests) {
        this.includeOldTests = includeOldTests;
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
            Long testGenJob = testGenerationService.startAnalysisAndGenerationJob(profileId, getIncludeOldTests(), null, getTargetCoverage());
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
        String destDir = Paths.get(resultFile.getParent().toString(), resultFileName).toString();

        // Create the destination directory if it doesn't exist
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdirs();
        }

        List<File> extractedFiles = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(resultFile.toFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(destDir, entry.getName());

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    File parentDir = entryFile.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }

                    try (FileOutputStream fos = new FileOutputStream(entryFile)) {
                        Files.copy(zipFile.getInputStream(entry), entryFile.toPath());
                    }
                    extractedFiles.add(entryFile);
                }
            }
        } catch (IOException e) {
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


    public static void extract(String zipFilePath, String destinationDir) throws IOException {
        File destDir = new File(destinationDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File entryDestination = new File(destinationDir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(entryDestination)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    public List<Class<?>> getNewTests() {
        return newTests;
    }
}
