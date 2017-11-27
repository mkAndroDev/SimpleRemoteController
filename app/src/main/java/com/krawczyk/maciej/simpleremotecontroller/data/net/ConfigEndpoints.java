package com.krawczyk.maciej.simpleremotecontroller.data.net;

public class ConfigEndpoints {

    static String BASE_URL = "http://maciej.krawczyk.com";
    String HEADER_ACCEPT_TYPE = "application/json";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String BASE_URL) {
        ConfigEndpoints.BASE_URL = BASE_URL;
    }
}
