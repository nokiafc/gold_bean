package com.tianmi.goldbean.message;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.adapter.FragmentAccountAdapter;
import com.tianmi.goldbean.bean.AccountWaterBean;
import com.tianmi.goldbean.net.RequestInterface;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ListView listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<AccountWaterBean> list = new ArrayList<AccountWaterBean>();
    private FragmentAccountAdapter accountAdapter;
    private String userId = DataUtil.getPreferences("userId", "");
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        getAccount(pageNo);
        Log.d("FC", "-=-=-=-=");
        return view;
    }
    private void init(View view){
        listview = (ListView)view.findViewById(R.id.account_list);
        accountAdapter = new FragmentAccountAdapter(list, getActivity());
        listview.setAdapter(accountAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.account_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }
    private void getAccount(int pageNo){
        RequestInterface request = new RequestInterface(getActivity());
        request.getAccount(Integer.parseInt(userId), pageNo , pageSize);
    }
}
