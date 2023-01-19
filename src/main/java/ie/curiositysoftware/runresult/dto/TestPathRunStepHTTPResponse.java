package ie.curiositysoftware.runresult.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class TestPathRunStepHTTPResponse
{
    private String statusText;

    private HashMap<String, String> headers;

    private int statusCode;

    private String body;

    private String contentType;

    private String sessionId;

    private Long time;

    public TestPathRunStepHTTPResponse()
    {

    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getTime() {
        return time;
    }

    public String getContentType() {
        return contentType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
