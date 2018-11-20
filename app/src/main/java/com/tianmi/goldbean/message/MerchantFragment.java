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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.adapter.FragmentAccountAdapter;
import com.tianmi.goldbean.adapter.FragmentMerchantWaterAdapter;
import com.tianmi.goldbean.bean.AccountWaterBean;
import com.tianmi.goldbean.bean.MerchantWaterBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MerchantFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private String userId = DataUtil.getPreferences("userId", "");
    private int pageNo = 1;
    private int pageSize = 10;
    private List<MerchantWaterBean> list = new ArrayList<MerchantWaterBean>();
    private ListView listview;
    private FragmentMerchantWaterAdapter merchantWaterAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isEmpty = false;
    private View footerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant, container, false);
        init(view);
        getGoodsMsg(pageNo);
        return view;
    }
    private void init(View view){
        listview = (ListView)view.findViewById(R.id.merchant_list);
        merchantWaterAdapter = new FragmentMerchantWaterAdapter(list, getActivity());
        listview.setAdapter(merchantWaterAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.merchant_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);
        //条目跳转
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putSerializable("bean", list.get(position));
                ActivityUtil.startActivity(getActivity(), MessageMerchantItemActivity.class, b);
            }
        });
        //滑动
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = listview.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {


                    View lastVisibleItemView = listview.getChildAt(listview.getChildCount() - 1);

                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listview.getHeight()) {

                        int pageIndex;
                        if (listview.getFooterViewsCount() == 0) {
                            if (!isEmpty) {

                                pageIndex = pageNo + 1;
                                pageNo = pageIndex ;
                                footerView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footer, null);
                                listview.addFooterView(footerView);
                                getGoodsMsg(pageIndex);
                            }

                        }

                    }
                }
            }
        });

    }
    private void getGoodsMsg(int pageNo){
        RequestInterface request = new RequestInterface(getActivity());
        request.getGoodsMsg(Integer.parseInt(userId), pageNo, pageSize);
        request.setCallback(new JsonCallback<List<MerchantWaterBean>>() {
            @Override
            public void onError(Request request, String e) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(List<MerchantWaterBean> merchantWaterBeans, String message) throws IOException {
                swipeRefreshLayout.setRefreshing(false);

                if(merchantWaterBeans.size() == 0){//翻页没有数据了
                    isEmpty = true;
                }
                if (listview.getFooterViewsCount() > 0) {
                    listview.removeFooterView(footerView);
                }

                if(merchantWaterBeans != null){
                    list.addAll(merchantWaterBeans);
                }
                merchantWaterAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        list.clear();
        pageNo = 1;
        getGoodsMsg(1);
    }
}
