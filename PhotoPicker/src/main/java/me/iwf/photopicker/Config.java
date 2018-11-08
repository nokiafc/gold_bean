package me.iwf.photopicker;

/**
 * Created by fangchao on 2016/12/13.
 */

public class Config {
    public static boolean IS_DEBUG = false;

    public static String BASE_URL = "";
    public static String BASE_URL_IMAGE_UPLOAD = "";//上传图片
    public static String BASE_URL_UPDATE = "";//更新地址
    public static String SHOW_SEVER_IMAGE = "";//展示服务器地址
    public static String IS_SEVER_IMAGE = "";//判断是否是服务器图片

    static {
        if (IS_DEBUG) {//开发
            IS_SEVER_IMAGE = "file.qianbao.com";
            SHOW_SEVER_IMAGE = "http://file.qianbao.com/";
            BASE_URL = "https://sit6-apis.qianbao.com/chedai";
//            BASE_URL = "https://dev-apis.qianbao.com/chedai";
            BASE_URL_IMAGE_UPLOAD = "https://sit9-apis.qianbao.com/basicservice/v1/intranet/weed";
//            BASE_URL_IMAGE_UPLOAD = "https://dev-apis.qianbao.com/basicservice/v1/weed";
            BASE_URL_UPDATE = "http://172.28.38.57:6105";

        } else {//线上
            IS_SEVER_IMAGE = "img0.qianbao.com";
            SHOW_SEVER_IMAGE = "http://img0.qianbao.com/";
            BASE_URL_IMAGE_UPLOAD = "https://apis.qianbao.com/basicservice/v1/weed";
            BASE_URL = "https://apis.qianbao.com/chedai";
            BASE_URL_UPDATE = "https://api01.qianbao.com";
        }
    }
}
