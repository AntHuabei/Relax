package com.lxldev.relax.pengfu.tabs.io;

import com.google.gson.Gson;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.pengfu.tabs.entity.PengfuJokeEntity;

import java.util.List;

public class IndexIOService extends IOService {


    private int page_size = 20;

    private Gson gson = new Gson();

    public void getJokeList(int page, Callback<List<PengfuJokeEntity>> callback) {

        String url_prefix = "https://www.pengfu.com/index_";

        String url_sufix = ".html";

        page++;

        String url = url_prefix + page + url_sufix;

        new CatchIndexContentTask(callback).execute(url);

    }

    public void getMonthTopHotJokeList(int page, Callback<List<PengfuJokeEntity>> callback) {

        String url_prefix = "https://www.pengfu.com/zuijurenqi_30_";

        String url_sufix = ".html";

        page++;

        String url = url_prefix + page + url_sufix;

        new CatchIndexContentTask(callback).execute(url);
    }


}
