package com.lxldev.relax.qiubai.tabs.io;

import com.google.gson.Gson;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.qiubai.tabs.entity.QiubaiJokeEntity;

import java.util.List;

public class IndexIOService extends IOService {


    private int page_size = 20;

    private Gson gson = new Gson();

    public void getHotJokeList(int page, Callback<List<QiubaiJokeEntity>> callback) {

        String url_prefix = "https://www.qiushibaike.com/8hr/page/";

        String url_sufix = ".html";

        page++;

        String url = url_prefix + page + url_sufix;

        new CatchHotContentTask(callback).execute(url);

    }

    public void getMonthTopHotJokeList(int page, Callback<List<QiubaiJokeEntity>> callback) {

        String url_prefix = "https://www.pengfu.com/zuijurenqi_30_";

        String url_sufix = ".html";

        page++;

        String url = url_prefix + page + url_sufix;

        new CatchHotContentTask(callback).execute(url);
    }


}
