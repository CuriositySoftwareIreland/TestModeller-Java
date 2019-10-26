package com.curiosity.JobEngine.Services.Job;

import com.curiosity.JobEngine.Entities.Job.JobEntity;
import com.curiosity.JobEngine.Services.ConnectionProfile;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JobSubmissionService {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public JobSubmissionService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String GetErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public JobEntity GetJob(long jobId)
    {
        try {
            HttpResponse<JobEntity> jsonResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/job/" + jobId)
                    .header("Content-Type","application/json")
                    .header("accept", "application/json")
                    .asObject(JobEntity.class);

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

    public JobEntity AddJob(JobEntity job)
    {
        try{
            HttpResponse<JobEntity> jsonResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/job")
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(job)
                    .asObject(JobEntity.class);

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
