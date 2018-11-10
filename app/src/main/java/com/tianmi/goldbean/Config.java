package com.tianmi.goldbean;

public class Config {
    public static String BASE_URL = "";
    public static boolean IS_DEBUG = true;
    static {
        if (IS_DEBUG) {//开发
            BASE_URL = "http://172.28.153.249:8081/tianmi";

        } else {//线上
            BASE_URL = "http://www.tianmi.com";
        }
    }
}
