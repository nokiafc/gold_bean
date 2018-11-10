package com.tianmi.goldbean.net;

import android.app.Activity;


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
        this.post("/user/login", map, activity);
    }
    public void register(String userPhone, String userPassword, int userSex, int userAge, String recommendCod) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("userPassword", userPassword);
        map.put("userSex", userSex);
        map.put("userAge", userAge);
        map.put("recommendCod", recommendCod);
        this.post("/user/register", map , activity);
    }

}
