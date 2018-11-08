package me.iwf.photopicker.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.iwf.photopicker.R;

/**
 * Created by xuning on 17/5/16.
 */

public class GlideUtils {

    private static GlideUtils instance;

    public static GlideUtils getInstance() {
        if (instance == null) {
            synchronized (GlideUtils.class) {
                if (instance == null) {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }

    public synchronized void LoadBitmap(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .centerCrop()
                .thumbnail(0.01f)
                .override(5, 5)
                // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                .placeholder(R.drawable.__picker_default_weixin)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(imageView);
    }
}
