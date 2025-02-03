package ie.curiositysoftware.utils;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import kong.unirest.core.HttpRequest;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.UnirestException;

public class RestService {

    protected ConnectionProfile connectionProfile;
    protected String errorMessage = "";

    public RestService(ConnectionProfile connectionProfile) {
        this.connectionProfile = connectionProfile;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    protected <T> T execute(HttpRequest request, Class<? extends T> responseClass) {
        try {
            HttpResponse<T> jsonResponse = request
                    .header("Content-Type","application/json")
                    .header("accept", "application/json")
                    .asObject(responseClass);

            if (jsonResponse.getStatus() != 200) {
                errorMessage = jsonResponse.getStatus() + jsonResponse.getStatusText();
                return null;
            }
            return jsonResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            errorMessage = e.getMessage();
            return null;
        }
    }

    protected String getUrl(String path) {
        return connectionProfile.getAPIUrl() + "api/apikey/" + this.connectionProfile.getAPIKey() + path;
    }
}
