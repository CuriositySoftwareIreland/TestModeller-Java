package ie.curiositysoftware.runresult.dto;

import java.util.HashMap;

public class TestPathRunStepHTTPRequest
{
    public enum RequestType {
        Get,
        Post,
        Put,
        Delete,
        Options,
        Patch,
        Head,
        Copy
    }

    public enum BodyType {
        Raw,
        FormData,
        XWWWFormURLEncoded,
        None
    }

    private RequestType requestType;

    private HashMap<String, String> headers;

    private String body;

    private String endpoint;

    private BodyType bodyType;

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }
}
