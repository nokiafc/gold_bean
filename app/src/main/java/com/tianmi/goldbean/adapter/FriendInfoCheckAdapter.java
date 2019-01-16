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
import com.tianmi.goldbean.bean.FriendInfoBean;
import com.tianmi.goldbean.bean.ManagerVerifyBean;
import com.tianmi.goldbean.my.ManagerFriendVerifyActivity;
import com.tianmi.goldbean.my.ManagerVerifyItemActivity;

import java.util.List;

public class FriendInfoCheckAdapter extends BaseAdapter {
    private Context context;
    private List<FriendInfoBean> list;
    private ViewHolder holder;
    public FriendInfoCheckAdapter(Context context, List<FriendInfoBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manage_friend_info, null);
           holder.itemLayout = convertView.findViewById(R.id.item_layout);
           holder.zhanghuText = convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendInfoBean bean = list.get(position);
                Bundle b =  new Bundle();
                b.putSerializable("bean", bean);
                ActivityUtil.startActivity((Activity)context, ManagerFriendVerifyActivity.class, b);
            }
        });
        holder.zhanghuText.setText("用户ID:  "+list.get(position).getUserId());
        return convertView;
    }

    class  ViewHolder{
        TextView zhanghuText;
        RelativeLayout itemLayout;
    }
}
