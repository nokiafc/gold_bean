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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MemberUpFriendActivity extends BaseActivity implements View.OnClickListener {
    private Button nextBtn;
    private MultiPickResultView upAlbum;
    private ArrayList<String> photos = new ArrayList<String>();
    private ListView listView ;
    private String userId = DataUtil.getPreferences("userId", "");
    private Dialog myDialog ;
    private String picUrls = "";
    private EditText friendNum;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                commitGoods();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_member_start_up);
        initTitle("上传转发截图");
        init();
        checkPermission();
    }
    private void init(){
        friendNum = (EditText)findViewById(R.id.edit_jieshao) ;
        nextBtn = (Button)findViewById(R.id.btn_confirm_commit);
        nextBtn.setOnClickListener(this);
        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 1, 1);
    }
    //提交商家设置
    private String redNum, allMoney, jieshao;
    private void commit(){
        if(friendNum.getText().toString().equals("")){
            Toast.makeText(this, "请输入商品转发号", Toast.LENGTH_SHORT).show();
            return;
        }
        myDialog = MyDialog.createLoadingDialog(this, "加载中...");
        myDialog.show();
        upLoadImg(photos.get(0));
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
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                if (writePermission != PackageManager.PERMISSION_GRANTED) {
                    String[] permissionss = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
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
                Toast.makeText(MemberUpFriendActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String picUrl, String message) throws IOException {
               picUrls = picUrl;
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void commitGoods(){
        RequestInterface request = new RequestInterface(this);
        request.memberUpFriend(Integer.parseInt(userId), friendNum.getText().toString(), picUrls);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                Toast.makeText(getApplicationContext(), "上传成功，请等待审核", Toast.LENGTH_SHORT).show();
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
            }


        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_commit:
                commit();//提交审核
                break;
        }
    }
}
