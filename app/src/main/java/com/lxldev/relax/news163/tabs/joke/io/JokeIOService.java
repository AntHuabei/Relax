package com.lxldev.relax.news163.tabs.joke.io;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.news163.tabs.joke.entity.JokeEntity;

import java.util.List;

public class JokeIOService extends IOService {

    private String url_prefix = "http://3g.163.com/touch/jsonp/joke/chanListNews/T1419316284722/2/";

    private String url_sufix = ".html";

    private int page_size = 20;

    private Gson gson = new Gson();

    public void getJokeList(int page, IOService.Callback<List<JokeEntity>> callback){

        int curPage = page * page_size;

        String url = url_prefix + curPage+ "-" + (curPage + page_size) + url_sufix;


        getJSON(0, url, new OnDataCallback() {
            @Override
            public void onSuccess(int req_id, JsonObject data) {

                JsonArray jsonArray = data.getAsJsonArray("段子");

                if(callback != null){

                    List<JokeEntity> result = gson.fromJson(jsonArray,new TypeToken<List<JokeEntity>>(){}.getType());

                    callback.onSuccess(result);

                }
            }

            @Override
            public void onError(int req_id, Exception e) {

                if(callback != null){

                    callback.onException(e);
                }
            }
        });

    }
}
