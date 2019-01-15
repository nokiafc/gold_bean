package com.tianmi.goldbean.my;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.adapter.ConductRecyclerAdapter;
import com.tianmi.goldbean.bean.GoodsQuestion;
import com.tianmi.goldbean.bean.RedPackage;
import com.tianmi.goldbean.bean.UserGoods;
import com.tianmi.goldbean.net.BaseRequest;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class StartConductActivity extends BaseActivity implements View.OnClickListener , ConductRecyclerAdapter.DeleteInterface{
    private Button nextBtn;
    private MultiPickResultView upAlbum;
    private ArrayList<String> photos = new ArrayList<String>();
    private ListView listView ;
    private List<GoodsQuestion> listQuestion = new ArrayList<GoodsQuestion>();
    private List<String> picUrls = new ArrayList<String>();
    private RecyclerView  recycleview;
    private ConductRecyclerAdapter adapter;
    private EditText redNumEdit, redMoneyEdit, jieshaoEdit;
    private String userId = DataUtil.getPreferences("userId", "");
    private int photoNum = 0;
    private int startNum = 0;
    private RelativeLayout chooseLayout, unchooseLayout, rightLayout;
    private ImageView chooseImg, unchooseImg;
    private Dialog myDialog ;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startNum = startNum+1;
            if(startNum == photoNum){//所有图片请求都已成功
                commitGoods();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_start_conduct);
        initTitle("商家设置");
        init();
        checkPermission();
    }
    private void init(){
        jieshaoEdit = (EditText)findViewById(R.id.edit_jieshao);
        rightLayout = (RelativeLayout)findViewById(R.id.layout_right);
        rightLayout.setOnClickListener(this);
        rightLayout.setVisibility(View.VISIBLE);
        chooseLayout = (RelativeLayout)findViewById(R.id.layout_choose) ;
        chooseLayout.setOnClickListener(this);
        unchooseLayout = (RelativeLayout)findViewById(R.id.layout_unchoose) ;
        unchooseLayout.setOnClickListener(this);
        chooseImg = (ImageView)findViewById(R.id.img_choose);
        unchooseImg = (ImageView)findViewById(R.id.img_unchoose);

        nextBtn = (Button)findViewById(R.id.btn_confirm_commit);
        nextBtn.setOnClickListener(this);
        redNumEdit = (EditText)findViewById(R.id.edit_red);
        redMoneyEdit = (EditText)findViewById(R.id.edit_red_money);
        for(int i=0; i<5; i++){//初始五个问题实例
            GoodsQuestion gq = new GoodsQuestion();
            gq.setUserId(userId);
            listQuestion.add(gq);
        }

        adapter = new ConductRecyclerAdapter(this, listQuestion);
        recycleview = (RecyclerView)findViewById(R.id.recycleview);
        adapter.setDeleteInterface(this);

        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));

        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 5, 1);
    }
    //提交商家设置
    private String redNum, allMoney, jieshao;
    private void commit(){
        redNum = redNumEdit.getText().toString().trim();
        allMoney = redMoneyEdit.getText().toString().trim();
        jieshao = jieshaoEdit.getText().toString().trim();
        //1.检查必填项目
        if(jieshao == null || jieshao.equals("")){
            Toast.makeText(this, "商品说明不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(photos.size() < 1){
            Toast.makeText(this, "商家宣传图片数量需大于1张", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0; i<listQuestion.size(); i++){
            if(listQuestion.get(i).getQuestionName() == null|| listQuestion.get(i).getQuestionName().equals("")){
                Toast.makeText(this, "问题"+(i+1)+"不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if(listQuestion.get(i).getQuestionAnswer() == null|| listQuestion.get(i).getQuestionAnswer().equals("")){
                Toast.makeText(this, "问题"+(i+1)+"的答案不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(redNum == null || redNum.equals("0") || redNum.equals("") ){
            Toast.makeText(this, "请输入正确的红包数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(redNum) <100){
            Toast.makeText(this, "红包数量不能低于100个", Toast.LENGTH_SHORT).show();
            return;
        }
        if(allMoney == null || allMoney.equals("0") || allMoney.equals("")){
            Toast.makeText(this, "请输入正确红包金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(allMoney) < 10){
            Toast.makeText(this, "红包总金额不能低于10元", Toast.LENGTH_SHORT).show();
            return;
        }

        myDialog = MyDialog.createLoadingDialog(this, "加载中...");
        myDialog.show();
        if(startNum == photoNum){//有肯能在提交商品的时候失败，但是图片已经上传成功
            commitGoods();
        }else {
            for(int i=0; i<photos.size(); i++){
                upLoadImg(photos.get(i));
            }
        }


    }

    private void checkPermission() {
        int readStoragePermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (readStoragePermissionState != PackageManager.PERMISSION_GRANTED || cameraPermissionState != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            } else {
                String[] permissions;
                if (readStoragePermissionState != PackageManager.PERMISSION_GRANTED && cameraPermissionState != PackageManager.PERMISSION_GRANTED) {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                } else {
                    permissions = new String[]{
                            (readStoragePermissionState != PackageManager.PERMISSION_GRANTED) ? Manifest.permission.READ_EXTERNAL_STORAGE : Manifest.permission.CAMERA};
                }

                ActivityCompat.requestPermissions(this, permissions, 0);
            }
        } else {

        }
        int writePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                if (writePermission != PackageManager.PERMISSION_GRANTED) {
                    String[] permissionss = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(this, permissionss, 0);
                }


            }
        } else {

        }

    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.CAMERA:
            case android.Manifest.permission.WRITE_EXTERNAL_STORAGE:
                // No need to explain to user as it is obvious
                return false;
            default:
                return true;
        }
    }

    private void upLoadImg(String url){
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.upLoadImg(url);
        baseRequest.setCallback(new JsonCallback<String>() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(StartConductActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String picUrl, String message) throws IOException {
                picUrls.add(picUrl);
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void commitGoods(){
        //设置图片url
        UserGoods userGoods = new UserGoods();
        userGoods.setUserId(userId);
        String allUrl = "";
        for(int i=0; i< picUrls.size(); i++){
            if(i==0){
                allUrl =  picUrls.get(0);
            }else {
                allUrl = allUrl+","+picUrls.get(i);
            }

        }
        userGoods.setGoodsUrl(allUrl);
        userGoods.setGoodsName(jieshao);
        //设置红包信息
        RedPackage redPackage = new RedPackage();
        redPackage.setRedWay(flag);
        redPackage.setUserId(userId);
        redPackage.setRedAmount(BigDecimal.valueOf(Double.parseDouble(allMoney)));
        redPackage.setRedCount(redNum);


        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.publishGoods(userGoods,listQuestion,redPackage);
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
                myDialog.dismiss();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
                Toast.makeText(getApplicationContext(), "上传成功，请等待审核", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), GoodsVerfiyActivity.class);
                i.putExtra("goodsId", o+"");
                startActivity(i);
                finish();
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
    private String flag = "1";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_commit:
                commit();//提交审核
                break;
            case R.id.layout_choose:
                if(flag.equals("1")){
                    chooseImg.setImageResource(R.drawable.icon_good_choose);
                    unchooseImg.setImageResource(R.drawable.icon_good_unchoose);
                    flag = "1";
                }else {
                    chooseImg.setImageResource(R.drawable.icon_good_choose);
                    unchooseImg.setImageResource(R.drawable.icon_good_unchoose);
                    flag = "1";
                }
                break;
            case R.id.layout_unchoose:
                if(flag.equals("1")){
                    chooseImg.setImageResource(R.drawable.icon_good_unchoose);
                    unchooseImg.setImageResource(R.drawable.icon_good_choose);
                    flag = "0";
                }else {
                    chooseImg.setImageResource(R.drawable.icon_good_unchoose);
                    unchooseImg.setImageResource(R.drawable.icon_good_choose);
                    flag = "0";
                }
                break;
            case R.id.layout_right://添加一个问题
                if(listQuestion.size() <5){
                    GoodsQuestion gq = new GoodsQuestion();
                    gq.setUserId(userId);
                    listQuestion.add(gq);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(this, "最多添加5个问题", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void deleteItem(int position) {
        listQuestion.remove(position);
        adapter.notifyDataSetChanged();
    }
}
