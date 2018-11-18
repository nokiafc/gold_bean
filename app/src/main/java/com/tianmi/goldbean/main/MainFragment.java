package com.tianmi.goldbean.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.MainPagerAdapter;
import com.tianmi.goldbean.adapter.MainRecyclerAdapter;
import com.tianmi.goldbean.bean.PagerBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private List<PagerBean> list = new ArrayList<PagerBean>() ;
    private List<RecyclerBean> recyclerList = new ArrayList<RecyclerBean>();
    private MainPagerAdapter mainPagerAdapter;
    private LinearLayout dotLayout;
    private RecyclerView recyclerView;
    private MainRecyclerAdapter mainRecyclerAdapter;


    private ImageView[] imageViews;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        getMainInfo();

        return view;
    }
    private void getMainInfo(){
        RequestInterface requestInterface = new RequestInterface(getActivity());
        requestInterface.getMainInfo(1, 10);
        requestInterface.setCallback(new JsonCallback<List<RecyclerBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<RecyclerBean> list, String message) throws IOException {
                recyclerList.addAll(list);
                mainRecyclerAdapter.notifyDataSetChanged();
            }
        });

    }
    private void init(View view){
        for(int i=0; i<1;i++){
            PagerBean bean = new PagerBean();
            list.add(bean);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), recyclerList);
        recyclerView.setAdapter(mainRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));




        imageViews = new ImageView[list.size()];
        dotLayout = (LinearLayout)view.findViewById(R.id.layout_dot);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        mainPagerAdapter = new MainPagerAdapter(list, getActivity());
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        initDotLayout();//初始化导航点

    }
    private void initDotLayout(){
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
}
