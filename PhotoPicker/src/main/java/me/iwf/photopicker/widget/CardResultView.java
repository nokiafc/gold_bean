package me.iwf.photopicker.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * Created by xuning on 17/5/3.
 */

public class CardResultView extends FrameLayout {

    @IntDef({ACTION_SELECT, ACTION_ONLY_SHOW})

    //Tell the compiler not to store annotation data in the .class file
    @Retention(RetentionPolicy.SOURCE)

    //Declare the NavigationMode annotation
    public @interface MultiPicAction {}




    public static final int ACTION_SELECT = 1;//该组件用于图片选择
    public static final int ACTION_ONLY_SHOW = 2;//该组件仅用于图片显示

    private int action;


    android.support.v7.widget.RecyclerView recyclerView;
    CardAdapter photoAdapter;
    ArrayList<String> selectedPhotos;
    public CardResultView(Context context) {
        this(context,null,0);
    }

    public CardResultView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
        initData(context,attrs);
        initEvent(context,attrs);
    }

    private void initEvent(Context context, AttributeSet attrs) {

    }

    private void initData(Context context, AttributeSet attrs) {

    }

    private void initView(Context context, AttributeSet attrs) {

        recyclerView = new android.support.v7.widget.RecyclerView(context,attrs);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, OrientationHelper.VERTICAL));
        this.addView(recyclerView);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CardResultView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }


    public void init(Activity context, @MultiPicAction int action, ArrayList<String> photos, int maxLimit, int requestCode, Handler handler){
        this.action = action;
        if (action == CardResultView.ACTION_ONLY_SHOW){//当只用作显示图片时,一行显示4张
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, OrientationHelper.VERTICAL));
        }

        selectedPhotos = new ArrayList<>();

        this.action = action;
        if (photos != null && photos.size() >0){
            selectedPhotos.addAll(photos);
        }
        photoAdapter = new CardAdapter(context, selectedPhotos, requestCode,handler);
        photoAdapter.setMaxLimit(maxLimit);
        photoAdapter.setAction(action);
        recyclerView.setAdapter(photoAdapter);

        //recyclerView.setLayoutFrozen(true);


    }


    public void showPics(List<String> paths){
        if (paths != null){
            selectedPhotos.clear();
            selectedPhotos.addAll(paths);
            photoAdapter.notifyDataSetChanged();
        }

    }








    public  void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (action == ACTION_SELECT){
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
                    //Toast.makeText(getContext(),"取消选择",Toast.LENGTH_LONG).show();
                }
            });
//        }

    }


    public ArrayList<String> getPhotos() {
        return selectedPhotos;
    }


}
