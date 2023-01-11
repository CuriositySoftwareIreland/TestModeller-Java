package ie.curiositysoftware.jobengine.services.job;

import ie.curiositysoftware.jobengine.dto.job.JobResult;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class JobResultService {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public JobResultService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String getErrorMessage()
    {
        return this.m_ErrorMessage;
    }


    public JobResult getResult(long jobId)
    {
        try {
            HttpResponse<JobResult> jsonResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/job/" + jobId + "/result")
                .header("accept", "application/json")
                .header("Content-Type","application/json")
                .asObject(JobResult.class);

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
