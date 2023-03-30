package ie.curiositysoftware.modellerstorage.services;

import ie.curiositysoftware.dto.StringObject;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.journey.dto.JourneyPool;
import ie.curiositysoftware.modellerstorage.dto.ModellerFolder;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.http.entity.FileEntity;

public class ModellerFolderService  extends ServiceBase {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public ModellerFolderService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;

        UnirestHelper.initUnirestMapper();
    }

    public ModellerFolder saveFolder(ModellerFolder folder)
    {
        try {
            HttpResponse<ModellerFolder> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/folder"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(folder)
                    .asObject(ModellerFolder.class);


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

    public ModellerFolder mergeFolder(ModellerFolder folder)
    {
        try {
            HttpResponse<ModellerFolder> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/folder/merge"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(folder)
                    .asObject(ModellerFolder.class);


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
