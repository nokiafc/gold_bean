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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_account, null);
            holder.titleText = (TextView)convertView.findViewById(R.id.title_msg_text);
            holder.numText = (TextView)convertView.findViewById(R.id.title_no_text);
            holder.timeText = (TextView)convertView.findViewById(R.id.title_time_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (list.get(position).getDealSource()){
            case 1:
                holder.titleText.setText("您有一条充值消息");
                break;
            case 2:
                holder.titleText.setText("您有一条红包消息");
                break;
            case 3:
                holder.titleText.setText("您有一条分享奖励消息");
                break;
            case 4:
                holder.titleText.setText("您有一条红包退款消息");
                break;
            case 5:
                holder.titleText.setText("您有一条发红包消息");
                break;
            case 6:
                holder.titleText.setText("您有一条提现消息");
                break;
        }
        holder.numText.setText("订单号:"+list.get(position).getDealNo());
        holder.timeText.setText("交易时间:"+list.get(position).getCreateTime());


        return convertView;
    }
    class ViewHolder{
        TextView titleText, numText, timeText;
    }
}
