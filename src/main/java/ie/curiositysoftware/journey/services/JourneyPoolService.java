package ie.curiositysoftware.journey.services;

import ie.curiositysoftware.dto.StringObject;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.journey.dto.Journey;
import ie.curiositysoftware.journey.dto.JourneyPool;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class JourneyPoolService extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public JourneyPoolService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;

        UnirestHelper.initUnirestMapper();
    }

    public JourneyPool createJourneyPool(Long releaseId, String name)
    {
        try {
            HttpResponse<JourneyPool> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/project/release/" + releaseId + "/journey-pool/create"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(new StringObject(name))
                    .asObject(JourneyPool.class);


            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatusText();

                return null;
            }

            return jsonResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return null;
        }
    }
}
