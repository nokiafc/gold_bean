package com.tianmi.goldbean.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.net.BaseRequest;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class OpenActivity extends BaseActivity {
    private Button cashBtn;
    private EditText companyName, companyAddress, companyPhone;
    private MultiPickResultView upAlbum;
    private ArrayList<String> photos = new ArrayList<String>();
    private Button commitBtn;
    private String picUrl="", name, address, phone;
    private String userId = DataUtil.getPreferences("userId", "");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_open);
        initTitle("开通商户");
        checkPermission();
        init();
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
    private void init(){
        commitBtn = (Button)findViewById(R.id.btn_confirm_commit) ;
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photos.size() <1){//没上传二维码
                    checkInfo();
                }else {
                    upLoadImg();
                }

            }
        });

        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 1, 1);

        companyName = (EditText)findViewById(R.id.edit_company_name) ;
        companyAddress = (EditText)findViewById(R.id.edit_company_address) ;
        companyPhone = (EditText)findViewById(R.id.edit_company_phone) ;

    }




    private void checkInfo(){
        name = companyName.getText().toString().trim();
        address = companyAddress.getText().toString().trim();
        phone = companyPhone.getText().toString().trim();
        if(name.equals("") || name == null){
            Toast.makeText(this, "请输入公司名称", Toast.LENGTH_SHORT).show();
            return;

        }
        if(address.equals("") || address == null){
            Toast.makeText(this, "请输入公司地址", Toast.LENGTH_SHORT).show();
            return;

        }
        if(phone.equals("") || phone == null){
            Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;

        }
        openMerchant();

    }

    private void upLoadImg(){
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.upLoadImg(photos.get(0));
        baseRequest.setCallback(new JsonCallback<String>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(String url, String message) throws IOException {
                picUrl = url;
                checkInfo();
            }
        });
    }

    private void openMerchant(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.openMerchant(Integer.parseInt(userId), name, address, phone, picUrl);
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                Toast.makeText(getApplicationContext(), "商户开通成功",Toast.LENGTH_SHORT).show();
                DataUtil.putPreferences("merchantsFlag", "1");
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
}
