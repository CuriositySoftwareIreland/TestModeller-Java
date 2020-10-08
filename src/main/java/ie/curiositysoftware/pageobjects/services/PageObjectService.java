package ie.curiositysoftware.pageobjects.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.pageobjects.dto.PageObjectEntity;
import ie.curiositysoftware.pageobjects.dto.PageObjectHistoryEntity;
import ie.curiositysoftware.pageobjects.dto.PageObjectParameterEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageObjectService {
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
            HttpResponse jsonResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/page-collection/page-object/page-object-history")
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
            HttpResponse jsonResponse = Unirest.put(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/page-collection/page-object/page-object-param")
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
            HttpResponse jsonResponse = Unirest.put(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/page-collection/page-object")
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
        setUnirestMapper();

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
            HttpResponse<PageObjectEntity> jsonResponse = Unirest.post(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/project/release/page-collection/page-object/criteria")
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
        setUnirestMapper();

        try {
            HttpResponse<PageObjectEntity> jsonResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "api/apikey/" + this.m_ConnectionProfile.getAPIKey() + "/page-collection/page-object/" + pageId)
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

    private void setUnirestMapper()
    {
        ObjectMapper om = (new ObjectMapper() {
            public com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

            {
                jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Unirest.setObjectMapper(om);
    }
}
