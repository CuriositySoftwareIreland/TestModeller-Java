package ie.curiositysoftware.testdata;

import ie.curiositysoftware.jobengine.dto.job.AutomationExecutionParameter;
import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobState;
import ie.curiositysoftware.jobengine.dto.job.JobType;
import ie.curiositysoftware.jobengine.dto.job.settings.RunResultAnalysisJobSettings;
import ie.curiositysoftware.jobengine.dto.job.settings.VIPAutomationExecutionJobSettings;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.job.JobResultService;
import ie.curiositysoftware.jobengine.services.job.JobSubmissionService;
import ie.curiositysoftware.utils.UnirestHelper;

public class TestDataResolver {
    private String m_ErrorMessage;

    private ConnectionProfile m_ConnectionProfile;

    public TestDataResolver(ConnectionProfile p)
    {
        m_ConnectionProfile = p;

        UnirestHelper.initUnirestMapper();
    }

    public String ResolveTestDataValue(String expression)
    {
        JobSubmissionService jobSubmission = new JobSubmissionService(m_ConnectionProfile);

        Job job = new Job();
        job.setJobType(JobType.VIPAutoExecutionJob);

        VIPAutomationExecutionJobSettings vipAutomationExecutionJobSettings = new VIPAutomationExecutionJobSettings();
        vipAutomationExecutionJobSettings.setMachineKey("DockerJobEngine");
        vipAutomationExecutionJobSettings.setSharedJobServer(false);
        vipAutomationExecutionJobSettings.setAutomationType("PreviewDataResult");
        job.setVipAutomationJobSettings(vipAutomationExecutionJobSettings);

        AutomationExecutionParameter parDataValue = new AutomationExecutionParameter();
        parDataValue.setVar("parDataValue");
        parDataValue.setValue(expression);
        parDataValue.setParamIndex(1);
        vipAutomationExecutionJobSettings.getAutomationParameters().add(parDataValue);

        Job savedJob = jobSubmission.addJob(job);

        Long jobId = savedJob.getId();
        // Wait for job to finish and complete
        long startTime = System.currentTimeMillis();

        // Two minutes maximum
        long twoMinutes = 120000;
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > twoMinutes) {
                m_ErrorMessage = "Maximum time elapsed";

                return m_ErrorMessage;
            }

            savedJob = jobSubmission.getJob(jobId);

            if (savedJob == null)
                break;

            if (savedJob.getJobState().equals(JobState.Complete)){
                System.out.println("Executing job - State: " + savedJob.getJobState() + " - Message: " + savedJob.getProgressMessage());

                JobResultService jobResultService = new JobResultService(m_ConnectionProfile);
                return jobResultService.getResult(savedJob.getId()).getResultValue();

            } else if (savedJob.getJobState().equals(JobState.Error)) {
                m_ErrorMessage = "Error executing job " + savedJob.getProgressMessage();

                return m_ErrorMessage;
            }

            System.out.println("Executing job - State: " + savedJob.getJobState() + " - Message: " + savedJob.getProgressMessage());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
