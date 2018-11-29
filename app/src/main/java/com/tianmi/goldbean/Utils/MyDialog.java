package com.tianmi.goldbean.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.R;


public class MyDialog {

    private static Toast toast;
    public static Dialog mProgresDialog;

    public static void showToast(Context context, String msg) {
        try {
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissProgressDialog() {
        if (mProgresDialog != null) {
            try {
                mProgresDialog.dismiss();
                mProgresDialog = null;
            } catch (Exception e) {
            } finally {
                mProgresDialog = null;
            }
        }
    }

    public static Dialog createLoadingDialog(Context context, String msg){

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setContentView(layout);// 设置布局

        return loadingDialog;
    }

}
