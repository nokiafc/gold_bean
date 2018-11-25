package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianmi.goldbean.R;

public class UpdateDialog1 implements View.OnClickListener {
    private View dialog;
    private Dialog myDialog;
    private Activity activity;
    private RelativeLayout updateLayout, cancelLayout;
    private TextView contentText;
    private RechargeDialog.MyPayCallBack payCallBack;
    private String content;
    private ProgressBar progressBar;

    public UpdateDialog1(Activity activity, String content){
        this.activity = activity;
        this.content = content;
        dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_update_1, null);
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
        contentText = (TextView)view.findViewById(R.id.content_text);
        updateLayout = (RelativeLayout)view.findViewById(R.id.update_layout);
        updateLayout.setOnClickListener(this);

        cancelLayout = (RelativeLayout)view.findViewById(R.id.cancel_layout);
        cancelLayout.setOnClickListener(this);

        progressBar = (ProgressBar)view.findViewById(R.id.id_progress);


    }

    public interface  UpdateProgressBar{
        void setProgressBar(int progress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_layout:
                //升级接口
                break;
            case R.id.cancel_layout:
                myDialog.dismiss();
                break;
        }
    }
}
