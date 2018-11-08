package me.iwf.photopicker.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * Created by xuning on 17/6/6.
 */

public class CustomResultView extends FrameLayout {


    @IntDef({ACTION_SELECT, ACTION_ONLY_SHOW})

    @Retention(RetentionPolicy.SOURCE)

    public @interface MultiPicAction {
    }


    public static final int ACTION_SELECT = 1;//该组件用于图片选择
    public static final int ACTION_ONLY_SHOW = 2;//该组件仅用于图片显示

    private int action;

    CustomGridView recyclerView;
    CustomAdapter photoAdapter;
    ArrayList<String> selectedPhotos;

    public CustomResultView(Context context) {
        this(context, null, 0);
    }

    public CustomResultView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initData(context, attrs);
        initEvent(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomResultView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initEvent(Context context, AttributeSet attrs) {

    }

    private void initData(Context context, AttributeSet attrs) {

    }

    private void initView(Context context, AttributeSet attrs) {
        recyclerView = new CustomGridView(context, attrs);
        recyclerView.setNumColumns(5);
        this.addView(recyclerView);
    }

    public void init(Activity context, @CardResultView.MultiPicAction int action, ArrayList<String> photos, int maxLimit, int requestCode, Handler handler) {
        this.action = action;
        if (action == CardResultView.ACTION_ONLY_SHOW) {//当只用作显示图片时,一行显示4张
            recyclerView.setNumColumns(5);
        }

        selectedPhotos = new ArrayList<>();

        this.action = action;
        if (photos != null && photos.size() > 0) {
            selectedPhotos.addAll(photos);
        }
        photoAdapter = new CustomAdapter(context, selectedPhotos, requestCode, handler);
        photoAdapter.setMaxLimit(maxLimit);
        photoAdapter.setAction(action);
        recyclerView.setAdapter(photoAdapter);
        //recyclerView.setLayoutFrozen(true);
    }

    public void showPics(List<String> paths) {
        if (paths != null) {
            selectedPhotos.clear();
            selectedPhotos.addAll(paths);
            photoAdapter.notifyDataSetChanged();
        }

    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {
                photoAdapter.refresh(photos);
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {
                photoAdapter.refresh(photos);
            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
                selectedPhotos.clear();
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPickCancle() {
            }
        });

    }


    public ArrayList<String> getPhotos() {
        return selectedPhotos;
    }

}
