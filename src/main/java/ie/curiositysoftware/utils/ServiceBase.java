package ie.curiositysoftware.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

import java.io.IOException;

public abstract class ServiceBase {
    public static String createURLs(String... args)
    {
        String url = args[0];

        for (int i = 1; i < args.length; i++)
            url = buildUrl(url, args[i]);

        return url;
    }

    private static String buildUrl (String urlPrefix, String urlSuffix) {
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
