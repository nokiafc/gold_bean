package com.tianmi.goldbean.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.adapter.RoomPagerAdapter;
import com.tianmi.goldbean.my.MerchantInfoActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RoomBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class RoomActivity extends BaseActivity implements View.OnClickListener {
    private String goodsId = "";
    private EditText wordEdit;
    private Button sayBtn;
    private String userId = DataUtil.getPreferences("userId", "");
    private RelativeLayout circleFriendLayout, merchantLayout;
    private String merchantUserId = "";
    private ViewPager viewPager;
    private RoomPagerAdapter adapter;
    private List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initTitle("商品详情");
        goodsId = (String)getIntent().getExtras().get("goodsId");
        init();
        getGoodsDetail();
    }
    private void init(){
        viewPager = (ViewPager)findViewById(R.id.viewPager) ;
        adapter = new RoomPagerAdapter(list, this);
        viewPager.setAdapter(adapter);

        wordEdit = (EditText)findViewById(R.id.edit_words);
        sayBtn = (Button)findViewById(R.id.btn_say);
        sayBtn.setOnClickListener(this);
        circleFriendLayout = (RelativeLayout)findViewById(R.id.friend_layout);
        circleFriendLayout.setOnClickListener(this);
        merchantLayout = (RelativeLayout)findViewById(R.id.merchant_layout);
        merchantLayout.setOnClickListener(this);
    }
    private void getGoodsDetail(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.getGoodsDetail(Integer.parseInt(goodsId));
        requestInterface.setCallback(new JsonCallback<RoomBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(RoomBean bean, String message) throws IOException {
                merchantUserId = bean.getUserId()+"";
                String urls = bean.getGoodsUrl();
                String [] urlStr = urls.split(",");
                for(int i=0; i<urlStr.length; i++){
                    if(urlStr[i] != null || urlStr[i].equals("")){
                        list.add(urlStr[i]);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_say:
                goodsComment();
                break;
            case R.id.friend_layout:
                //分享到朋友圈
                break;
            case R.id.merchant_layout:
                //跳转商家信息页
                Intent intent = new Intent(this, MerchantInfoActivity.class);
                intent.putExtra("merchantUserId", merchantUserId);
                startActivity(intent);
                break;
        }
    }
    private void goodsComment(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.goodsComment(Integer.parseInt(goodsId), "lalalaal", userId);
    }
}
