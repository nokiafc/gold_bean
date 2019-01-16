package com.tianmi.goldbean.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.FriendInfoCheckAdapter;
import com.tianmi.goldbean.bean.FriendInfoBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class CheckFriendInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<FriendInfoBean> verifyList = new ArrayList<FriendInfoBean>();
    private String pageNo = "1";
    private FriendInfoCheckAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_friend);
        initTitle("审核朋友圈信息");
        init();
        getInfoList(pageNo);
    }
    private void init(){
        listview = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.unread_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new FriendInfoCheckAdapter(this, verifyList);
        listview.setAdapter(adapter);
    }
    private void getInfoList(String pageNo){
        RequestInterface request = new RequestInterface(this);
        request.getFriendInfoList(Integer.parseInt(pageNo), 10, 0);
        request.setCallback(new JsonCallback<List<FriendInfoBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<FriendInfoBean> list, String message) throws IOException {
                verifyList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {

    }
}
