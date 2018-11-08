package me.iwf.photopicker.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by fangchao on 2017/2/13.
 */

public class BitmapUtil {
    public static String getPath(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int with = options.outWidth;
        int heigth = options.outHeight;

        options.inJustDecodeBounds = false;
        float hh = 1000f;
        float ww = 600f;

        int be = 1;
        if(with > heigth && with > ww){
            be = (int) (options.outWidth / ww);
        }else if(heigth > with && heigth > hh){
            be = (int) (options.outHeight / hh);
        }
        if(be <= 0){
            be = 1;
        }

        options.inSampleSize = be;//设置采样率
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        bitmap = BitmapFactory.decodeFile(path, options);

        File appDir = new File(Environment.getExternalStorageDirectory(), "cheya");
        if(!appDir.exists()){
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis()+".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            if(!bitmap.isRecycled()){
                bitmap.recycle();
                bitmap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }
}
