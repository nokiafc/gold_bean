package com.tianmi.goldbean.my;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.adapter.ManagerCashListAdapter;
import com.tianmi.goldbean.adapter.ManagerVerifyAdapter;
import com.tianmi.goldbean.bean.ManagerCashListBean;
import com.tianmi.goldbean.bean.ManagerVerifyBean;
import com.tianmi.goldbean.bean.SonManagerBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ManagerCashActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ManagerCashListAdapter adapter;
    private List<ManagerCashListBean> cashList = new ArrayList<ManagerCashListBean>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_manager_cash);
        initTitle("提现列表");
        init();
        getCashList();
    }
    private void init(){
        listview = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.unread_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new ManagerCashListAdapter(this, cashList);
        listview.setAdapter(adapter);
    }
    private void getCashList(){
        RequestInterface request = new RequestInterface(this);
        request.managerCashList(1, 10);
        request.setCallback(new JsonCallback<List<ManagerCashListBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<ManagerCashListBean> list, String message) throws IOException {
                cashList.addAll(list);
            }
        });
    }

    @Override
    public void onRefresh() {
        cashList.clear();//下拉请求第一页数据
        getCashList();
    }

}
