package com.tianmi.goldbean.config;

public class Config {
    public static String BASE_URL = "";
    public static boolean IS_DEBUG = true;
    public static String PICTURE_URL = "http://59.111.61.145/tianmi/user/upload";
    static {
        if (IS_DEBUG) {//开发
            BASE_URL = "http://59.111.61.145/tianmi";

        } else {//线上
            BASE_URL = "http://www.tianmi.com";
        }
    }
}
