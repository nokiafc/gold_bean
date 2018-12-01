package com.tianmi.goldbean.config;

public class Config {
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
