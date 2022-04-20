package ie.curiositysoftware.datacatalogue.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import ie.curiositysoftware.allocation.dto.DataCatalogueTestCriteria;
import ie.curiositysoftware.datacatalogue.DataListRowDto;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;

import ie.curiositysoftware.utils.RestResponsePage;
import org.apache.http.client.AuthCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class DataCatalogueTestCriteriaExecutionService {
    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public DataCatalogueTestCriteriaExecutionService(ConnectionProfile connectionProfile)
    {
        m_ConnectionProfile = connectionProfile;
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }


    public PageImpl<DataListRowDto> GetDataListRows( Long catalogueId, Long criteriaId, Pageable pageable, Map<String, String> parameters)
    {
        try {
            String queryString = "";
            if (parameters != null && parameters.size() > 0) {
                queryString = parameters.entrySet().stream()
                        .map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
                        .reduce((p1, p2) -> p1 + "&" + p2)
                        .orElse("");
            }
            if (pageable != null)
            {
                queryString = queryString + "page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize();
            }


            HttpResponse<com.fasterxml.jackson.databind.JsonNode> postResponse = Unirest.get(m_ConnectionProfile.getAPIUrl() + "/api/apikey/" + m_ConnectionProfile.getAPIKey() + "/data-catalogue/" + catalogueId + "/test-criteria/" + criteriaId + "/listdata?" + queryString)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    //.asJson();
                    .asObject(com.fasterxml.jackson.databind.JsonNode.class);

            if (postResponse.getStatus() == 200) {
                com.fasterxml.jackson.databind.JsonNode node = postResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectReader reader = objectMapper.readerFor(new TypeReference<RestResponsePage<DataListRowDto>>() { });

                return reader.readValue(node);
            } else {
                m_ErrorMessage = postResponse.getStatus() + " - " +  postResponse.getStatusText();

                return null;
            }
        } catch (Exception e) {
            m_ErrorMessage = e.getMessage();

            e.printStackTrace();

            return null;
        }
    }

    public String getErrorMessage() {
        return m_ErrorMessage;
    }
}
