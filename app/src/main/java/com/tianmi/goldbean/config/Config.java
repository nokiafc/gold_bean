package com.tianmi.goldbean.config;

public class Config {
    public static String APP_ID = "wxab96184a9724d08d";
    public static String APP_SECRECT = "2f98c59650bdb3ce51b0d0d7e383a554";
    public static String BASE_URL = "";
    public static boolean IS_DEBUG = true;
    public static String SHARE_URL = "http://www.tianmi0319.com/tianmi/toRegist?invideCode=";
    public static String PICTURE_URL = "http://www.tianmi0319.com/tianmi/user/upload";
    static {
        if (IS_DEBUG) {//开发
            BASE_URL = "http://www.tianmi0319.com/tianmi";

        } else {//线上
        }
    }
}
