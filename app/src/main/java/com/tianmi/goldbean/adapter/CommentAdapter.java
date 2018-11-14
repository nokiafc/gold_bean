package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.GoodsQuestion;
import com.tianmi.goldbean.net.bean.RoomBean;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<RoomBean.CommentInfo> list;
    private ViewHolder holder;
    public CommentAdapter(Context context, List<RoomBean.CommentInfo> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder.commentText = convertView.findViewById(R.id.text_comment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.commentText.setText(list.get(position).getUserComments());

        return convertView;
    }

    class ViewHolder{
        TextView commentText;

    }
}
