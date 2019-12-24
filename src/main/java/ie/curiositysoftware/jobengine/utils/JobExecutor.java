package ie.curiositysoftware.jobengine.utils;

import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobResult;
import ie.curiositysoftware.jobengine.dto.job.JobState;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.job.JobResultService;
import ie.curiositysoftware.jobengine.services.job.JobSubmissionService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JobExecutor {

    private String errorMessage = "";
    private JobSubmissionService jobSubmissionService;
    private JobResultService jobResultService;

    public JobExecutor(ConnectionProfile profile) {
        this.jobSubmissionService = new JobSubmissionService(profile);
        this.jobResultService = new JobResultService(profile);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean executeJob(Job job, Long maxTime) throws IOException {
        return executeJob(job, null, maxTime);
    }

    public Boolean executeJob(Job job, String outputLocation, Long maxTime) throws IOException {
        Job r = jobSubmissionService.addJob(job);
        if (r == null || r.getId() == null) {
            errorMessage = "Error submiting job - " + jobSubmissionService.getErrorMessage();

            return false;
        }

        return waitForJob(r.getId(), outputLocation, maxTime);
    }

    public Boolean waitForJob(Long jobId, Long maxTime) throws IOException {
        return waitForJob(jobId, null, maxTime);
    }

    public Boolean waitForJob(Long jobId, String outputLocation, Long maxTime) throws IOException {
        // 5 - Wait for job to complete
        long startTime = System.currentTimeMillis();
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > maxTime) {
                errorMessage = "Maximum time elapsed";
                System.out.println(errorMessage);

                return false;
            }

            Job curJobStatus = jobSubmissionService.getJob(jobId);
            if (curJobStatus == null)
                break;

            if (curJobStatus.getJobState().equals(JobState.Complete))
            {
                errorMessage = "Job complete";
                System.out.println(errorMessage);

                break;
            } else if (curJobStatus.getJobState().equals(JobState.Error)) {
                errorMessage = "Error executing job " + curJobStatus.getProgressMessage();
                System.out.println(errorMessage);

                return false;
            }

            errorMessage = "Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage();
            System.out.println(errorMessage);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(outputLocation != null) {
            // 6 - Retrieve result
            JobResult jobResult = jobResultService.getResult(jobId);
            if (jobResult == null) {
                errorMessage = "Error retrieving result";
                System.out.println(errorMessage);

                return false;
            }

            // 7 - If result file download it to location
            FileUtils.writeByteArrayToFile(new File(outputLocation), jobResult.getResultObject());
        }

        return true;
    }
}
