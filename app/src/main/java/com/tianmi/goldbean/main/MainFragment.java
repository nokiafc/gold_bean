package com.tianmi.goldbean.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianmi.goldbean.R;

public class MainFragment extends Fragment {
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        //测试第二部
        return view;
    }
    private void init(View view){
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
    }
}
