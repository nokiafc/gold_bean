package com.tianmi.goldbean.Utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuning on 17/2/22.
 */

public class UpdateManager {

    private Handler handler;

    public UpdateManager(Handler handler) {
        this.handler = handler;
    }
    private boolean mIsCancel = false;

    public synchronized void downloadApkFile(final String appPackageUrl) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String savePath = Environment.getExternalStorageDirectory() + "/goldbean.apk";
                File file = new File(savePath);
                if (file.exists()) {
                    file.delete();
                }
                try {
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        URL serverURL = new URL(appPackageUrl);
                        HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
                        connect.setReadTimeout(10000);
                        connect.setConnectTimeout(10000);
                        BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
                        File apkfile = new File(savePath);
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(apkfile));

                        int fileLength = connect.getContentLength();
                        int downLength = 0;
                        int progress = 0;
                        int n;
                        byte[] buffer = new byte[1024];
                        while ((n = bis.read(buffer, 0, buffer.length)) != -1) {
                            bos.write(buffer, 0, n);
                            downLength += n;

                            Message msg = new Message();
                            msg.what = 0;
                            msg.arg1 = downLength;
                            msg.arg2 = fileLength;
                            handler.sendMessage(msg);
                        }
                        bis.close();
                        bos.close();
                        connect.disconnect();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg2 = 10;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


}
