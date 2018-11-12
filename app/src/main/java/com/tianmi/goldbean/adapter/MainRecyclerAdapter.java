package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import org.w3c.dom.Text;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RecyclerBean> list;

    public MainRecyclerAdapter(Context context , List<RecyclerBean> list){
        this.context = context;
        this.list = list;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler , parent , false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder)holder;
        String url = list.get(position).getGoodsUrl();
        String urls[] = url.split(",");
        Glide.with(context)
                .load(urls[1])
                .placeholder(R.drawable.img_fail)
                .error(R.drawable.img_fail)
                .into(holder1.img);

        holder1.textName.setText("房间ID： "+list.get(position).getId());
        holder1.textGift.setText("红包剩余量: "+list.get(position).getRemainRed()+"个");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView textName, textGift;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img_cover);
            textName = (TextView)itemView.findViewById(R.id.text_name);
            textGift = (TextView)itemView.findViewById(R.id.text_gift);
        }
    }
}
