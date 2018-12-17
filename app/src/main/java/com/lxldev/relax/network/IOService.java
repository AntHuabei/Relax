package com.lxldev.relax.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IOService {

    public interface OnDataCallback{

        void onSuccess(int req_id,JsonObject data);

        default void onError(int req_id,Exception e){

        }
    }


    public interface Callback<T>{

        void onSuccess(T t);

        void onException(Exception e);
    }



    private Gson gson = new Gson();




    public void postJSON(int req_id, String url, Map<String,?> params){

    }

    public void postJSON(int req_id, String url){

        postJSON(req_id,url, Collections.EMPTY_MAP);
    }

    public void getJSON(final int req_id,String url,Map<String,?> parmas,OnDataCallback callback){

        OkHttpClient client = NetworkManager.getHttpClient();

        //添加User-Agent 网易对手机浏览器做了权限限制
        Request request = new Request.Builder().get().url(url)
                .addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1").build();


        //TODO url

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback != null){

                    callback.onError(req_id,e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(callback != null){


                    if(response.isSuccessful()){


                        try {

                            JsonObject object = gson.fromJson(response.body().string(),JsonObject.class);

                            callback.onSuccess(req_id,object);
                        }catch (Exception e){
                            callback.onError(req_id,e);
                        }


                    }else{

                        callback.onError(req_id,new Exception(response.message()));
                    }
                }

            }
        });

    }

    public void getJSON(int req_id,String url,OnDataCallback callback){

        getJSON(req_id,url,Collections.EMPTY_MAP,callback);
    }


}
