package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.R;

public class ImageVerifyDialog implements View.OnClickListener{
    private View dialog;
    private Dialog myDialog;
    private Activity activity;
    private EditText imgEdit;
    private ImageView codeImg;
    private Bitmap bitmap;
    private NewImgCallBack callBack;
    private Button cancelBtn, confirmBtn;

    RechargeDialog.MyPayCallBack payCallBack;

    public ImageVerifyDialog(Activity activity, Bitmap bitmap){
        this.bitmap = bitmap;
        this.activity = activity;
        dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_image_verify, null);
        initViews(dialog);
    }

    public void show(){
        myDialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        myDialog.setCancelable(true);
        myDialog.setContentView(dialog);
        myDialog.show();
    }
    public void dismiss(){
        myDialog.dismiss();
    }
    private void initViews(View view) {
        imgEdit = (EditText)view.findViewById(R.id.img_edit);
        codeImg = (ImageView)view.findViewById(R.id.img_code);
        codeImg.setImageBitmap(bitmap);
        codeImg.setOnClickListener(this);
        cancelBtn = (Button)view.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);
        confirmBtn = (Button)view.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(this);

    }
    public void setNewImg(Bitmap bitmap){
        codeImg.setImageBitmap(bitmap);
    }

    public interface NewImgCallBack{
        void getNewMsg();
        void getSmsMessage(String str);
    }
    public void setNewImg(NewImgCallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_code:
                callBack.getNewMsg();
                break;
            case R.id.btn_cancel:
                myDialog.dismiss();
                break;
            case R.id.btn_confirm:
                String imgStr = imgEdit.getText().toString();
                if(imgStr == null || imgStr.equals("")){
                    Toast.makeText(activity, "图片验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                myDialog.dismiss();
                callBack.getSmsMessage(imgEdit.getText().toString());
                break;
        }
    }
}
