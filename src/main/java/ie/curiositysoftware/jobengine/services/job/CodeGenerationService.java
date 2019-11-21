package ie.curiositysoftware.jobengine.services.job;

import ie.curiositysoftware.jobengine.dto.job.AutomationExecutionParameter;
import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobType;
import ie.curiositysoftware.jobengine.dto.job.settings.VIPAutomationExecutionJobSettings;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.RestService;

public class CodeGenerationService extends RestService {

    private final JobSubmissionService jobService;
    private static final String TEMPLATE_PROCESS_NAME = "CreateTests";
    private static final String TEMPLATE_ID_PARAM_NAME = "parCodeGenTemplateId";
    public static final Long DEFAULT_JAVA_TEMPLATE_ID = 2L;

    public CodeGenerationService(ConnectionProfile connectionProfile) {
        super(connectionProfile);
        jobService = new JobSubmissionService(connectionProfile);
    }

    public Long startTestCaseCodeGenerationJob(Long suiteId, String serverName) {
        return startTestCaseCodeGenerationJob(suiteId, serverName, DEFAULT_JAVA_TEMPLATE_ID);
    }

    public Long startTestCaseCodeGenerationJob(Long suiteId, String serverName, Long templateId) {
        Job job = new Job();
        job.setJobType(JobType.VIPAutoExecutionJob);
        job.setVipAutomationJobSettings(new VIPAutomationExecutionJobSettings());
        job.getVipAutomationJobSettings().setTestSuiteId(suiteId);
        job.getVipAutomationJobSettings().setMachineKey(serverName);
        job.getVipAutomationJobSettings().setAutomationType(TEMPLATE_PROCESS_NAME);
        job.getVipAutomationJobSettings().getAutomationParameters().add(createTemplateIdParam(templateId));

        Job saved = jobService.addJob(job);
        return saved == null ? null : saved.getId();
    }

    @Override
    public String getErrorMessage() {
        return jobService.getErrorMessage();
    }

    private AutomationExecutionParameter createTemplateIdParam(Long templateId) {
        AutomationExecutionParameter templateIdParam = new AutomationExecutionParameter();
        templateIdParam.setParamIndex(1);
        templateIdParam.setVar(TEMPLATE_ID_PARAM_NAME);
        templateIdParam.setValue(templateId.toString());
        return templateIdParam;
    }
}
