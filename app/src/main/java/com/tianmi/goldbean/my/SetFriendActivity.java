package com.tianmi.goldbean.my;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.net.BaseRequest;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class SetFriendActivity extends BaseActivity {
    private MultiPickResultView upAlbum;
    private Button commitBtn;
    private ArrayList<String> photos = new ArrayList<String>();
    private int photoNum = 0;
    private int startNum = 0;
    private EditText redNumEdit, redMoneyEdit, remarkEdit;
    private Dialog myDialog ;
    private List<String> picUrls = new ArrayList<String>();
    private String userId = DataUtil.getPreferences("userId", "");
    private int goodsId ;
    private String redNum, redMoney, remark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_friend);
//        goodsId = Integer.parseInt(getIntent().getStringExtra("goodsId"));
        goodsId = 32;
        init();
    }
    private void init(){
        remarkEdit = findViewById(R.id.edit_jieshao);
        redMoneyEdit = findViewById(R.id.edit_red_money);
        redNumEdit = findViewById(R.id.edit_red);
        commitBtn = findViewById(R.id.btn_confirm_commit);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交接口
                if(photos.size() < 1){
                    Toast.makeText(getApplicationContext(), "转发图片数量需大于1张", Toast.LENGTH_SHORT).show();
                    return;
                }
                redNum = redNumEdit.getText().toString();
                if(redNum == null || redNum.equals("0") || redNum.equals("") ){
                    Toast.makeText(getApplicationContext(), "请输入正确的红包数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                 redMoney = redMoneyEdit.getText().toString();
                if(redMoney == null || redMoney.equals("0") || redMoney.equals("") ){
                    Toast.makeText(getApplicationContext(), "请输入正确的奖励金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(redMoney) / Integer.parseInt(redNum) < 1){
                    Toast.makeText(getApplicationContext(), "转发单个奖励不能低于1元", Toast.LENGTH_SHORT).show();
                    return;
                }
                remark = remarkEdit.getText().toString();

                if(startNum == photoNum){//有肯能在提交商品的时候失败，但是图片已经上传成功
                    setFriend();
                }else {
                    for(int i=0; i<photos.size(); i++){
                        Log.d("FC", i+"---"+photos.get(i));
                        upLoadImg(photos.get(i));
                    }
                }
            }
        });
        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 5, 1);
    }
    private void setFriend( ){
        String allUrl = "";
        for(int i=0; i< picUrls.size(); i++){
            if(i==0){
                allUrl =  picUrls.get(0);
            }else {
                allUrl = allUrl+","+picUrls.get(i);
            }

        }
        Log.d("FC", allUrl);
        RequestInterface request = new RequestInterface(this);
        request.retweet(goodsId, allUrl, Integer.parseInt(redNum), Integer.parseInt(redMoney), Integer.parseInt(userId), remark);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                Toast.makeText(getApplicationContext(), "上传成功，请等待审核", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startNum = startNum+1;
            if(startNum == photoNum){//所有图片请求都已成功
                setFriend( );
            }
        }
    };

    private void upLoadImg(String url) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.upLoadImg(url);
        baseRequest.setCallback(new JsonCallback<String>() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(SetFriendActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String picUrl, String message) throws IOException {
                picUrls.add(picUrl);
                handler.sendEmptyMessage(0);
            }
        });
    }


        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                upAlbum.onActivityResult(requestCode, resultCode, data);
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                photoNum = photos.size();
            }
        }
    }
}
