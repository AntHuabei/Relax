package com.lxldev.relax.pengfu.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.rotationheader.RotationFooter;
import com.liaoinstan.springview.rotationheader.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lxldev.relax.R;
import com.lxldev.relax.common.ImagePreviewActivity;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.pengfu.tabs.entity.PengfuJokeEntity;
import com.lxldev.relax.pengfu.tabs.io.IndexIOService;

import java.util.List;

public class JokeTopHotListFragment extends Fragment {

    private int cur_page;

    private RecyclerView recyclerView;

    private IndexIOService jokeIOService;

    private JokeListAdapter listAdapter;

    private SpringView springView;


    private String TAG = this.getClass().getSimpleName();


    private View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        if(contentView == null){

            contentView = inflater.inflate(R.layout.pengfu_fragment_index,container,false);
            this.initView(contentView);
        }


        return contentView;

    }

    private void initView(View view){

        springView = view.findViewById(R.id.pengfu_index_refreshView);

        recyclerView = view.findViewById(R.id.pengfu_index_recyclerview);


      //  recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(listAdapter = new JokeListAdapter(getContext()));


        jokeIOService = new IndexIOService();


        springView.setHeader(new RotationHeader(this.getContext()));
        springView.setFooter(new RotationFooter(this.getContext()));

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                cur_page = 0;

                loadData(true);

            }

            @Override
            public void onLoadmore() {

                loadData(false);
            }
        });


        listAdapter.setOnImageClickListener(new JokeListAdapter.OnImageClickListener() {
            @Override
            public void click(String url) {
                if(url != null){

                    Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);

                    intent.putExtra(ImagePreviewActivity.URL_ARG,url);

                    startActivity(intent);
                }
            }
        });


        loadData(true);

    }


    private void loadData(boolean clean){

        jokeIOService.getMonthTopHotJokeList(cur_page, new IOService.Callback<List<PengfuJokeEntity>>() {
            @Override
            public void onSuccess(List<PengfuJokeEntity> jokeEntities) {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(clean){

                            listAdapter.clearAndAppendData(jokeEntities);

                        }else{

                            listAdapter.appendData(jokeEntities);
                        }


                        cur_page ++;

                        springView.onFinishFreshAndLoad();
                    }
                });


            }

            @Override
            public void onException(Exception e) {

                Log.e(TAG,e.getLocalizedMessage());

                springView.onFinishFreshAndLoad();

            }
        });
    }


}
