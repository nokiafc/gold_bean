package com.tianmi.goldbean.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.MainPagerAdapter;
import com.tianmi.goldbean.bean.PagerBean;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private ViewPager viewPager;
    private List<PagerBean> list = new ArrayList<PagerBean>();
    private MainPagerAdapter mainPagerAdapter;
    private LinearLayout dotLayout;


    private ImageView[] imageViews;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        for(int i=0; i<3;i++){
            PagerBean bean = new PagerBean();
            list.add(bean);
        }

        imageViews = new ImageView[list.size()];
        dotLayout = (LinearLayout)view.findViewById(R.id.layout_dot);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        mainPagerAdapter = new MainPagerAdapter(list, getActivity());
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<list.size(); i++){
                    imageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.shape_dot_1));
                    if(position == i){
                        imageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.shape_dot));
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0,0,0);

        for(int i=0; i<list.size(); i++){
            ImageView imageView = new ImageView(getActivity());
            imageViews[i] = imageView;
            if(i ==0){
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.shape_dot));

            }else {
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.shape_dot_1));
            }


            dotLayout.addView(imageView, params);
        }


    }
}
