package com.lxldev.relax.news163.tabs.topnews.io;

import android.os.AsyncTask;

import com.lxldev.relax.network.IOService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CatchNewsContentTask extends AsyncTask<String, Void, String> {

    IOService.Callback<String> callback;

    public CatchNewsContentTask(IOService.Callback<String> callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {


        Document doc = null;

        try {
        Connection conn = Jsoup.connect(strings[0]);

        conn.header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");


            doc = conn.get();
        } catch (Exception e) {

            //e.printStackTrace();
        }

        if (doc == null) {
            return null;
        }

        Elements elements = doc.getElementsByClass("content");


        if (!elements.isEmpty()) {

            return elements.get(0).html();
        }


        return null;
    }


    @Override
    protected void onPostExecute(String s) {

        if (callback != null)

            if (s != null) {

                this.callback.onSuccess(s);
            } else {
                this.callback.onException(new Exception("解析失败"));
            }

    }
}
