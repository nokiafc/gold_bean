package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class AccountWaterBean implements Serializable{

    /**
     * userId : 9
     * dealAmount : 0.02
     * dealSource : 1
     * dealNo : 5e4a66b17afa41598ad06fa7a3a630f1
     * thirdDealNo : 2018111922001468121008861312
     * createTime : 2018-11-19T15:46:40.000+0000
     * modifyTime : null
     */

    private int userId;
    private double dealAmount;
    private int dealSource;
    private String dealNo;
    private String thirdDealNo;
    private String createTime;
    private Object modifyTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public int getDealSource() {
        return dealSource;
    }

    public void setDealSource(int dealSource) {
        this.dealSource = dealSource;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public String getThirdDealNo() {
        return thirdDealNo;
    }

    public void setThirdDealNo(String thirdDealNo) {
        this.thirdDealNo = thirdDealNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Object modifyTime) {
        this.modifyTime = modifyTime;
    }
}
