package ie.curiositysoftware.allocation.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import ie.curiositysoftware.allocation.dto.DataAllocationResult;
import ie.curiositysoftware.allocation.dto.DataCatalogueTestCriteria;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;

public class DataCriteriaService {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public DataCriteriaService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;
    }

    public DataCatalogueTestCriteria GetTestCriteria(String catalogueName, String criteriaName)
    {
        try {
            HttpResponse<DataCatalogueTestCriteria> postResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/data-catalogue/" + catalogueName + "/test-criteria/" + criteriaName)
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
