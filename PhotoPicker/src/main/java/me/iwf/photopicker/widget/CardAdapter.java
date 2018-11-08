package me.iwf.photopicker.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.R;

/**
 * Created by xuning on 17/5/3.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private int maxLimit = -1;

    private Context mContext;


    public void setAction(@MultiPickResultView.MultiPicAction int action) {
        this.action = action;
    }

    private int action;


    private int requestCode;

    private Handler handler;

    public CardAdapter(Context mContext, ArrayList<String> photoPaths, int requestCode, Handler handler) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.requestCode = requestCode;
        inflater = LayoutInflater.from(mContext);
        padding = dip2Px(8);
        this.handler = handler;

    }

    public void add(ArrayList<String> photoPaths) {
        if (photoPaths != null && photoPaths.size() > 0) {
            this.photoPaths.addAll(photoPaths);
            notifyDataSetChanged();
        }

    }

    public void setMaxLimit(int max) {
        this.maxLimit = max;
    }

    public void refresh(ArrayList<String> photoPaths) {
        this.photoPaths.clear();
        if (photoPaths != null && photoPaths.size() > 0) {
            this.photoPaths.addAll(photoPaths);
        }
        notifyDataSetChanged();
    }


    @Override
    public CardAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.__picker_item_photo, parent, false);
        return new CardAdapter.PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        if (action == MultiPickResultView.ACTION_SELECT) {
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ivPhoto.getLayoutParams();

//      holder.ivPhoto.setPadding(padding,padding,padding,padding);


            if (position == getItemCount() - 1) {//最后一个始终是+号，点击能够跳去添加图片
                Glide.with(mContext)
                        .load("")
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.icon_up_photo)
                        .error(R.drawable.icon_up_photo)
                        .into(holder.ivPhoto);
                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (maxLimit == -1) {
                            maxLimit = 9;
                        }
                        if (photoPaths != null && photoPaths.size() == maxLimit) {
                            Toast.makeText(mContext, "最多只能选择" + maxLimit + "张图片", Toast.LENGTH_SHORT).show();
                        } else {
                            int readStoragePermissionState = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
                            int cameraPermissionState = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);

                            if (readStoragePermissionState != PackageManager.PERMISSION_GRANTED || cameraPermissionState != PackageManager.PERMISSION_GRANTED) {

                            } else {
                                PhotoPickUtils.setMaxLimit(maxLimit);
                                PhotoPickUtils.startPick((Activity) mContext, photoPaths, requestCode);
                            }

                        }
                    }
                });

                holder.deleteBtn.setVisibility(View.GONE);
                holder.ivPhoto.setVisibility(View.GONE);

            } else {

                if (photoPaths.get(position).contains("http")) {
                    Glide.with(mContext)
                            .load(photoPaths.get(position))
                            .centerCrop()
                            .thumbnail(0.1f)
                            // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                            .placeholder(R.drawable.__picker_default_weixin)
                            .error(R.drawable.__picker_ic_broken_image_black_48dp)
                            .into(holder.ivPhoto);
                } else {
                    Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
                    Glide.with(mContext)
                            .load(uri)
                            .centerCrop()
                            .thumbnail(0.1f)
                            // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                            .placeholder(R.drawable.__picker_default_weixin)
                            .error(R.drawable.__picker_ic_broken_image_black_48dp)
                            .into(holder.ivPhoto);
                }


                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoPaths.remove(position);
                        notifyDataSetChanged();
                    }
                });

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
            }
        } else if (action == MultiPickResultView.ACTION_ONLY_SHOW) {
            //Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
            //Uri uri = Uri.parse(photoPaths.get(position));
//            Log.e("pic", photoPaths.get(position));

            if (photoPaths.get(position).contains("http")) {
                Glide.with(mContext)
                        .load(photoPaths.get(position))
                        .centerCrop()
                        .thumbnail(0.1f)
                        // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                        .placeholder(R.drawable.__picker_default_weixin)
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);
            } else {
                Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                        .placeholder(R.drawable.__picker_default_weixin)
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);
            }


//            Glide.with(mContext)
//                    .load(photoPaths.get(position))
//                    .centerCrop()
//                    .thumbnail(0.1f)
//                    // .bitmapTransform(new RoundedCornersTransformation(mContext,4,0))
//                    .placeholder(R.drawable.__picker_default_weixin)
//                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
//                    .into(holder.ivPhoto);

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

//                                    Intent intent = new Intent();
//                                    intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, photoPaths);
//                                    setResult(RESULT_OK, intent);

                                    Message message = new Message();
                                    message.what = 999;
                                    message.arg1 = requestCode;
                                    message.obj = photoPaths.get(position);
                                    handler.sendMessage(message);
//                                    photoPaths.remove(position);
//                                    notifyDataSetChanged();

                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

                    return true;
                }
            });

        }
    }

    public int dip2Px(int dip) {
        // px/dip = density;
        float density = mContext.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    int padding;


    @Override
    public int getItemCount() {
        return action == MultiPickResultView.ACTION_SELECT ? photoPaths.size() + 1 : photoPaths.size();
    }


    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;
        public View cover;
        public View deleteBtn;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            vSelected.setVisibility(View.GONE);
            cover = itemView.findViewById(R.id.cover);
            cover.setVisibility(View.GONE);
            deleteBtn = itemView.findViewById(R.id.v_delete);
            deleteBtn.setVisibility(View.GONE);
        }
    }

}
