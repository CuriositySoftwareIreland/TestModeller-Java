package ie.curiositysoftware.runresult.services;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.runresult.dto.TestPathRunStep;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import ie.curiositysoftware.runresult.dto.TestPathRun;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * Server for saving run results to test modeller
 */
public class TestRunService extends ServiceBase {
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
            HttpResponse<String> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpath/run"))
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

    /**
     * Save test path run result step
     * @param testPathRunStep run result step
     * @return success or failure
     */
    public boolean saveTestPathRunStep(TestPathRunStep testPathRunStep)
    {
        try {
            HttpResponse<String> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpath/run/step"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(testPathRunStep)
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
