package com.curiosity.RunResult.Services;

import com.curiosity.JobEngine.Services.ConnectionProfile;
import com.curiosity.JobEngine.Utils.UnirestHelper;
import com.curiosity.RunResult.Entities.TestPathRunEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

/**
 * Server for saving run results to test modeller
 */
public class TestRunService {
    private String m_ErrorMessage;

    private ConnectionProfile m_ConnectionProfile;

    public TestRunService(ConnectionProfile cp)
    {
        UnirestHelper.InitUnirestMapper();

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
    public boolean saveTestPathRun(TestPathRunEntity runEntity)
    {
        try {
            HttpResponse<JsonNode> postResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/model/version/profile/testcollection/testsuite/testpath/run")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(runEntity)
                    .asJson();

            if (postResponse.getStatus() == 200) {
                return true;
            } else {
                m_ErrorMessage = postResponse.toString();

                return false;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            return false;
        }
    }
}
