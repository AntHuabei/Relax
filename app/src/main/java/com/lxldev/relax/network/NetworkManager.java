package com.lxldev.relax.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class NetworkManager {

    private static OkHttpClient httpClient ;

    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    static {

        httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                cookieStore.put(url.host(),cookies);

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {

                List<Cookie> cookies= cookieStore.get(url.host());


                return cookies != null ? cookies : new ArrayList<>();
            }
        }).build();

    }

    public static  OkHttpClient getHttpClient(){

        return httpClient;
    }



}
