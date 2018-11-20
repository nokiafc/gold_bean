package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.AccountWaterBean;
import com.tianmi.goldbean.bean.MerchantWaterBean;

import java.util.List;

public class FragmentMerchantWaterAdapter extends BaseAdapter{
    private List<MerchantWaterBean> list;
    private Context context;
    private ViewHolder holder;
    public FragmentMerchantWaterAdapter(List<MerchantWaterBean> list, Context context){
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
        holder.titleText.setText("您有一条商品审核消息");
        holder.numText.setText("商品ID"+list.get(position).getId());
        holder.timeText.setText("审核时间"+list.get(position).getAuditTime());



        return convertView;
    }
    class ViewHolder{
        TextView titleText, numText, timeText;
    }
}
