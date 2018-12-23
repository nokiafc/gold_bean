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
import com.tianmi.goldbean.bean.ManagerCashListBean;
import com.tianmi.goldbean.bean.ManagerVerifyBean;
import com.tianmi.goldbean.my.ManagerCashVerifyActivity;
import com.tianmi.goldbean.my.ManagerVerifyItemActivity;

import java.util.List;

public class ManagerCashListAdapter extends BaseAdapter {
    private Context context;
    private List<ManagerCashListBean> list;
    private ViewHolder holder;
    public ManagerCashListAdapter(Context context, List<ManagerCashListBean> list){
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
                //不做跳转了，自动提现，不需要跳转审核
//                ManagerCashListBean bean = list.get(position);
//                Bundle b =  new Bundle();
//                b.putSerializable("bean", bean);
//                ActivityUtil.startActivity((Activity)context, ManagerCashVerifyActivity.class, b);
            }
        });
        holder.zhanghuText.setText("用户ID:"+list.get(position).getUserId()+"  提现金额:"+list.get(position).getAmount()+"  审核状态:"+list.get(position).getAduitStatus()
        );
        return convertView;
    }

    class  ViewHolder{
        TextView zhanghuText, contentText;
        RelativeLayout itemLayout;
    }
}
