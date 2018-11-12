package com.tianmi.goldbean.net;

import android.app.Activity;


import com.tianmi.goldbean.bean.GoodsQuestion;
import com.tianmi.goldbean.bean.RedPackage;
import com.tianmi.goldbean.bean.UserGoods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangchao on 2016/12/14.
 */

public class RequestInterface extends BaseRequest {
    private Activity activity;

    public RequestInterface(Activity activity) {
        this.activity = activity;
    }

    public void login(String userPhone, String userPassword) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("userPassword", userPassword);
        this.post("/login", map, activity);
    }
    public void register(String userPhone, String userPassword, int userSex, int userAge, String recommendCod) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("userPassword", userPassword);
        map.put("userSex", userSex);
        map.put("userAge", userAge);
        map.put("recommendCod", recommendCod);
        this.post("/register", map , activity);
    }
    public void openMerchant(int userId, String merchantsName, String merchantsAddr, String merchantsPhone, String merchantsWx){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("merchantsName", merchantsName);
        map.put("merchantsAddr", merchantsAddr);
        map.put("merchantsPhone", merchantsPhone);
        map.put("merchantsWx", merchantsWx);
        this.post("/user/createMerchants", map , activity);

    }
    public void publishGoods(UserGoods userGoods, List<GoodsQuestion> list, RedPackage redPackage){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userGoods", userGoods);
        map.put("questionList", list);
        map.put("redPackage", redPackage);
        this.post("/merchants/publishGoods", map, activity);
    }

}