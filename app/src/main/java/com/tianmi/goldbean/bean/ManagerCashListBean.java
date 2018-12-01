package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class ManagerCashListBean implements Serializable {
    /**
     * userId : 38
     * userPhone : null
     * aduiterId : null
     * aduitTime : null
     * aduitStatus : 0
     * createTime : 2018-12-01 20:39:30
     * amount : 1
     * withdrowNo : 40307b66eefa478c8a2439b08f6620ac
     * backNo : null
     */

    private int userId;
    private Object userPhone;
    private Object aduiterId;
    private Object aduitTime;
    private int aduitStatus;
    private String createTime;
    private int amount;
    private String withdrowNo;
    private Object backNo;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Object userPhone) {
        this.userPhone = userPhone;
    }

    public Object getAduiterId() {
        return aduiterId;
    }

    public void setAduiterId(Object aduiterId) {
        this.aduiterId = aduiterId;
    }

    public Object getAduitTime() {
        return aduitTime;
    }

    public void setAduitTime(Object aduitTime) {
        this.aduitTime = aduitTime;
    }

    public int getAduitStatus() {
        return aduitStatus;
    }

    public void setAduitStatus(int aduitStatus) {
        this.aduitStatus = aduitStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getWithdrowNo() {
        return withdrowNo;
    }

    public void setWithdrowNo(String withdrowNo) {
        this.withdrowNo = withdrowNo;
    }

    public Object getBackNo() {
        return backNo;
    }

    public void setBackNo(Object backNo) {
        this.backNo = backNo;
    }
    //[{"userId":38,"userPhone":null,"aduiterId":null,"aduitTime":null,"aduitStatus":0,"createTime":"2018-12-01 20:39:30","amount":1,"withdrowNo":"40307b66eefa478c8a2439b08f6620ac","backNo":null},{"userId":38,"userPhone":null,"aduiterId":null,"aduitTime":null,"aduitStatus":0,"createTime":"2018-12-01 20:39:34","amount":1,"withdrowNo":"ab05b28039f848418a5ca27bdf5b6bf0","backNo":null},{"userId":38,"userPhone":null,"aduiterId":null,"aduitTime":null,"aduitStatus":0,"createTime":"2018-12-01 20:39:36","amount":1,"withdrowNo":"22e8aba6fe0b441aa5ae13d295348b0f","backNo":null},{"userId":38,"userPhone":null,"aduiterId":null,"aduitTime":null,"aduitStatus":0,"createTime":"2018-12-01 20:39:38","amount":1,"withdrowNo":"dfd54b67780845c9b84d3bd31a4b1c9e","backNo":null},{"userId":38,"userPhone":null,"aduiterId":null,"aduitTime":null,"aduitStatus":0,"createTime":"2018-12-01 20:39:42","amount":10,"withdrowNo":"d96985072ae94ecaa935249e6f047523","backNo":null}]

}
