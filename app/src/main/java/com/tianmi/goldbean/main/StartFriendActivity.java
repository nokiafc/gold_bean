package com.tianmi.goldbean.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.FriendInfoBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class StartFriendActivity extends BaseActivity {
    private String goodsId = "";
    private TextView title, redNum, redMoney, titleNum;
    private MultiPickResultView multiPick;
    private List<String> list = new ArrayList<String>();
    private Button copy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_friend);
        initTitle("分享朋友圈信息");
        goodsId = getIntent().getStringExtra("goodsId");
        init();
        getFriendInfo(Integer.parseInt(goodsId));
    }

    private void init() {
        copy = findViewById(R.id.copy_btn);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制图片到本地
                for (int i = 0; i < list.size(); i++) {
                    final String url = list.get(i);
                    final int y = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getBitmap(url, y);
                        }
                    }).start();

                }
            }
        });
        titleNum = findViewById(R.id.title_num);
        title = findViewById(R.id.title);
        multiPick = (MultiPickResultView) findViewById(R.id.multiPick);
        redNum = findViewById(R.id.redNum);
        redMoney = findViewById(R.id.redMoney);

    }

    private void getFriendInfo(int goodsID) {
        RequestInterface request = new RequestInterface(this);
        request.getFriendInfo(goodsID);
        request.setCallback(new JsonCallback<FriendInfoBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(FriendInfoBean bean, String message) throws IOException {
                if (bean != null) {


                    String urls = bean.getRetweetImage();
                    String[] urlStr = urls.split(",");
                    for (int i = 0; i < urlStr.length; i++) {
                        if (urlStr[i] != null && !urlStr[i].equals("")) {
                            list.add(urlStr[i]);
                        }
                    }

                    for (int i = 0; i < list.size(); i++) {
                        Log.d("FC", list.get(i));
                    }
                    multiPick.init(StartFriendActivity.this, MultiPickResultView.ACTION_ONLY_SHOW, (ArrayList<String>) list, 5, 0);
                    redNum.setText("红包剩余个数" + bean.getRetweetRemainCount());
                    redMoney.setText("红包剩余金额" + bean.getRetweetRemainBonus());
                    title.setText("商品转发标题   :" + bean.getRemark());
                    titleNum.setText("商品转发号：   " + bean.getRetweetNo());
                }

            }
        });

    }

    private void getBitmap(String url, final int i) {
        try {
            Bitmap b = Glide.with(this).load(url).asBitmap().centerCrop().into(150, 150).get();
            Log.d("FC", i + "----");
            saveBitmap(b, i);
        } catch (Exception e) {

        }
    }


    private void saveBitmap(Bitmap bmp, int picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);


    }
    }



