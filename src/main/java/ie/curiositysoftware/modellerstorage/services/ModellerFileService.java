package ie.curiositysoftware.modellerstorage.services;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.modellerstorage.dto.ModellerFile;
import ie.curiositysoftware.modellerstorage.dto.ModellerFolder;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.http.entity.FileEntity;

public class ModellerFileService  extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public ModellerFileService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;

        UnirestHelper.initUnirestMapper();
    }

    public ModellerFile saveFile(ModellerFile file)
    {
        try {
            HttpResponse<ModellerFile> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/folder/0/file"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(file)
                    .asObject(ModellerFile.class);


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
