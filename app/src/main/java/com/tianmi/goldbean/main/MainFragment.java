package com.tianmi.goldbean.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.RechargeDialog;
import com.tianmi.goldbean.Utils.UpdateManager;
import com.tianmi.goldbean.Utils.Utils;
import com.tianmi.goldbean.adapter.MainListAdapter;
import com.tianmi.goldbean.adapter.MainPagerAdapter;
import com.tianmi.goldbean.bean.PagerBean;
import com.tianmi.goldbean.bean.VersionBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener ,SwipeRefreshLayout.OnRefreshListener {
    private List<PagerBean> list = new ArrayList<PagerBean>() ;
    private ImageView[] imageViews;
    private TextView title;
    private ListView listView;
    private List<RecyclerBean> mainList = new ArrayList<RecyclerBean>();
    private List<RecyclerBean> topList = new ArrayList<RecyclerBean>();
    private MainListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int pageNo = 1;
    private int pageSize = 10;
    private View footerView;
    private boolean isEmpty = false;
    private UpdateDialog1 updateDialog;
    private String updateUrl = "";
    private ViewPager viewPager;
    private MainPagerAdapter topAdapter;
    private LinearLayout dotLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        checkPermission();
        getMainInfo(1);
        getVersion();
        getMainUp();
        return view;
    }

    private void init(View view){
        title = (TextView)view.findViewById(R.id.text_title);
        title.setText("捞金豆");
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.main_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#00aeff"));
        swipeRefreshLayout.setOnRefreshListener(this);

        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_main_list, null);
        viewPager = (ViewPager)headerView.findViewById(R.id.viewPager) ;
        topAdapter = new MainPagerAdapter(topList, getActivity());
        viewPager.setAdapter(topAdapter);
        dotLayout = (LinearLayout)headerView.findViewById(R.id.layout_dot) ;


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
    private void getMainUp(){
        RequestInterface request = new RequestInterface(getActivity());
        request.getMainInfoUp(1, 10, 1);
        request.setCallback(new JsonCallback<List<RecyclerBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<RecyclerBean> list, String message) throws IOException {
                if(list != null){
                    topList.addAll(list);
                    topAdapter.notifyDataSetChanged();
                    initDotLayout();
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

    private MyHandler myHandler = new MyHandler();
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int downLength = msg.arg1;
                    int fileLength = msg.arg2;
                    int progress = (int) (((float) downLength / fileLength)*100);
                    updateDialog.updateProgress(progress);
                    if(progress == 100){
                        installAPK();
                    }
                    break;

                case 1:
                    if (null != updateDialog)
                        updateDialog.dismiss();
                    Toast.makeText(getActivity(), "网络错误请重试", Toast.LENGTH_SHORT).show();
                    break;
                case 3:

                    break;
            }
        }
    }
    private String mSavePath = Environment.getExternalStorageDirectory() + "/goldbean.apk";

    protected void installAPK() {
        File apkFile = new File(mSavePath);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public void getVersion(){
        RequestInterface request = new RequestInterface(getActivity());
        request.getVersion();
        request.setCallback(new JsonCallback<VersionBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(VersionBean bean, String message) throws IOException {

                updateUrl = bean.getUrl();
                String remark = bean.getRemark();
                //判断本地和服务器版本是否相同
                String versionName = Utils.packageName(getActivity());
                String name = bean.getVersionName();

                if(!versionName.equals(name)){//服务器保存最新版本，如果不同就要更新

                    if(bean.getUpdateFlag() == 0){//非强制更新
                        updateDialog = new UpdateDialog1(getActivity(), remark, false);
                        Log.d("FC", versionName+"===="+name);
                        updateDialog.show();

                    }else {//强制

                    }
                }
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
        for(int i=0; i<topList.size(); i++){
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

    public class UpdateDialog1 implements View.OnClickListener {
        private View view;
        private Dialog myDialog;
        private Activity activity;
        private RelativeLayout updateLayout, cancelLayout;
        private TextView contentText;
        private RechargeDialog.MyPayCallBack payCallBack;
        private String content;
        private ProgressBar progressBar;
        private boolean isQiangzhi = false;

        public UpdateDialog1(Activity activity, String content, boolean isQiangzhi){
            this.isQiangzhi = isQiangzhi;
            this.activity = activity;
            this.content = content;
            view = LayoutInflater.from(activity).inflate(R.layout.dialog_update_1, null);
            initViews(view);
        }

        public void show(){
            myDialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
            myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            if(isQiangzhi){
                myDialog.setCancelable(false);
            }else {
                myDialog.setCancelable(true);
            }

            myDialog.setContentView(view);
            myDialog.show();
        }
        public void dismiss(){
            myDialog.dismiss();
        }
        private void initViews(View view) {
            contentText = (TextView)view.findViewById(R.id.content_text);
            updateLayout = (RelativeLayout)view.findViewById(R.id.update_layout);
            updateLayout.setOnClickListener(this);

            cancelLayout = (RelativeLayout)view.findViewById(R.id.cancel_layout);
            cancelLayout.setOnClickListener(this);
            if(isQiangzhi){//是
                cancelLayout.setVisibility(View.GONE);
            }else {
                cancelLayout.setVisibility(View.VISIBLE);
            }
            progressBar = (ProgressBar)view.findViewById(R.id.id_progress);

        }

        public void updateProgress(int progress){
            progressBar.setProgress(progress);
        }




        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.update_layout:
                    //升级接口
                    UpdateManager manager = new UpdateManager(myHandler);
                    manager.downloadApkFile(updateUrl);
                    break;
                case R.id.cancel_layout:
                    myDialog.dismiss();
                    break;
            }
        }

    }




}
