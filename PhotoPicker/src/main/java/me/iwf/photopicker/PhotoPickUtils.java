package me.iwf.photopicker;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class PhotoPickUtils {
    public static int maxLimit = 9;
    public static void setMaxLimit(int max){
        maxLimit =  max;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data,PickHandler pickHandler ) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoPreview.REQUEST_CODE){//如果是预览与删除后返回
                if (data != null) {
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    pickHandler.onPreviewBack(photos);
                } else {
                   // pickHandler.onPickFail("选择图片失败");
                }

            }else {//第一次，选择图片后返回

                if (data != null) {
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    pickHandler.onPickSuccess(photos);
                } else {
                    pickHandler.onPickFail("选择图片失败");
                }
            }
        }else {

            if (requestCode == PhotoPicker.REQUEST_CODE){
                pickHandler.onPickCancle();
            }
        }


    }

    public static void startPick(Activity context,ArrayList<String> photos, int requestCode){
        PhotoPicker.builder()
                .setPhotoCount(maxLimit)
                .setShowCamera(true)
                .setShowGif(true)
                .setSelected(photos)
                .setPreviewEnabled(true)
                .start(context, requestCode);
    }



    public interface  PickHandler{
        void onPickSuccess(ArrayList<String> photos);
        void onPreviewBack(ArrayList<String> photos);
        void onPickFail(String error);
        void onPickCancle();
    }
}
