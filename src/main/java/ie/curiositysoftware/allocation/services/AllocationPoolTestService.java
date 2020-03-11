package ie.curiositysoftware.allocation.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import ie.curiositysoftware.allocation.dto.AllocatedTest;
import ie.curiositysoftware.allocation.dto.AllocationPool;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;

public class AllocationPoolTestService {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public AllocationPoolTestService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;
    }

    public AllocatedTest CreateAllocatedTest(AllocatedTest allocatedTest, Long poolId)
    {
        try {
            HttpResponse<AllocatedTest> postResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/allocation-pool/" + poolId + "/allocated-test")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(allocatedTest)
                    .asObject(AllocatedTest.class);

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

    public Boolean DeleteAllocationPoolTest(Long id)
    {
        try {
            HttpResponse deleteResponse = Unirest.delete(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/allocation-pool/allocated-test/" + id)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();

            if (deleteResponse.getStatus() == 200) {
                return true;
            } else {
                m_ErrorMessage = deleteResponse.getStatus() + " - " +  deleteResponse.getStatusText();

                return false;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            e.printStackTrace();

            return false;
        }

    }

    public String getErrorMessage() {
        return m_ErrorMessage;
    }

}
