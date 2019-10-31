package ie.curiositysoftware.DataAllocation.Engine;

import ie.curiositysoftware.DataAllocation.Entities.AllocationType;
import ie.curiositysoftware.DataAllocation.Entities.DataAllocationResult;
import ie.curiositysoftware.JobEngine.Entities.Job.JobEntity;
import ie.curiositysoftware.JobEngine.Entities.Job.JobState;
import ie.curiositysoftware.JobEngine.Services.ConnectionProfile;
import ie.curiositysoftware.JobEngine.Services.Job.JobSubmissionService;
import ie.curiositysoftware.JobEngine.Utils.UnirestHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Perform data resolution and retrieve data results
*/
public class DataAllocationEngine {
    private String m_ErrorMessage;

    private Boolean failOnError;

    private ConnectionProfile m_ConnectionProfile;

    public DataAllocationEngine(ConnectionProfile p)
    {
        m_ConnectionProfile = p;

        failOnError = true;

        UnirestHelper.InitUnirestMapper();
    }

    /**
     * Set failure on data resolution error
     * @param failOnError fail on error flag
     */
    public void setFailOnError(Boolean failOnError) {
        this.failOnError = failOnError;
    }

    /**
     * Get failure on data resolution error
     * @return failure on data resolution
     */
    public Boolean getFailOnError() {
        return failOnError;
    }

    /**
     * Get error message
     * @return error message
     */
    public String getErrorMessage() {
        return m_ErrorMessage;
    }

    /**
     * Resolve specified tests within data pools on specified server
     * @param serverName server to use for performing resolution
     * @param allocationTypes allocations to perform
     * @return success or failure
     */
    public Boolean ResolvePools(String serverName, List<AllocationType> allocationTypes)
    {
        return ResolvePools(serverName, allocationTypes, 1000000000L);
    }

    /**
     * Resolve specified tests within data pools on specified server
     * @param serverName server to use for performing resolution
     * @param allocationTypes allocations to perform
     * @param maxTime maximum time in milliseconds to wait for resolution
     * @return success or failure
     */
    public Boolean ResolvePools(String serverName, List<AllocationType> allocationTypes, Long maxTime)
    {
        // Chunk this into each pool ID
        HashMap<String, List<AllocationType>> allocationPoolsToResolve = new HashMap<String, List<AllocationType>>();
        for (AllocationType allocType: allocationTypes) {
            if (!allocationPoolsToResolve.containsKey(allocType.getPoolName()))
                allocationPoolsToResolve.put(allocType.getPoolName(), new ArrayList<AllocationType>());

            allocationPoolsToResolve.get(allocType.getPoolName()).add(allocType);
        }

        for (String poolName : allocationPoolsToResolve.keySet()) {
            if (!performAllocation(serverName, poolName, allocationPoolsToResolve.get(poolName), maxTime))
                if (failOnError)
                    return false;
        }

        return true;
    }

    /**
     * Retrieve data allocation result for test name within a test suite and data pool
     * @param pool pool to use for resolution
     * @param suite suite to use for resolution
     * @param testName test name of data to retrieve
     * @return allocated result
     */
    public DataAllocationResult GetDataResult(String pool, String suite, String testName)
    {
        try {
            HttpResponse<DataAllocationResult> postResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/allocation-pool/" + pool + "/suite/" + suite + "/allocated-test/" + testName + "/result/value")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asObject(DataAllocationResult.class);

            if (postResponse.getStatus() == 200) {
                return postResponse.getBody();
            } else {
                m_ErrorMessage = postResponse.getStatus() + " - " +  postResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            e.printStackTrace();

            return null;
        }
    }

    private Boolean performAllocation(String serverName, String poolName, List<AllocationType> allocationTypes, Long maxTimeMS)
    {
        JobSubmissionService jobSubmission = new JobSubmissionService(m_ConnectionProfile);

        // We'll need to package these up and call API to start job
        JobEntity curJobStatus = createAllocateJob(serverName, poolName, allocationTypes);
        if (curJobStatus == null)
            return false;

        Long jobId = curJobStatus.getId();

        // Wait for job to finish and complete
        long startTime = System.currentTimeMillis();
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > maxTimeMS) {
                m_ErrorMessage = "Maximum time elapsed";

                System.out.println(m_ErrorMessage);

                return false;
            }

            curJobStatus = jobSubmission.GetJob(jobId);

            if (curJobStatus == null)
                break;

            if (curJobStatus.getJobState().equals(JobState.Complete)){
                System.out.println("Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage());

                return true;

            } else if (curJobStatus.getJobState().equals(JobState.Error)) {
                m_ErrorMessage = "Error executing job " + curJobStatus.getProgressMessage();

                System.out.println(m_ErrorMessage);

                return false;
            }

            System.out.println("Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Executing job - State: " + curJobStatus.getJobState() + " - Message: " + curJobStatus.getProgressMessage());

        return false;
    }

    private JobEntity createAllocateJob(String serverName, String poolName, List<AllocationType> allocationTypes)
    {
        try {
            HttpResponse<JobEntity> postResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/allocation-pool/" + poolName + "/resolve/server/" + serverName  + "/execute")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(allocationTypes)
                    .asObject(JobEntity.class);

            if (postResponse.getStatus() == 200) {
                return postResponse.getBody();
            } else {
                m_ErrorMessage = postResponse.getStatus() + " - " + postResponse.getStatusText();

                System.out.println("Error " + m_ErrorMessage);

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            System.out.println("Error " + m_ErrorMessage);

            e.printStackTrace();

            return null;
        }
    }
}
