package com.tianmi.goldbean.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tianmi.goldbean.R;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class Utils {

    public static UMWeb getUMWeb(Context context){
        UMImage thumb =  new UMImage(context, R.drawable.icon_app);
       String userRecommendCode = DataUtil.getPreferences("userRecommendCode", "");
        UMWeb  web = new UMWeb("hhttp://www.tianmi0319.com/tianmi/toRegist?invideCode="+userRecommendCode);
        web.setTitle("【捞金豆】我在这里领到了红包，快来一起抢红包吧");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("我在这里领到了红包，快来一起抢红包吧");//描述
        return web;
    }
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
