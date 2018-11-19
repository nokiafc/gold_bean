package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.AccountWaterBean;

import java.util.List;

public class FragmentAccountAdapter extends BaseAdapter{
    private List<AccountWaterBean> list;
    private Context context;
    private ViewHolder holder;
    public FragmentAccountAdapter(List<AccountWaterBean> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return 10;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_account, null);
            holder.timeText = (TextView)convertView.findViewById(R.id.title_msg_text);
            holder.numText = (TextView)convertView.findViewById(R.id.title_no_text);
            holder.timeText = (TextView)convertView.findViewById(R.id.title_time_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    class ViewHolder{
        TextView titleText, numText, timeText;
    }
}
