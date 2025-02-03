package ie.curiositysoftware.jobengine.services.job;

import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;

public class JobSubmissionService extends ServiceBase {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public JobSubmissionService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String getErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public Job getJob(long jobId)
    {
        try {
            HttpResponse<Job> jsonResponse = Unirest.get(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/job/", Long.toString(jobId)))
                    .header("Content-Type","application/json")
                    .header("accept", "application/json")
                    .asObject(Job.class);

            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatus() + jsonResponse.getStatusText();

                return null;
            }
            return jsonResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return null;
        }
    }

    public Job addJob(Job job)
    {
        try{
            HttpResponse<Job> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/job"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(job)
                    .asObject(Job.class);

            System.out.println(jsonResponse.getStatus() + jsonResponse.getStatusText() + jsonResponse.toString());
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

    public Job cloneJobAndRun(Long jobId)
    {
        try{
            HttpResponse<Job> jsonResponse = Unirest.put(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/job/" + jobId + "/clone-and-run"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .asObject(Job.class);

            System.out.println(jsonResponse.getStatus() + jsonResponse.getStatusText() + jsonResponse.toString());
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
