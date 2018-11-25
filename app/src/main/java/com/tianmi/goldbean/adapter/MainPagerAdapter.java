package com.tianmi.goldbean.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.bean.PagerBean;
import com.tianmi.goldbean.main.RoomActivity;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.util.List;

public class MainPagerAdapter extends PagerAdapter {
    private List<RecyclerBean> list;
    private Context context;
    public MainPagerAdapter(List<RecyclerBean> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, null);
        ImageView imageView =  (ImageView) view.findViewById(R.id.img_view_pager);
        String url = list.get(position).getGoodsUrl();
        String []urls = url.split(",");

        Glide.with(context)
                .load(urls[0])
                .dontTransform()
                .placeholder(R.drawable.img_fail)
                .error(R.drawable.img_fail)
                .into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("bean", list.get(position));
                ActivityUtil.startActivity((Activity) context, RoomActivity.class, b);
            }
        });
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
