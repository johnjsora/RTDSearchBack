package com.co.autentic.RTDDataSearch.common.utils;


import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GenUtils {

    private static String envFilePath = "env.properties";

//    public static String strToBase64(String value) {
//        return Base64.encodeBase64String(value.getBytes());
//    }

    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static int genRandomNumber(int min, int max) {

        return new Random().nextInt(max) + min;
    }

    public static Properties loadPropertiesObj() {
        Properties prop = new Properties();
        try (InputStream resourceAsStream = GenUtils.class.getClassLoader().getResourceAsStream(envFilePath)) {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("Unable to load properties file : " + envFilePath);
        }
        return prop;
    }

    private static boolean CheckParamName(String params, String queryParam) {
        String[] parameters = params.split(",");
        return Arrays.stream(parameters).anyMatch(queryParam::equals);
    }
    public static String GetParamValue(JsonNode queryParam) {

        if (queryParam != null) {
            return queryParam.asText();
        }
        return null;
    }


    public static Map<String, String> splitQuery(String queryString) throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new HashMap<>();
        String[] pairs = queryString.split("&");

        if (pairs.length > 0) {
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }

        return query_pairs;
    }

    public static Long TryParseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return (long) 0;
        }
    }

    public static int TryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String getHtmlTemplate(String resource) {

        try {
            StringBuilder htmlTemplateBuilder = new StringBuilder();
            System.out.println(resource);
            URL url = GenUtils.class.getClassLoader().getResource(resource);
            System.out.println(url);
            Files.readAllLines(Paths.get(url.toURI())).forEach(htmlTemplateBuilder::append);
            return htmlTemplateBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getHtmlTemplate(String resource, Map<String, String> params) {

        String htmlTemplate = getHtmlTemplate(resource);

        if (htmlTemplate.equals("")) {
            return "";
        }

        for (Map.Entry<String, String> data : params.entrySet()) {

            htmlTemplate = htmlTemplate.replace(data.getKey(), data.getValue());
        }

        return htmlTemplate;
    }


}
