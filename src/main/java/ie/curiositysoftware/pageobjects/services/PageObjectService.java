package ie.curiositysoftware.pageobjects.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.pageobjects.dto.PageObjectEntity;
import ie.curiositysoftware.pageobjects.dto.PageObjectHistoryEntity;
import ie.curiositysoftware.pageobjects.dto.PageObjectParameterEntity;
import ie.curiositysoftware.utils.ServiceBase;
import ie.curiositysoftware.utils.UnirestHelper;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.ObjectMapper;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageObjectService extends ServiceBase {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public PageObjectService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public static class PageObjectSearchCriteria
    {
        public String project;

        public String release;

        public String pageCollection;

        public String objectName;
    }

    public String GetErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public Boolean AddPageObjectHistory(PageObjectHistoryEntity pageObjectHistoryEntity)
    {
        try {
            HttpResponse jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/page-collection/page-object/page-object-history"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(pageObjectHistoryEntity)
                    .asJson();


            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatusText();

                return false;
            }

            return true;
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return false;
        }
    }

    public Boolean UpdatePageObjectParameter(PageObjectParameterEntity pageObjectParameter)
    {
        try {
            HttpResponse jsonResponse = Unirest.put(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/page-collection/page-object/page-object-param"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(pageObjectParameter)
                    .asJson();


            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatusText();

                return false;
            }

            return true;
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return false;
        }
    }

    public Boolean UpdatePageObject(PageObjectEntity pageObjectEntity)
    {
        try {
            HttpResponse jsonResponse = Unirest.put(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/page-collection/page-object"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(pageObjectEntity)
                    .asJson();


            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatusText();

                return false;
            }

            return true;
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return false;
        }
    }

    public PageObjectEntity GetPageObject(String pageObjectName)
    {
        UnirestHelper.initUnirestMapper();

        try {
            // 1) Extract components
            String pattern = "\\[(?<projectName>[^\\[]*)\\]\\[(?<releaseName>[^\\[]*)\\]\\[(?<pColName>[^\\[]*)\\]\\[(?<objectName>[^\\[]*)\\]";

            Pattern patternRegex = Pattern.compile(pattern);

            Matcher matcher = patternRegex.matcher(pageObjectName);

            if (!matcher.find())
                return null;

            PageObjectSearchCriteria poSearchCriteria = new PageObjectSearchCriteria();
            poSearchCriteria.project = matcher.group("projectName");
            poSearchCriteria.release = matcher.group("releaseName");
            poSearchCriteria.pageCollection = matcher.group("pColName");
            poSearchCriteria.objectName = matcher.group("objectName");

            // 2) Call API to get component
            HttpResponse<PageObjectEntity> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/project/release/page-collection/page-object/criteria"))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(poSearchCriteria)
                    .asObject(PageObjectEntity.class);

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

    public PageObjectEntity GetPageObject(long pageId)
    {
        UnirestHelper.initUnirestMapper();

        try {
            HttpResponse<PageObjectEntity> jsonResponse = Unirest.get(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/page-collection/page-object/", Long.toString(pageId)))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .asObject(PageObjectEntity.class);

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
