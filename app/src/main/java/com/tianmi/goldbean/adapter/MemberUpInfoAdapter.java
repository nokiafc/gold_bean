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
import com.tianmi.goldbean.bean.MemberUpInfoBean;
import com.tianmi.goldbean.my.ManagerFriendVerifyActivity;
import com.tianmi.goldbean.my.ManagerMemberUpInfoActivity;

import java.util.List;

public class MemberUpInfoAdapter extends BaseAdapter {
    private Context context;
    private List<MemberUpInfoBean> list;
    private ViewHolder holder;
    public MemberUpInfoAdapter(Context context, List<MemberUpInfoBean> list){
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
           holder.userid = convertView.findViewById(R.id.title);
           holder.state = convertView.findViewById(R.id.shenhe_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberUpInfoBean bean = list.get(position);
                Bundle b =  new Bundle();
                b.putSerializable("bean", bean);
                ActivityUtil.startActivity((Activity)context, ManagerMemberUpInfoActivity.class, b);
            }
        });
        holder.userid.setText("用户ID:  "+list.get(position).getUserId());
        String status = list.get(position).getAuditStatus()+"";
        if(status.equals("0")){
            holder.state.setText("审核状态:"+ "待审核");
        }else if(status.equals("1")){
            holder.state.setText("审核状态:"+ "审核通过");
        }else if(status.equals("2")){
            holder.state.setText("审核状态:"+ "驳回");
        }else if(status.equals("3")){
            holder.state.setText("审核状态:"+ "下架");
        }else if(status.equals("4")){
            holder.state.setText("审核状态:"+ "异议");
        }

        return convertView;
    }

    class  ViewHolder{
        TextView userid, state;
        RelativeLayout itemLayout;
    }
}
