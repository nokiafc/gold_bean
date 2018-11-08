package me.iwf.photopicker.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.R;

/**
 * Created by xuning on 17/6/6.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<String> photoPaths;

    private int maxLimit = -1;

    private Context mContext;

    private int action;


    private int requestCode;

    private Handler handler;


    public CustomAdapter(Context context, ArrayList<String> selectedPhotos, int requestCode, Handler handler) {
        this.photoPaths = selectedPhotos;
        this.mContext = context;
        this.requestCode = requestCode;
        padding = dip2Px(8);
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return photoPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_custom,null);
            holder = new ViewHolder();
            holder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (photoPaths.get(position).contains("http")) {
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.__picker_default_weixin)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);
        } else {
            Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
            Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.__picker_default_weixin)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);
        }


        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPreview.builder()
                        .setPhotos(photoPaths)
                        .setAction(action)
                        .setCurrentItem(position)
                        .start((Activity) mContext);
            }
        });
        holder.ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog(mContext).builder().setTitle("提示")
                        .setCancelable(false)
                        .setMsg("确定要删除图片?")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(photoPaths.get(position).contains("http")){
                                    Toast.makeText(mContext, "客户证件照无法删除", Toast.LENGTH_SHORT).show();
                                }else {
                                    Message message = new Message();
                                    message.what = 999;
                                    message.arg1 = requestCode;
                                    message.obj = photoPaths.get(position);
                                    handler.sendMessage(message);
                                }


                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                return true;
            }
        });
        return convertView;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public void setAction(@MultiPickResultView.MultiPicAction int action) {
        this.action = action;
    }

    public void refresh(ArrayList<String> photoPaths) {
        this.photoPaths.clear();
        if (photoPaths != null && photoPaths.size() > 0) {
            this.photoPaths.addAll(photoPaths);
        }
        notifyDataSetChanged();
    }


    public int dip2Px(int dip) {
        // px/dip = density;
        float density = mContext.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    int padding;

    class ViewHolder {
        ImageView ivPhoto;
    }
}
