package ie.curiositysoftware.runresult.services;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.runresult.dto.TestPathRunCollectionEntity;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class TestPathRunCollectionService extends ServiceBase {
    private String m_ErrorMessage;

    private ConnectionProfile m_ConnectionProfile;

    public TestPathRunCollectionService(ConnectionProfile cp)
    {
        UnirestHelper.initUnirestMapper();

        m_ConnectionProfile = cp;

        m_ErrorMessage = "";
    }

    /**
     * Get error message
     * @return error message if failure occurs
     */
    public String getErrorMessage() {
        return m_ErrorMessage;
    }

    /**
     * Save test path run collection
     * @param runCol run collection
     * @return success or failure
     */
    public boolean saveTestPathRun(TestPathRunCollectionEntity runCol)
    {
        try {
            HttpResponse<String> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpathcollection"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(runCol)
                    .asString();

            if (postResponse.getStatus() == 200) {
                return true;
            } else {
                m_ErrorMessage = postResponse.getBody();

                return false;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            return false;
        }
    }

}
