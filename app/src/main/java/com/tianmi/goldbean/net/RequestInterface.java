package com.tianmi.goldbean.net;

import android.app.Activity;


import com.tianmi.goldbean.bean.AnswerBean;
import com.tianmi.goldbean.bean.GoodsQuestion;
import com.tianmi.goldbean.bean.QuestionAnswer;
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
    //获取服务器版本信息
    public void getVersion(){
        Map<String, Object> map = new HashMap<String, Object>();
        this.post("/version/get", map, activity);
    }
    //获取图片验证码
    public void getImgCode(String userPhone, String source){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("source", source);
        this.postByte("/getCaptcha", map, activity);
    }
    //获取短信验证码
    public void getSMS(String userPhone, String source, String captchaCode){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("source", source);
        map.put("captchaCode", captchaCode);
        this.post("/sendSMSCode", map, activity);
    }
    //验证短信是否正确
    public void verifySMS(String userPhone, String smsCode, String source){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("smsCode", smsCode);
        map.put("source", source);
        this.post("/checkSmsCode", map, activity);
    }
    //修改密码
    public void changePsd(String userPhone, String userPassword){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("userPassword", userPassword);
        this.post("/updatePwdByPhone", map, activity);
    }
    //微信支付
    public void wechatPay(int userId, String amount){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("amount", amount);
        this.post("/user/wx_recharge", map, activity);
    }

    public void alipay(int userId, String amount){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("amount", amount);
        this.post("/user/zfb_recharge", map, activity);
    }
    //下载apk更新
    public void update(){

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
    public void loginOut(int userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        this.post("/user/logout", map , activity);
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


    public void getMainInfo(int  pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/index", map, activity);
    }
    public void getMainInfoUp(int  pageNo, int pageSize, int topFlag){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("topFlag", topFlag);
        this.post("/index", map, activity);
    }
    public void getGoodsDetail(int goodsId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goodsId);
        this.post("/user/getGoodsDetail", map, activity);
    }
    public void goodsComment(int goodsId, String remark, String userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goodsId);
        map.put("remark", remark);
        map.put("userId", userId);
        this.post("/user/goodsComment", map, activity);
    }

    public void getMerchantsInfo(int merchant_userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("merchant_userId", merchant_userId);
        this.post("/getMerchantsInfo", map, activity);
    }

    //获取问题
    public void getQuestions(int userId, int goodsId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("goodsId", goodsId);
        this.post("/user/getQuestions", map, activity);
    }
    //回答问题
    public void answer(List<QuestionAnswer> list, int userId, int goodsId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("questionAnswer", list);
        map.put("userId", userId);
        map.put("goodsId", goodsId);
        this.post("/user/answer", map, activity);
    }
    //获取我的页面信息
    public void getMyInfo(int userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        this.post("/user/getUserInfo", map, activity);
    }
    //获取商品列表
    public void getGoodsList(int goodsState, int  pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsState", goodsState);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/manager/getGoodsApplys", map, activity);
    }
    //管理员审核
    public void shenhe(int userId, int goodsId, int goodsState, int topFlag){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsState", goodsState);
        map.put("userId", userId);
        map.put("goodsId", goodsId);
        map.put("topFlag", topFlag);
        this.post("/manager/audit", map, activity);
    }
    //获取用户信息
    public void managerGetUserInfo(String userPhone, int  pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPhone", userPhone);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/manager/getUsers", map, activity);
    }
    //移除管理员
    public void removeSonManager(int userManageId, int loginUserId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userManageId", userManageId);
        map.put("loginUserId", loginUserId);
        this.post("/manager/removeManager", map, activity);
    }
    //设置管理员
    public void addSonManager(int userManageId, int loginUserId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userManageId", userManageId);
        map.put("loginUserId", loginUserId);
        this.post("/manager/setManager", map, activity);
    }


    //账户流水消息
    public void getAccount(int userId, int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/user/getAccount", map, activity);
    }
    //商品审核上架消息
    public void getGoodsMsg(int userId, int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/user/getGoodsMsg", map, activity);
    }
    //提现申请
    public void cash(int userId, String amount){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("amount", amount);
        this.post("/user/withdrowApply", map, activity);
    }
    //管理员查询提现列表
    public void managerCashList(int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        this.post("/manager/getWithdrowList", map, activity);
    }
}
