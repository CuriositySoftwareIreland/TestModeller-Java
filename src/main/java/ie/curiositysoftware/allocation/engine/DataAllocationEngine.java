package ie.curiositysoftware.allocation.engine;

import ie.curiositysoftware.allocation.dto.*;
import ie.curiositysoftware.allocation.services.AllocationPoolService;
import ie.curiositysoftware.allocation.services.AllocationPoolTestService;
import ie.curiositysoftware.allocation.services.DataCriteriaService;
import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobState;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.job.JobSubmissionService;
import ie.curiositysoftware.utils.UnirestHelper;
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

        UnirestHelper.initUnirestMapper();
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
    public Boolean resolvePools(String serverName, List<AllocationType> allocationTypes)
    {
        return resolvePools(serverName, allocationTypes, 1000000000L);
    }

    /**
     * Resolve specified tests within data pools on specified server
     * @param serverName server to use for performing resolution
     * @param allocationTypes allocations to perform
     * @param maxTime maximum time in milliseconds to wait for resolution
     * @return success or failure
     */
    public Boolean resolvePools(String serverName, List<AllocationType> allocationTypes, Long maxTime)
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
    public DataAllocationResult getDataResult(String pool, String suite, String testName)
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

    public DataAllocationResult getDataResultForCriteria(String serverName, String catalogueName, String criteriaName, Integer howMany, ArrayList<DataAllocationCriteria> criteriaParameters) {
        return getDataResultForCriteria(serverName, catalogueName, criteriaName, howMany, criteriaParameters, 10000000L);
    }

    public DataAllocationResult getDataResultForCriteria(String serverName, String catalogueName, String criteriaName, Integer howMany, ArrayList<DataAllocationCriteria> criteriaParameters, Long maxTime)
    {
        // 1) Find criteria of name in catalogue
        DataCriteriaService dataCriteriaService = new DataCriteriaService(m_ConnectionProfile);
        DataCatalogueTestCriteria criteria = dataCriteriaService.GetTestCriteria(catalogueName, criteriaName);
        if (criteria == null) {
            m_ErrorMessage = dataCriteriaService.getErrorMessage();

            System.out.println("Error " + m_ErrorMessage);

            return null;
        }

        // 2) Create a new allocation pool
        AllocationPool allocationPool = new AllocationPool();
        allocationPool.setCatalogueId(criteria.getCatalogueId());
        allocationPool.setName("Temporary pool " + java.util.UUID.randomUUID().toString());

        AllocationPoolService allocationPoolService = new AllocationPoolService(m_ConnectionProfile);
        allocationPool = allocationPoolService.CreateAllocationPool(allocationPool);
        if (allocationPool == null) {
            m_ErrorMessage = dataCriteriaService.getErrorMessage();

            System.out.println("Error " + m_ErrorMessage);

            return null;
        }

        // 3) Create a new test inside pool
        AllocatedTest allocatedTest = new AllocatedTest();
        allocatedTest.setHowMany(howMany);
        allocatedTest.setName("Test " + java.util.UUID.randomUUID().toString());
        allocatedTest.setPoolId(allocationPool.getId());
        allocatedTest.setSuiteName("DataAllocationFramework");
        allocatedTest.setTableName("DataAllocationFramework");
        allocatedTest.setTestCriteriaIdCatalogueId(allocationPool.getCatalogueId());
        allocatedTest.setTestCriteriaId(criteria.getId());
        allocatedTest.setUniqueFind(false);

        HashMap<String, DataCatalogueModellerParameter> criteriaParamHash = criteria.getModellerParameterHash();

        List<AllocatedTestParameter> params = new ArrayList<AllocatedTestParameter>();
        for (DataAllocationCriteria allocationCriteria : criteriaParameters) {
            AllocatedTestParameter param = new AllocatedTestParameter();

            if (criteriaParamHash.containsKey(allocationCriteria.getParameterName())) {
                param.setCriteriaParameterId(criteriaParamHash.get(allocationCriteria.getParameterName()).getId());
                param.setValue(allocationCriteria.getParameterValue());
                param.setCriteriaParameterName(allocationCriteria.getParameterName());

                params.add(param);
            }
        }
        allocatedTest.setParameters(params);

        AllocationPoolTestService allocationPoolTestService = new AllocationPoolTestService(m_ConnectionProfile);
        allocatedTest = allocationPoolTestService.CreateAllocatedTest(allocatedTest, allocationPool.getId());
        if (allocatedTest == null) {
            m_ErrorMessage = dataCriteriaService.getErrorMessage();

            System.out.println("Error " + m_ErrorMessage);

            return null;
        }


        // 4) Run Allocation and retrieve result
        ArrayList<AllocationType> allocationTypes = new ArrayList<AllocationType>();
        allocationTypes.add(new AllocationType(allocationPool.getName(), allocatedTest.getSuiteName(), allocatedTest.getName()));
        performAllocation(serverName, allocationPool.getName(), allocationTypes, );

        DataAllocationResult res = getDataResult(allocationPool.getName(), allocatedTest.getSuiteName(), allocatedTest.getName());

        // 5) Delete pool
        Boolean deleted = allocationPoolService.DeleteAllocationPool(allocationPool.getId());
        if (!deleted) {
            m_ErrorMessage = allocationPoolService.getErrorMessage();

            System.out.println("Error deleting pool " + m_ErrorMessage);

            return res;

        }

        return res;

    }

    private Boolean performAllocation(String serverName, String poolName, List<AllocationType> allocationTypes, Long maxTimeMS)
    {
        JobSubmissionService jobSubmission = new JobSubmissionService(m_ConnectionProfile);

        // We'll need to package these up and call API to start job
        Job curJobStatus = createAllocateJob(serverName, poolName, allocationTypes);
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

            curJobStatus = jobSubmission.getJob(jobId);

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

    private Job createAllocateJob(String serverName, String poolName, List<AllocationType> allocationTypes)
    {
        try {
            HttpResponse<Job> postResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/allocation-pool/" + poolName + "/resolve/server/" + serverName  + "/execute")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(allocationTypes)
                    .asObject(Job.class);

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
