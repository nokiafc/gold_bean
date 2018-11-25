package com.tianmi.goldbean.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.GlideRoundTransform;
import com.tianmi.goldbean.main.RoomActivity;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.util.List;

public class MainListAdapter extends BaseAdapter {
    private Context context;
    private List<RecyclerBean> list;

    public MainListAdapter(Context context , List<RecyclerBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list.size() == 0){
            return 0;
        }else if(list.size()%2 == 0){//是整数

            return list.size()/2;
        }else {//单数
            return 1+list.size()/2;
        }

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
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_list, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img_cover);
            holder.textName = (TextView)convertView.findViewById(R.id.text_name) ;
            holder.textGift = (TextView)convertView.findViewById(R.id.text_gift);
            holder.itemLayout = (RelativeLayout)convertView.findViewById(R.id.layout_item);

            holder.img2 = (ImageView) convertView.findViewById(R.id.img_cover_2);
            holder.textName2 = (TextView)convertView.findViewById(R.id.text_name_2) ;
            holder.textGift2 = (TextView)convertView.findViewById(R.id.text_gift_2);
            holder.itemLayout2 = (RelativeLayout)convertView.findViewById(R.id.layout_item_2);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        String url = list.get(position*2).getGoodsUrl();
        String urls[] = url.split(",");
        Glide.with(context)
                .load(urls[1])
                .transform(new GlideRoundTransform(context, 10))
                .placeholder(R.drawable.img_fail)
                .error(R.drawable.img_fail)
                .into(holder.img);

        holder.textName.setText("房间ID： "+list.get(position*2).getId());
        holder.textGift.setText("红包剩余量: "+list.get(position*2).getRemainRed()+"个");
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("bean", list.get(position*2));
                ActivityUtil.startActivity((Activity) context, RoomActivity.class, b);
            }
        });
        //如果list是单数，不能填充
        if(position*2+1 != list.size()){holder.itemLayout2.setVisibility(View.VISIBLE);
            String url2 = list.get(position*2+1).getGoodsUrl();
            String urls2[] = url.split(",");
            Glide.with(context)
                    .load(urls2[1])
                    .transform(new GlideRoundTransform(context, 10))
                    .placeholder(R.drawable.img_fail)
                    .error(R.drawable.img_fail)
                    .into(holder.img2);

            holder.textName2.setText("房间ID： "+list.get(position*2+1).getId());
            holder.textGift2.setText("红包剩余量: "+list.get(position*2+1).getRemainRed()+"个");
            holder.itemLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable("bean", list.get(position*2+1));
                    ActivityUtil.startActivity((Activity) context, RoomActivity.class, b);
                }
            });
        }else if(position*2+1 == list.size()){
            holder.itemLayout2.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }
    class ViewHolder{
        private ImageView img, img2;
        private TextView textName, textName2, textGift, textGift2;
        private RelativeLayout itemLayout, itemLayout2;
    }
}
