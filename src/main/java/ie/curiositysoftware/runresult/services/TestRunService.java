package ie.curiositysoftware.runresult.services;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.runresult.dto.TestPathRunStep;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import ie.curiositysoftware.runresult.dto.TestPathRun;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;

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
    public TestPathRun saveTestPathRun(TestPathRun runEntity)
    {
        try {
            HttpResponse<TestPathRun> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpath/run"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(runEntity)
                    .asObject(TestPathRun.class);

            if (postResponse.getStatus() == 200) {
                return postResponse.getBody();
            } else {
                m_ErrorMessage = postResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            return null;
        }
    }

    /**
     * Save test path run result step
     * @param testPathRunStep run result step
     * @return success or failure
     */
    public TestPathRunStep saveTestPathRunStep(TestPathRunStep testPathRunStep)
    {
        try {
            HttpResponse<TestPathRunStep> postResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpath/run/step"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(testPathRunStep)
                    .asObject(TestPathRunStep.class);

            if (postResponse.getStatus() == 200) {
                return postResponse.getBody();
            } else {
                m_ErrorMessage = postResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            return null;
        }
    }

    /**
     * Update test path run result
     * @param runEntity run result
     * @return success or failure
     */
    public TestPathRun updateTestPathRun(TestPathRun runEntity)
    {
        try {
            HttpResponse<TestPathRun> postResponse = Unirest.put(createURLs(m_ConnectionProfile.getAPIUrl(), "/api/apikey/", m_ConnectionProfile.getAPIKey(), "/model/version/profile/testcollection/testsuite/testpath/run"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(runEntity)
                    .asObject(TestPathRun.class);

            if (postResponse.getStatus() == 200) {
                return postResponse.getBody();
            } else {
                m_ErrorMessage = postResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            return null;
        }
    }
}
