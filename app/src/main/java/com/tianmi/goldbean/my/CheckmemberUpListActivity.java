package com.tianmi.goldbean.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.FriendInfoCheckAdapter;
import com.tianmi.goldbean.adapter.MemberUpInfoAdapter;
import com.tianmi.goldbean.bean.FriendInfoBean;
import com.tianmi.goldbean.bean.MemberUpInfoBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class CheckmemberUpListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<MemberUpInfoBean> memberList = new ArrayList<MemberUpInfoBean>();
    private String pageNo = "1";
    private MemberUpInfoAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_friend);
        initTitle("查看用户上传信息");
        init();
        getMemberList(pageNo);
    }
    private void init(){
        listview = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.unread_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new MemberUpInfoAdapter(this, memberList);
        listview.setAdapter(adapter);
    }
    private void getMemberList(String pageNo){
        RequestInterface request = new RequestInterface(this);
        request.getMemberInfoList1(1, 10);
        request.setCallback(new JsonCallback<List<MemberUpInfoBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<MemberUpInfoBean> list, String message) throws IOException {
                memberList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {

    }
}
