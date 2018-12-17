package com.lxldev.relax.news163.tabs.topnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.rotationheader.RotationFooter;
import com.liaoinstan.springview.rotationheader.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lxldev.relax.R;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.news163.tabs.joke.JokeListAdapter;
import com.lxldev.relax.news163.tabs.joke.io.JokeIOService;
import com.lxldev.relax.news163.tabs.topnews.entity.TopNewsEntity;
import com.lxldev.relax.news163.tabs.topnews.io.TopNewsIOService;

import java.util.List;

import androidx.navigation.Navigation;

/**
 * 头条
 */
public class TopNewsFragment extends Fragment {

    private View contentView;

    private SpringView springView;


    private RecyclerView recyclerView;


    private TopNewsListAdapter listAdapter;

    private TopNewsIOService topNewsIOService;

    private int cur_page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (contentView == null) {

            contentView = inflater.inflate(R.layout.news163_fragment_topnews, container, false);


            initView();
        }

        return contentView;

    }

    private void initView() {

        springView = contentView.findViewById(R.id.news163_topnews_refreshView);

        recyclerView = contentView.findViewById(R.id.news163_topnews_recyclerview);


        //  recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(listAdapter = new TopNewsListAdapter(getContext()));


        listAdapter.setOnItemClickListener(new TopNewsListAdapter.OnItemClickListener() {
            @Override
            public void click(int position, View view) {

                Bundle bundle = new Bundle();

                String url = listAdapter.getTopNewsEntity(position).getUrl();

                Intent intent = new Intent(getActivity(),TopNewDetailsDialog.class);

                intent.putExtra("url",url);
;
                startActivity(intent);

            }
        });


        topNewsIOService = new TopNewsIOService();


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


    private void loadData(boolean clean) {


        topNewsIOService.getTopNewsList(cur_page, new IOService.Callback<List<TopNewsEntity>>() {
            @Override
            public void onSuccess(List<TopNewsEntity> topNewsEntities) {

                getActivity().runOnUiThread(() -> {

                    if (topNewsEntities.isEmpty()) {

                        Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();

                    } else {


                    }

                    if (clean) {

                        listAdapter.clearAndAppendData(topNewsEntities);

                    } else {

                        listAdapter.appendData(topNewsEntities);

                    }

                    springView.onFinishFreshAndLoad();

                    cur_page++;
                });

            }

            @Override
            public void onException(Exception e) {

                getActivity().runOnUiThread(()->{

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    springView.onFinishFreshAndLoad();

                });
            }
        });
    }

}
