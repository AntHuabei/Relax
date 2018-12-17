package com.lxldev.relax.news163.tabs.topnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxldev.relax.R;
import com.lxldev.relax.news163.tabs.joke.entity.JokeEntity;
import com.lxldev.relax.news163.tabs.topnews.entity.TopNewsEntity;

import java.util.ArrayList;
import java.util.List;

public class TopNewsListAdapter extends RecyclerView.Adapter<TopNewsListAdapter.ViewHolder> {


    public interface OnItemClickListener{

        void click(int position,View view);
    }


    private List<TopNewsEntity> dataSource = new ArrayList<>();


    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;

    public TopNewsListAdapter(@NonNull Context context) {

        this.context = context;
    }

    public void clearAndAppendData(List<TopNewsEntity> data){

        dataSource.clear();

        dataSource.addAll(data);

        notifyDataSetChanged();
    }


    public void appendData(List<TopNewsEntity> data){

        dataSource.addAll(data);

        notifyDataSetChanged();
    }

    public TopNewsEntity getTopNewsEntity(int position){
        return dataSource.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.news163_item_topnews_list,parent,false);

        ViewHolder holder = new ViewHolder(rootView);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onItemClickListener != null){

                    onItemClickListener.click( holder.getAdapterPosition(),view);
                }

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        TopNewsEntity entity = dataSource.get(position);

        holder.dest.setText(entity.getDigest());

        holder.title.setText(entity.getTitle());

        String imgUrl = entity.getImgsrc();


        if(imgUrl == null || "".equals(imgUrl)){



            holder.imageView.setVisibility(View.GONE);

        }else{


            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(entity.getImgsrc()).thumbnail(0.1f).into(holder.imageView);

        }


    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView dest;

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news163_topnews_item_title);

            dest = itemView.findViewById(R.id.news163_topnews_item_dest);

            imageView = itemView.findViewById(R.id.news163_topnews_item_image);

        }

    }
}
