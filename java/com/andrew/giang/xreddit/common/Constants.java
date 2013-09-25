package com.andrew.giang.xreddit.common;

/**
 * Created by Andrew on 9/3/13.
 */
public class Constants {

    public static class Reddit {

        public static final String
                HTTP = "http",
                DOMAIN = "www.reddit.com",
                API_LOGIN = "/api/login";


        public static String getUrl(String api) {
            return HTTP + "://" + DOMAIN + api;
        }
    }
}
