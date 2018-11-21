package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.PagerBean;

import java.util.List;

public class MainPagerAdapter extends PagerAdapter {
    private List<String> list;
    private Context context;
    public MainPagerAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, null);
        ImageView imageView =  (ImageView) view.findViewById(R.id.img_view_pager);
        Glide.with(context)
                .load(R.drawable.pager)
                .placeholder(R.drawable.img_fail)
                .error(R.drawable.img_fail)
                .into(imageView);
        container.addView(imageView);
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
