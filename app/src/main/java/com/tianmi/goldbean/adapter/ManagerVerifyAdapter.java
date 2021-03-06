package com.tianmi.goldbean.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.bean.GoodsQuestion;
import com.tianmi.goldbean.bean.ManagerVerifyBean;
import com.tianmi.goldbean.my.ManagerVerifyItemActivity;

import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;

public class ManagerVerifyAdapter extends BaseAdapter {
    private Context context;
    private List<ManagerVerifyBean> list;
    private ViewHolder holder;
    public ManagerVerifyAdapter(Context context, List<ManagerVerifyBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manager_verify, null);
            holder.contentText = (TextView)convertView.findViewById(R.id.content_text) ;
            holder.zhanghuText = (TextView)convertView.findViewById(R.id.zhanghu_text) ;
            holder.itemLayout = (RelativeLayout)convertView.findViewById(R.id.layout_item) ;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerVerifyBean bean = list.get(position);
                Bundle b =  new Bundle();
                b.putSerializable("bean", bean);
                ActivityUtil.startActivity((Activity)context, ManagerVerifyItemActivity.class, b);
            }
        });
        holder.zhanghuText.setText("用户ID:  "+list.get(position).getUserId());
        return convertView;
    }

    class  ViewHolder{
        TextView zhanghuText, contentText;
        RelativeLayout itemLayout;
    }
}
