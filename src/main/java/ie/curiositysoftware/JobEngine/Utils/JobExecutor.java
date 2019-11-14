package ie.curiositysoftware.JobEngine.Utils;

import ie.curiositysoftware.JobEngine.Entities.Job.JobEntity;
import ie.curiositysoftware.JobEngine.Entities.Job.JobResultEntity;
import ie.curiositysoftware.JobEngine.Entities.Job.JobState;
import ie.curiositysoftware.JobEngine.Services.ConnectionProfile;
import ie.curiositysoftware.JobEngine.Services.Job.JobResultService;
import ie.curiositysoftware.JobEngine.Services.Job.JobSubmissionService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JobExecutor {

    private String errorMessage = "";
    private ConnectionProfile connectionProfile;
    private JobSubmissionService jobSubmissionService;

    @Deprecated
    public JobExecutor()
    {
    }

    public JobExecutor(ConnectionProfile connectionProfile)
    {
        setConnectionProfile(connectionProfile);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setConnectionProfile(ConnectionProfile profile){
        this.connectionProfile = profile;
        this.jobSubmissionService = new JobSubmissionService(profile);
    }

    @Deprecated
    public Boolean ExecuteJob(JobEntity job, ConnectionProfile p, String outputLocation, Long maxTime) throws IOException {
        setConnectionProfile(p);
        return ExecuteJob(job, outputLocation, maxTime);
    }

    public Boolean ExecuteJob(JobEntity job, String outputLocation, Long maxTime) throws IOException {
        JobEntity r = jobSubmissionService.AddJob(job);
        if (r == null || r.getId() == null) {
            errorMessage = "Error submiting job - " + jobSubmissionService.GetErrorMessage();

            return false;
        }

        return WaitForJob(r.getId(), outputLocation, maxTime);
    }

    public Boolean WaitForJob(Long jobId, String outputLocation, Long maxTime) throws IOException {
        // 5 - Wait for job to complete
        long startTime = System.currentTimeMillis();
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > maxTime) {
                errorMessage = "Maximum time elapsed";

                return false;
            }

            JobEntity curJobStatus = jobSubmissionService.GetJob(jobId);
            if (curJobStatus == null)
                break;

            if (curJobStatus.getJobState().equals(JobState.Complete))
            {
                errorMessage = "Job complete";

                break;
            } else if (curJobStatus.getJobState().equals(JobState.Error)) {
                errorMessage = "Error executing job " + curJobStatus.getProgressMessage();

                return false;
            }

            errorMessage = "Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 6 - Retrieve result
        JobResultService jobResult = new JobResultService(connectionProfile);
        JobResultEntity jobResultEntity = jobResult.GetResult(jobId);
        if (jobResultEntity == null) {
            errorMessage = "Error retrieving result";

            return false;
        }

        // 7 - If result file download it to location
        FileUtils.writeByteArrayToFile(new File(outputLocation), jobResultEntity.getResultObject());

        return true;
    }
}
