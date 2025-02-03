package ie.curiositysoftware.jobengine.services.file;

import ie.curiositysoftware.jobengine.dto.file.UploadFileResponse;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;

import java.io.File;

public class FileService extends ServiceBase {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public FileService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String getErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public UploadFileResponse addFile(File fileDataStorage)
    {
        String endUrl = m_ConnectionProfile.getAPIUrl();

        try {
            HttpResponse<UploadFileResponse> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/file-storage/upload"))
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
