package ie.curiositysoftware.allocation.services;

import ie.curiositysoftware.allocation.dto.AllocationPool;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class AllocationPoolService extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public AllocationPoolService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;
    }

    public AllocationPool CreateAllocationPool(AllocationPool allocationPool)
    {
        try {
            HttpResponse<AllocationPool> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/allocation-pool"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(allocationPool)
                    .asObject(AllocationPool.class);

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

    public AllocationPool GetAllocationPool(String name)
    {
        try {
            HttpResponse<AllocationPool> getResponse = Unirest.get(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/allocation-pool/name/", name))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asObject(AllocationPool.class);

            if (getResponse.getStatus() == 200) {
                return getResponse.getBody();
            } else {
                m_ErrorMessage = getResponse.getStatus() + " - " +  getResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            e.printStackTrace();

            return null;
        }
    }

    public Boolean DeleteAllocationPool(Long id)
    {
        try {
            HttpResponse deleteResponse = Unirest.delete(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/allocation-pool/", id.toString()))
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
