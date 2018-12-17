package com.lxldev.relax.pengfu.tabs.io;

import android.os.AsyncTask;

import com.lxldev.relax.network.IOService;
import com.lxldev.relax.pengfu.tabs.entity.PengfuJokeEntity;

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

public class CatchIndexContentTask extends AsyncTask<String, Void, List<PengfuJokeEntity>> {

    IOService.Callback<List<PengfuJokeEntity>> callback;

    public CatchIndexContentTask(IOService.Callback<List<PengfuJokeEntity>> callback) {
        this.callback = callback;
    }

    private static HashMap<String, HashMap<String, String>> host2cookies = new HashMap<String, HashMap<String, String>>();

    @Override
    protected List<PengfuJokeEntity> doInBackground(String... strings) {


        Document doc = null;

        try {



            Connection conn = Jsoup.connect(strings[0]);

            URL url = new URL(strings[0]);

            loadCookiesByHost(url, conn);

            conn.userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");


            doc = conn.get();

            storeCookiesByHost(url, conn);

        } catch (Exception e) {

            //e.printStackTrace();
        }

        if (doc == null) {
            return null;
        }

        Elements elements = doc.getElementsByClass("list-item bg1 b1 boxshadow");


        List<PengfuJokeEntity> pengfuJokeEntities = new ArrayList<>();

        elements.forEach(ele -> {

            PengfuJokeEntity entity = new PengfuJokeEntity();

            pengfuJokeEntities.add(entity);

            Elements h1s = ele.getElementsByTag("h1");

            if (!h1s.isEmpty()) {

                Element h1 = h1s.first();

                if (h1.childNodeSize() > 0) {

                    Element aNode = h1.child(0);

                    if ("a".equals(aNode.tagName())) {

                        //描述
                        entity.setTitle(aNode.text());
                    }
                }

                Element imgNode = h1.nextElementSibling();

                if (imgNode.hasClass("content-img")) {

                    entity.setDigest(imgNode.text());

                    Elements imgs = imgNode.getElementsByTag("img");

                    if (!imgs.isEmpty()) {

                        entity.setImgsrc(imgs.first().attr("src"));
                    }
                }

            }


        });


        return pengfuJokeEntities;
    }


    @Override
    protected void onPostExecute(List<PengfuJokeEntity> s) {

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
            System.err.println(t.toString()+":: Error loading cookies to: " + url);
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
            System.err.println(t.toString()+":: Error saving cookies from: " + url);
        }
    }
}
