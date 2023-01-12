package ie.curiositysoftware.utils;

public abstract class ServiceBase {
    public String createURLs(String... args)
    {
        String url = "";

        for(String arg : args)
        {
            url = buildUrl(url, arg);
        }

        return url;
    }

    private String buildUrl (String urlPrefix, String urlSuffix) {
        String urlPrefixFixed = urlPrefix.trim();
        while (urlPrefixFixed.endsWith("/")) { //Remove trailing '/'
            urlPrefixFixed = urlPrefixFixed.substring(0, urlPrefixFixed.length() - 1);
        }

        while (urlSuffix.startsWith("/")) { //Remove opening '/'
            urlSuffix = urlSuffix.substring(1, urlPrefixFixed.length());
        }

        return (urlPrefixFixed + "/" + urlSuffix);
    }
}
