package com.lxldev.relax.news163.tabs.topnews.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.news163.tabs.joke.entity.JokeEntity;
import com.lxldev.relax.news163.tabs.topnews.entity.TopNewsEntity;

import java.util.List;

public class TopNewsIOService extends IOService {

    private String url_prefix = "http://c.3g.163.com/nc/article/list/T1467284926140/";

    private String url_sufix = ".html";

    private int page_size = 40;

    private Gson gson = new Gson();

    public void getTopNewsList(int page, Callback<List<TopNewsEntity>> callback){

        int curPage = page * page_size;

        String url = url_prefix + curPage+ "-" + (curPage + page_size) + url_sufix;


        getJSON(0, url, new OnDataCallback() {
            @Override
            public void onSuccess(int req_id, JsonObject data) {

                JsonArray jsonArray = data.getAsJsonArray("T1467284926140");

                if(callback != null){

                    List<TopNewsEntity> result = gson.fromJson(jsonArray,new TypeToken<List<TopNewsEntity>>(){}.getType());

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

    public void  getNewsHtmlContent(String url,Callback<String> callback){


        new CatchNewsContentTask(callback).execute(url);

    }
}
