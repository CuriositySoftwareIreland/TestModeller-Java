package com.curiosity.JobEngine.Utils;

import com.curiosity.JobEngine.Entities.Job.JobEntity;
import com.curiosity.JobEngine.Entities.Job.JobResultEntity;
import com.curiosity.JobEngine.Entities.Job.JobState;
import com.curiosity.JobEngine.Services.ConnectionProfile;
import com.curiosity.JobEngine.Services.Job.JobResultService;
import com.curiosity.JobEngine.Services.Job.JobSubmissionService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JobExecutor {
    private String ErrorMessage;

    public JobExecutor()
    {
        ErrorMessage = "";
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public Boolean ExecuteJob(JobEntity job, ConnectionProfile p, String outputLocation, Long maxTime) throws IOException {
        JobSubmissionService jobSubmission = new JobSubmissionService(p);
        JobEntity r = jobSubmission.AddJob(job);
        if (r == null) {
            ErrorMessage = "Error submiting job - " + jobSubmission.GetErrorMessage();

            return false;
        }

        // 5 - Wait for job to complete
        long startTime = System.currentTimeMillis();
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > maxTime) {
                ErrorMessage = "Maximum time elapsed";

                return false;
            }

            JobEntity curJobStatus = jobSubmission.GetJob(r.getId());
            if (curJobStatus == null)
                break;

            if (curJobStatus.getJobState().equals(JobState.Complete))
            {
                ErrorMessage = "Job complete";

                break;
            } else if (curJobStatus.getJobState().equals(JobState.Error)) {
                ErrorMessage = "Error executing job " + curJobStatus.getProgressMessage();

                return false;
            }

            ErrorMessage = "Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 6 - Retrieve result
        JobResultService jobResult = new JobResultService(p);
        JobResultEntity jobResultEntity = jobResult.GetResult(r.getId());
        if (jobResultEntity == null) {
            ErrorMessage = "Error retrieving result";

            return false;
        }

        // 7 - If result file download it to location
        FileUtils.writeByteArrayToFile(new File(outputLocation), jobResultEntity.getResultObject());

        return true;
    }
}
