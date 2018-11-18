package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ImmersedStatusbarUtils {
    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        if (activity == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
         if (titleViewGroup == null)
             return;
         // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
         int statusBarHeight = getStatusBarHeight(activity);
         titleViewGroup.setPadding(0, statusBarHeight, 0, 0);

     }

    /**
 37      * 获取状态栏高度
 38      *
 39      * @param context
 40      * @return
 41      */
         private static int getStatusBarHeight(Context context) {
             int result = 0;
             int resourceId = context.getResources().getIdentifier(
                     "status_bar_height", "dimen", "android");
             if (resourceId > 0) {
                 result = context.getResources().getDimensionPixelSize(resourceId);
             }
             return result;
         }

}
