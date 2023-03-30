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

public class JourneyService  extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public JourneyService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;

        UnirestHelper.initUnirestMapper();
    }

    public Journey createJourney(Journey journey)
    {
        try {
            HttpResponse<Journey> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/journey"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(journey)
                    .asObject(Journey.class);


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
