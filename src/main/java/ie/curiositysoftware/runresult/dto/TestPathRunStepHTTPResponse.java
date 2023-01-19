package ie.curiositysoftware.runresult.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class TestPathRunStepHTTPResponse
{
    private String StatusText;

    private HashMap<String, String> Headers;

    private int StatusCode;

    private String Body;

    public TestPathRunStepHTTPResponse()
    {

    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public HashMap<String, String> getHeaders() {
        return Headers;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getStatusText() {
        return StatusText;
    }

    public void setHeaders(HashMap<String, String> headers) {
        Headers = headers;
    }

    public void setStatusText(String statusText) {
        StatusText = statusText;
    }
}
