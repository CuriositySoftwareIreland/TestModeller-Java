package ie.curiositysoftware.tdm.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ie.curiositysoftware.datacatalogue.DataListRowDto;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.RestResponsePage;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class DataListService extends ServiceBase {

    ConnectionProfile m_ConnectionProfile;

    String m_ErrorMessage;

    public DataListService(ConnectionProfile connectionProfile)
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

    public PageImpl<DataListRowDto> GetDataListRows(Long listID, String query, String select)
    {
        try {

            String url = createURLs(m_ConnectionProfile.getTDMUrl(), "apikey/", m_ConnectionProfile.getAPIKey(), "/metadata/lists/", listID.toString(), "/rows");

            String prepend = "?";
            if (!query.isEmpty())
            {
                url = url + prepend + "where=" + urlEncodeUTF8(query);
                prepend = "&";
            }
            if (!select.isEmpty())
            {
                url = url + prepend + "select=" + urlEncodeUTF8(select);
                prepend = "&";
            }

            HttpResponse<JsonNode> postResponse = Unirest.get(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    //.asJson();
                    .asObject(com.fasterxml.jackson.databind.JsonNode.class);

            if (postResponse.getStatus() == 200) {
                com.fasterxml.jackson.databind.JsonNode node = postResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
