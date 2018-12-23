package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.umeng.commonsdk.debug.I;


public class RechargeDialog implements View.OnClickListener {
    private View dialog;
    private Dialog myDialog;
    private Activity activity;
    private RelativeLayout alipayLayout, wechatLayout;
    private ImageView alipayImg, wechatImg;
    private Button payConfirmBtn, payCancelBtn;
    private TextView titleText;
    private String imgFlag = "1";
    private String title = "";
    private MyPayCallBack payCallBack;

    public RechargeDialog(Activity activity, String title){
        this.activity = activity;
        this.title = title;
        dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_recharge, null);
        initViews(dialog);
    }

    public void showDialog(){
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
        titleText = (TextView)view.findViewById(R.id.text_title);
        titleText.setText(title);
        alipayLayout = (RelativeLayout)view.findViewById(R.id.layout_alipay);
        alipayLayout.setOnClickListener(this);
        wechatLayout = (RelativeLayout)view.findViewById(R.id.layout_wechat);
        wechatLayout.setOnClickListener(this);
        alipayImg = (ImageView)view.findViewById(R.id.img_alipay);
        wechatImg = (ImageView)view.findViewById(R.id.img_wechat);
        payConfirmBtn = (Button)view.findViewById(R.id.btn_recharge_confirm);
        payConfirmBtn.setOnClickListener(this);
        payCancelBtn = (Button)view.findViewById(R.id.btn_recharge_cancel);
        payCancelBtn.setOnClickListener(this);
    }

        public interface MyPayCallBack{
        void pay(String imgFlag);
    }
    public void setPayCall(MyPayCallBack payCallBack){
        this.payCallBack = payCallBack;
    }
    public void hintWechat(){
        wechatLayout.setVisibility(View.INVISIBLE);
    }

    public void showWechat(){
        alipayLayout.setVisibility(View.INVISIBLE);
        wechatImg.setImageResource(R.drawable.icon_pay_choose);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_alipay:
                if(imgFlag.equals("2")){
                    alipayImg.setImageResource(R.drawable.icon_pay_choose);
                    wechatImg.setImageResource(R.drawable.icon_pay_unchoose);
                    imgFlag = "1";
                }else {
                    alipayImg.setImageResource(R.drawable.icon_pay_choose);
                    wechatImg.setImageResource(R.drawable.icon_pay_unchoose);
                }
                break;
            case R.id.layout_wechat:
                if(imgFlag.equals("1")){
                    alipayImg.setImageResource(R.drawable.icon_pay_unchoose);
                    wechatImg.setImageResource(R.drawable.icon_pay_choose);
                    imgFlag = "2";
                }else {
                    alipayImg.setImageResource(R.drawable.icon_pay_unchoose);
                    wechatImg.setImageResource(R.drawable.icon_pay_choose);
                }
                break;
            case R.id.btn_recharge_confirm:
                payCallBack.pay(imgFlag);
                break;
            case R.id.btn_recharge_cancel:
                myDialog.dismiss();
                break;
        }
    }
}
