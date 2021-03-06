package ie.curiositysoftware.runresult.services;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.UnirestHelper;
import ie.curiositysoftware.runresult.dto.TestPathRun;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * Server for saving run results to test modeller
 */
public class TestRunService {
    private String m_ErrorMessage;

    private ConnectionProfile m_ConnectionProfile;

    public TestRunService(ConnectionProfile cp)
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
     * Save test path run result
     * @param runEntity run result
     * @return success or failure
     */
    public boolean saveTestPathRun(TestPathRun runEntity)
    {
        try {
            HttpResponse<String> postResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/model/version/profile/testcollection/testsuite/testpath/run")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(runEntity)
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
