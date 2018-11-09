package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.RecyclerBean;

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
