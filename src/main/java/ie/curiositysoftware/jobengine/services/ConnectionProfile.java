package ie.curiositysoftware.jobengine.services;

public class ConnectionProfile
{
    private String APIUrl;

    private String APIKey;

    private String TDMUrl;

    private String GlobalKey;

    public ConnectionProfile(String host, String apiKey)
    {
        APIKey = apiKey;

        APIUrl = host;
    }

    public ConnectionProfile()
    {
        APIKey = null;

        APIUrl = null;

        GlobalKey = null;
    }

    public ConnectionProfile(ConnectionProfile p)
    {
        APIKey = p.APIKey;

        APIUrl = p.APIUrl;

        GlobalKey = p.GlobalKey;

        TDMUrl = p.TDMUrl;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setTDMUrl(String TDMUrl) {
        this.TDMUrl = TDMUrl;
    }

    public String getTDMUrl()
    {
        if (!TDMUrl.endsWith("/"))
            return TDMUrl + "/";

        return TDMUrl;
    }

    public String getAPIUrl() {
        if (!APIUrl.endsWith("/"))
            return APIUrl + "/";

        return APIUrl;
    }

    public String getGlobalKey() {
        return GlobalKey;
    }

    public void setAPIUrl(String APIUrl) {
        this.APIUrl = APIUrl;
    }

    public void setGlobalKey(String globalKey) {
        GlobalKey = globalKey;
    }
}
