package ie.curiositysoftware.JobEngine.Utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import ie.curiositysoftware.JobEngine.Services.ConnectionProfile;

public class RESTService {

    protected ConnectionProfile connectionProfile;
    private String errorMessage = "";

    public RESTService(ConnectionProfile connectionProfile) {
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
