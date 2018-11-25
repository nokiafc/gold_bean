package com.tianmi.goldbean.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianmi.goldbean.R;


public class UpdateDialog extends Dialog implements SpecialProgressBarView.AnimationEndListener, SpecialProgressBarView.OntextChangeListener, View.OnClickListener {
    /**
     * 类标签
     */
    public static final String LOG_TAG = "UpdateDialog";

    /**
     * 版本号
     */
    private TextView versionCodeTex;

    /**
     * 版本更新描述
     */
    private TextView updateDescribeTex;

    /**
     * 下载进度条
     */
    private SpecialProgressBarView loadingProgressView;

    /**
     * 执行安装按钮
     */
    private ImageView installImg;

    /**
     * 取消安装按钮
     */
    private ImageView cancelImg;

    /**
     * 更新监听器
     */
    private OnUpdateListener onUpdateListener;

    /**
     * 是否强制更新
     */
    private boolean isForce = false;

    /**
     * 更新Api的版本Code
     */
    private String versionCode;

    /**
     * 更新信息
     */
    private String updateDescribe;


    public UpdateDialog(Context context, boolean isForce, String versionCode, String updateDescribe) {
        super(context, R.style.custom_select_dialog);
        this.isForce = isForce;
        this.versionCode = versionCode;
        this.updateDescribe = updateDescribe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置为不能关闭
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);

        versionCodeTex = (TextView) findViewById(R.id.versionCodeTex);
        updateDescribeTex = (TextView) findViewById(R.id.updateDescribeTex);
        loadingProgressView = (SpecialProgressBarView) findViewById(R.id.loadingProgressView);
        installImg = (ImageView) findViewById(R.id.installImg);
        cancelImg = (ImageView) findViewById(R.id.cancelImg);

        installImg.setOnClickListener(this);
        cancelImg.setOnClickListener(this);


        // 设置下载描述信息
        versionCodeTex.setText("版本号："+versionCode);
        updateDescribeTex.setText(updateDescribe);

        // 如果不是强制更新有取消按钮
        if (!isForce) {
            cancelImg.setVisibility(View.VISIBLE);
        }

        // 初始化进度条状态
        loadingProgressView.setEndSuccessBackgroundColor(Color.parseColor("#FF6642"))//设置进度完成时背景颜色
                .setEndSuccessDrawable(R.drawable.dl_install, null)//设置进度完成时背景图片
                .setCanEndSuccessClickable(false)//设置进度完成后是否可以再次点击开始
                .setProgressBarColor(Color.parseColor("#9BD6FE"))//进度条颜色
                .setCanDragChangeProgress(false)//是否进度条是否可以拖拽
                .setCanReBack(true)//是否在进度成功后返回初始状态
                .setProgressBarBgColor(Color.parseColor("#EAEAEA"))//进度条背景颜色
                .setProgressBarHeight(loadingProgressView.dip2px(getContext(), 10))//进度条宽度
                .setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getContext().getResources().getDisplayMetrics()))//设置字体大小
                .setStartDrawable(R.drawable.dl_btn, null)//设置开始时背景图片
                .setTextColorSuccess(Color.parseColor("#FFFFFF"))//设置成功时字体颜色
                .setTextColorNormal(Color.parseColor("#FFFFFF"))//设置默认字体颜色
                .setTextColorError(Color.parseColor("#FFFFFF"));//设置错误时字体颜色
        loadingProgressView.setOnAnimationEndListener(this);
        loadingProgressView.setOntextChangeListener(this);
    }

    @Override
    public void onAnimationEnd() {
        // 初始化动画结束，提示用户可以开始执行下载
        loadingProgressView.setProgress(0);
        if (onUpdateListener != null) {
            onUpdateListener.onStarUpdate();
        }

    }

    @Override
    public String onProgressTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
        return progress * 100 / max + "%";
    }

    @Override
    public String onErrorTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
        return "Error";
    }

    @Override
    public String onSuccessTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
        return "OK";
    }

    @Override
    public void onFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 下载结束，显示安装按钮
                loadingProgressView.setVisibility(View.INVISIBLE);
                installImg.setVisibility(View.VISIBLE);
                // 通知用户下载结束
                if (onUpdateListener != null) {
                    onUpdateListener.onFinishUpdate();
                }

            }
        }, 2000);
    }

    private void cancelInstall() {
        if (onUpdateListener != null) {
            onUpdateListener.onCancel();
        }
    }

    /**
     * 下载完成后安装APK
     */
    private void installApk() {
        if (onUpdateListener != null) {
            onUpdateListener.onInstall();
        }
    }

    /**
     * 更新失败
     *
     * @param errorMessage 错误信息
     */
    public void updateFail(String errorMessage) {
        // 加载控件执行error动画
        loadingProgressView.setError();

        // 提示用户下载失败
        if (onUpdateListener != null) {
            onUpdateListener.onUpdateError(errorMessage);
        }

        // 将关闭按钮显示出来，让用户可以点击退出
        if (cancelImg.getVisibility() != View.VISIBLE) {
            cancelImg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置更新进度最大值
     */
    public void setMaxProgress(long maxVal) {
        loadingProgressView.setMax(maxVal);
    }

    /**
     * 设置当前更新的进度位置
     */
    public void setCurrentProgress(long currentVal) {
        loadingProgressView.setProgress(currentVal);
    }

    /**
     * 设置更新状态监听器
     */
    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.installImg:
//                CountlyEventStatisticsOperator.addEvent(CountlyEventStatisticsOperator.VERSION_UPGRADE_GOTO);
                installApk();
                break;
            case R.id.cancelImg:
//                CountlyEventStatisticsOperator.addEvent(CountlyEventStatisticsOperator.VERSION_UPGRADE_CLOSE);
                cancelInstall();
                break;
        }
    }

    /**
     * 更新监听器回调
     */
    public interface OnUpdateListener {
        /**
         * 开始更新前回调
         */
        void onStarUpdate();

        /**
         * 更新完毕后回调
         */
        void onFinishUpdate();

        /**
         * 更新失败回调
         *
         * @param errorMessage 更新失败的错误信息
         */
        void onUpdateError(String errorMessage);

        /**
         * 用户执行安装Apk回调
         */
        void onInstall();

        /**
         * 用户取消安装，或下载失败时回调
         */
        void onCancel();
    }
}
