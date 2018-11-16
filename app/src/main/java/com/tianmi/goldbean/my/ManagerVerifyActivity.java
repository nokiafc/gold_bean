package com.tianmi.goldbean.my;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.ManagerVerifyAdapter;
import com.tianmi.goldbean.bean.ManagerVerifyBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ManagerVerifyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ManagerVerifyAdapter adapter;
    private List<ManagerVerifyBean> list = new ArrayList<ManagerVerifyBean>();
    private int goodsState = 0;//待审核
    private String pageNo = "1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_manager_verify);
        initTitle("审核上架");
        init();
        getGoodsList(1);
    }
    private void init(){

        listview = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.unread_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new ManagerVerifyAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //根据滚动状态，添加下拉加载
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }
    private void getGoodsList(int pageNo){
        RequestInterface request = new RequestInterface(this);
        request.getGoodsList(goodsState, pageNo, 10);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                swipeRefreshLayout.setRefreshing(false);//停止刷新
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }


    @Override
    public void onRefresh() {
        list.clear();//下拉请求第一页数据
        getGoodsList(1);
    }
}
