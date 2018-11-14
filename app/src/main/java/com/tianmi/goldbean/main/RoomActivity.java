package com.tianmi.goldbean.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.adapter.CommentAdapter;
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
    private Button sayBtn ,getRedBtn;
    private String userId = DataUtil.getPreferences("userId", "");
    private RelativeLayout circleFriendLayout, merchantLayout;
    private String merchantUserId = "";
    private ViewPager viewPager;
    private RoomPagerAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private TextView redNumText;
    private ListView sayListView;
    private CommentAdapter commentAdapter;
    private List<RoomBean.CommentInfo> commentInfos = new ArrayList<RoomBean.CommentInfo>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_room);
        initTitle("商品详情");
        goodsId = (String)getIntent().getExtras().get("goodsId");
        init();
        getGoodsDetail();
    }
    private void init(){
        sayListView = (ListView)findViewById(R.id.say_listview);
        commentAdapter = new CommentAdapter(this, commentInfos);
        sayListView.setAdapter(commentAdapter);

        redNumText = (TextView)findViewById(R.id.text_red) ;
        getRedBtn = (Button)findViewById(R.id.get_red);
        getRedBtn.setOnClickListener(this);
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
                redNumText.setText("红包剩余"+bean.getRemainAmount());
                merchantUserId = bean.getUserId()+"";
                String urls = bean.getGoodsUrl();
                String [] urlStr = urls.split(",");
                for(int i=0; i<urlStr.length; i++){
                    if(urlStr[i] != null && !urlStr[i].equals("")){
                        list.add(urlStr[i]);
                    }
                }
                adapter.notifyDataSetChanged();
                if(bean.getCommentInfo() != null){
                    commentInfos.addAll(bean.getCommentInfo());
                    commentAdapter.notifyDataSetChanged();
                }
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
            case R.id.get_red://跳转回答问题页面
                Intent i  = new Intent(this, AnswerActivity.class);
                i.putExtra("goodsId", goodsId);
                startActivity(i);
                break;
        }
    }
    private void goodsComment(){
        String remark = wordEdit.getText().toString().trim();
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.goodsComment(Integer.parseInt(goodsId), remark, userId);
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                //
            }
        });
    }
}
