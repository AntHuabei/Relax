package com.lxldev.relax.news163.tabs.joke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lxldev.relax.R;
import com.lxldev.relax.common.MD5Utils;
import com.lxldev.relax.news163.tabs.joke.entity.JokeEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JokeListAdapter extends RecyclerView.Adapter<JokeListAdapter.ViewHolder> {


    private List<JokeEntity> dataSource = new ArrayList<>();

    private Set<String> contentMd5Set = new HashSet<>();

    private RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)	//加载成功之前占位图
            .error(R.mipmap.ic_launcher)	//加载错误之后的错误图
           // .override(400,400)	//指定图片的尺寸
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
            //.fitCenter()
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
            //.centerCrop()
            //.circleCrop()//指定图片的缩放类型为centerCrop （圆形）
            //.skipMemoryCache(true)	//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
            //.diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
            //.diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
            //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
            ;

    private Context context;

    public JokeListAdapter(@NonNull Context context) {

        this.context = context;
    }

    public void clearAndAppendData(List<JokeEntity> data){

        dataSource.clear();
        contentMd5Set.clear();

        appendData(data);
    }


    public int appendData(List<JokeEntity> data){

        int i = 0;
        for(JokeEntity entity : data){

            String digest = entity.getDigest();

            if(digest == null){

                digest = entity.getImgsrc();

                if(digest == null){
                    continue;
                }
            }

            String cmd5 = MD5Utils.getMD5(digest);

            if(!contentMd5Set.contains(cmd5)){

                dataSource.add(entity);

                contentMd5Set.add(cmd5);

                i ++;
            }
        }


        notifyDataSetChanged();

        return  i;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ViewHolder( LayoutInflater.from(context).inflate(R.layout.news163_item_joke_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        JokeEntity entity = dataSource.get(position);

        holder.textView.setText(entity.getDigest());


        String imgUrl = entity.getImgsrc();



        if(imgUrl == null || "".equals(imgUrl)){



            holder.imageView.setVisibility(View.GONE);

        }else{


            holder.imageView.setVisibility(View.VISIBLE);

            Glide.with(context).load(entity.getImgsrc()).apply(options).thumbnail(0.1f).into(holder.imageView);

        }


    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.news163_joke_item_textView);

            imageView = itemView.findViewById(R.id.news163_joke_item_image);

        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
