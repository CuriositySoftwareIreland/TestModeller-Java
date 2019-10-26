package com.curiosity.JobEngine.Services.Job;

import com.curiosity.JobEngine.Entities.Job.JobResultEntity;
import com.curiosity.JobEngine.Services.ConnectionProfile;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JobResultService {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public JobResultService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String GetErrorMessage()
    {
        return this.m_ErrorMessage;
    }


    public JobResultEntity GetResult(long jobId)
    {
        try {
            HttpResponse<JobResultEntity> jsonResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/job/" + jobId + "/result")
                .header("accept", "application/json")
                .header("Content-Type","application/json")
                .asObject(JobResultEntity.class);

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
