package com.lxldev.relax.qiubai.tabs.io;

import android.os.AsyncTask;

import com.lxldev.relax.network.IOService;
import com.lxldev.relax.qiubai.tabs.entity.QiubaiJokeEntity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatchHotContentTask extends AsyncTask<String, Void, List<QiubaiJokeEntity>> {

    IOService.Callback<List<QiubaiJokeEntity>> callback;

    public CatchHotContentTask(IOService.Callback<List<QiubaiJokeEntity>> callback) {
        this.callback = callback;
    }

    private static HashMap<String, HashMap<String, String>> host2cookies = new HashMap<String, HashMap<String, String>>();

    @Override
    protected List<QiubaiJokeEntity> doInBackground(String... strings) {



        List<QiubaiJokeEntity> pengfuJokeEntities = new ArrayList<>();


        do {

            String host = null;

            Document doc = null;

            try {


                Connection conn = Jsoup.connect(strings[0]);

                URL url = new URL(strings[0]);

                host = url.getHost();

                loadCookiesByHost(url, conn);

                conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");


                doc = conn.get();

                storeCookiesByHost(url, conn);

            } catch (Exception e) {

                //e.printStackTrace();
            }

            if (doc == null) {

                break;
            }


            Element contentEle = doc.getElementById("content-left");

            if (contentEle == null) {

                break;

            }

            Elements elements = contentEle.children();


            elements.forEach(ele -> {

                if(!ele.hasClass("article"))

                    return;

                QiubaiJokeEntity entity = new QiubaiJokeEntity();

                pengfuJokeEntities.add(entity);

                Elements h1s = ele.getElementsByClass("content");

                if (!h1s.isEmpty()) {

                    Element h1 = h1s.first();

                    if (h1.childNodeSize() > 0) {

                        Element aNode = h1.child(0);

                        if ("span".equals(aNode.tagName())) {

                            //描述
                            entity.setDigest(aNode.text());
                        }
                    }

                    Elements imgNodes = ele.getElementsByClass("thumb");


                    if (! imgNodes.isEmpty()) {

                        //entity.setDigest(imgNode.text());

                        Elements imgs = imgNodes.first().getElementsByTag("img");

                        if (!imgs.isEmpty()) {

                            entity.setImgsrc("http:" + imgs.first().attr("src"));
                        }
                    }

                }


            });


        } while (false);


        return pengfuJokeEntities;
    }


    @Override
    protected void onPostExecute(List<QiubaiJokeEntity> s) {

        if (callback != null)

            if (s != null) {

                this.callback.onSuccess(s);
            } else {

                this.callback.onException(new Exception("解析失败"));

            }

    }


    private static void loadCookiesByHost(URL url, Connection con) {
        try {
            String host = url.getHost();
            if (host2cookies.containsKey(host)) {
                HashMap<String, String> cookies = host2cookies.get(host);
                for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                    con.cookie(cookie.getKey(), cookie.getValue());
                }
            }
        } catch (Throwable t) {
            // MTMT move to log
            System.err.println(t.toString() + ":: Error loading cookies to: " + url);
        }
    }

    private static void storeCookiesByHost(URL url, Connection con) {
        try {
            String host = url.getHost();
            HashMap<String, String> cookies = host2cookies.get(host);
            if (cookies == null) {
                cookies = new HashMap<String, String>();
                host2cookies.put(host, cookies);
            }
            cookies.putAll(con.response().cookies());
        } catch (Throwable t) {
            // MTMT move to log
            System.err.println(t.toString() + ":: Error saving cookies from: " + url);
        }
    }
}
