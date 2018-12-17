package com.lxldev.relax.news163.tabs.joke;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.rotationheader.RotationFooter;
import com.liaoinstan.springview.rotationheader.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lxldev.relax.R;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.news163.tabs.joke.entity.JokeEntity;
import com.lxldev.relax.news163.tabs.joke.io.JokeIOService;

import java.util.List;

public class JokeFragment extends Fragment {

    private int cur_page;

    private RecyclerView recyclerView;

    private JokeIOService jokeIOService;

    private JokeListAdapter listAdapter;

    private SpringView springView;


    private String TAG = this.getClass().getSimpleName();


    private View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(contentView == null){

            contentView = inflater.inflate(R.layout.news163_fragment_joke,container,false);
            this.initView(contentView);
        }


        return contentView;

    }

    private void initView(View view){

        springView = view.findViewById(R.id.news163_joke_refreshView);

        recyclerView = view.findViewById(R.id.news163_joke_recycleview);


      //  recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(listAdapter = new JokeListAdapter(getContext()));


        jokeIOService = new JokeIOService();


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


        loadData(true);

    }


    private void loadData(boolean clean){

        jokeIOService.getJokeList(cur_page, new IOService.Callback<List<JokeEntity>>() {
            @Override
            public void onSuccess(List<JokeEntity> jokeEntities) {

                if(jokeEntities.isEmpty()){

                    Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                }

                getActivity().runOnUiThread(() -> {

                    if(clean){

                        listAdapter.clearAndAppendData(jokeEntities);

                    }else{

                        int count = listAdapter.appendData(jokeEntities);

                        if(count == 0){
                            loadData(false);
                        }
                    }


                    cur_page ++;

                    springView.onFinishFreshAndLoad();
                });


            }

            @Override
            public void onException(Exception e) {


                springView.onFinishFreshAndLoad();


                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
