package ie.curiositysoftware.utils;

public abstract class ServiceBase {
    public String createURLs(String... args)
    {
        String url = args[0];

        for (int i = 1; i < args.length; i++)
            url = buildUrl(url, args[i]);

        return url;
    }

    private String buildUrl (String urlPrefix, String urlSuffix) {
        String urlPrefixFixed = urlPrefix.trim();
        while (urlPrefixFixed.endsWith("/")) { //Remove trailing '/'
            urlPrefixFixed = urlPrefixFixed.substring(0, urlPrefixFixed.length() - 1);
        }

        while (urlSuffix.startsWith("/")) { //Remove opening '/'
            urlSuffix = urlSuffix.substring(1, urlSuffix.length());
        }

        return (urlPrefixFixed + "/" + urlSuffix);
    }
}
