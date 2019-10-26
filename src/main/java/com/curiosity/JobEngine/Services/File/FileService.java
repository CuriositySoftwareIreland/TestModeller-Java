package com.curiosity.JobEngine.Services.File;

import com.curiosity.JobEngine.Entities.File.UploadFileResponse;
import com.curiosity.JobEngine.Services.ConnectionProfile;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class FileService {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public FileService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String GetErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public UploadFileResponse AddFile(File fileDataStorage)
    {
        String endUrl = m_ConnectionProfile.getAPIUrl();

        try {
            HttpResponse<UploadFileResponse> jsonResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/file-storage/upload")
                    .header("accept", "application/json")
//                    .header("Content-Type","multipart/form-data")
                    .field("file", fileDataStorage)
                    .asObject(UploadFileResponse.class);

            System.out.println(jsonResponse.getStatus());
            System.out.println(jsonResponse.getStatusText());

            return jsonResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return null;
        }
    }
}
