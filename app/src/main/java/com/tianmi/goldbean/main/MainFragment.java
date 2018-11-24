package com.tianmi.goldbean.main;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.MainListAdapter;
import com.tianmi.goldbean.bean.PagerBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener ,SwipeRefreshLayout.OnRefreshListener{
    private List<PagerBean> list = new ArrayList<PagerBean>() ;
    private ImageView[] imageViews;
    private TextView title;
    private ListView listView;
    private List<RecyclerBean> mainList = new ArrayList<RecyclerBean>();
    private MainListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int pageNo = 1;
    private int pageSize = 10;
    private View footerView;
    private boolean isEmpty = false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        checkPermission();
        getMainInfo(1);
        return view;
    }

    private void init(View view){
        title = (TextView)view.findViewById(R.id.text_title);
        title.setText("捞金豆");

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.main_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_main_list, null);
        listView = (ListView)view.findViewById(R.id.listView);
        adapter = new MainListAdapter(getActivity(), mainList);
        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);
        //添加滚动监听，到底部加载
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount + firstVisibleItem == totalItemCount) {
                    View lastVisibleItemView = listView.getChildAt(totalItemCount - firstVisibleItem - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == view.getHeight()) {
                        // 滑动到了底部
                        if(listView.getFooterViewsCount() == 0 && !isEmpty){
                            footerView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footer, null);
                            listView.addFooterView(footerView);
                            pageNo++;
                            getMainInfo(pageNo);
                        }
                    } else {
                    }
                } else {
                }
            }
        });


    }

    private void getMainInfo(int pageNo){
        RequestInterface requestInterface = new RequestInterface(getActivity());
        requestInterface.getMainInfo(pageNo, pageSize);
        requestInterface.setCallback(new JsonCallback<List<RecyclerBean>>() {
            @Override
            public void onError(Request request, String e) {
                swipeRefreshLayout.setRefreshing(false);

            }
            @Override
            public void onResponse(List<RecyclerBean> list, String message) throws IOException {
                Log.d("FC", list.size()+"===");
                swipeRefreshLayout.setRefreshing(false);
                if(listView.getFooterViewsCount() > 0){//移除底部加载条
                    listView.removeFooterView(footerView);
                }
                if(list.size() == 0){
                    isEmpty = true;
                }
                mainList.addAll(list);
                adapter.notifyDataSetChanged();

            }
        });
    }


    @Override
    public void onRefresh() {
        //下拉刷新清除list里数据,恢复pageNo为1；
        isEmpty = false;
        mainList.clear();
        pageNo = 1;
        getMainInfo(pageNo);
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
//            dotLayout.addView(imageView, params);
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

    private void checkPermission() {
        int readStoragePermissionState = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStoragePermissionState != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                String[] permissions;
                permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), permissions, 0);
            }
        } else {

        }
        int writePermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                if (writePermission != PackageManager.PERMISSION_GRANTED) {
                    String[] permissionss = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(getActivity(), permissionss, 0);
                }
            }
        } else {

        }
    }

}
