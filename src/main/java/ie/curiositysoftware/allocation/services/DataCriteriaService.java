package ie.curiositysoftware.allocation.services;

import ie.curiositysoftware.allocation.dto.DataAllocationResult;
import ie.curiositysoftware.allocation.dto.DataCatalogueTestCriteria;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class DataCriteriaService extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public DataCriteriaService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;
    }

    public DataCatalogueTestCriteria GetTestCriteria(String catalogueName, String criteriaName)
    {
        try {
            HttpResponse<DataCatalogueTestCriteria> postResponse = Unirest.get(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/data-catalogue/", catalogueName, "/test-criteria/", criteriaName))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asObject(DataCatalogueTestCriteria.class);

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

    public String getErrorMessage() {
        return m_ErrorMessage;
    }
}
