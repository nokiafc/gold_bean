package com.tianmi.goldbean.net.bean;

public class MerchantBean {
        private String data;
    /**
     * id : 3
     * userId : null
     * merchantsName : 天米
     * merchantsAddr : 邢台
     * merchantsPhone : 16646646
     * merchantsWx :
     * createTime : 2018-11-11T13:43:07.000+0000
     */

    private int id;
    private Object userId;
    private String merchantsName;
    private String merchantsAddr;
    private String merchantsPhone;
    private String merchantsWx;
    private String createTime;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getMerchantsName() {
        return merchantsName;
    }

    public void setMerchantsName(String merchantsName) {
        this.merchantsName = merchantsName;
    }

    public String getMerchantsAddr() {
        return merchantsAddr;
    }

    public void setMerchantsAddr(String merchantsAddr) {
        this.merchantsAddr = merchantsAddr;
    }

    public String getMerchantsPhone() {
        return merchantsPhone;
    }

    public void setMerchantsPhone(String merchantsPhone) {
        this.merchantsPhone = merchantsPhone;
    }

    public String getMerchantsWx() {
        return merchantsWx;
    }

    public void setMerchantsWx(String merchantsWx) {
        this.merchantsWx = merchantsWx;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
