package com.lxldev.relax.qiubai.tabs.hot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lxldev.relax.R;
import com.lxldev.relax.qiubai.tabs.entity.QiubaiJokeEntity;

import java.util.ArrayList;
import java.util.List;

public class JokeListAdapter extends RecyclerView.Adapter<JokeListAdapter.ViewHolder> {


    private List<QiubaiJokeEntity> dataSource = new ArrayList<>();

    public interface OnImageClickListener{

        void click(String url);
    }


    private OnImageClickListener onImageClickListener;


    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

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

    public void clearAndAppendData(List<QiubaiJokeEntity> data){

        dataSource.clear();

        appendData(data);
    }


    public void appendData(List<QiubaiJokeEntity> data){

         dataSource.addAll(data);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(context).inflate(R.layout.qiubai_item_hot_list,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QiubaiJokeEntity entity = dataSource.get(viewHolder.getAdapterPosition());

                if(onImageClickListener != null){
                    onImageClickListener.click(entity.getImgsrc());
                }

            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        QiubaiJokeEntity entity = dataSource.get(position);


        holder.dest.setText(entity.getDigest());


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


        private ImageView imageView;

        private TextView dest;

        public ViewHolder(View itemView) {
            super(itemView);


            dest = itemView.findViewById(R.id.qiubai_hot_item_dest);

            imageView = itemView.findViewById(R.id.qiubai_hot_item_image);

        }
    }
}
