package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class ManagerVerifyBean implements Serializable {

    /**
     * id : 5
     * goodsName : null
     * userId : 3
     * goodsUrl : http://59.111.105.170/img/2018-11-16/8f7b983619bb4c5aa212d34dad2a3f60.jpg,http://59.111.105.170/img/2018-11-16/8f7b983619bb4c5aa212d34dad2a3f60.jpg
     * remark : null
     * redpackageId : null
     * createTime : 2018-11-16T13:12:26.000+0000
     * goodsStatus : 0
     * auditUserId : null
     * auditTime : null
     * downTime : null
     * remainRed : null
     */

    private int id;
    private Object goodsName;
    private int userId;
    private String goodsUrl;
    private Object remark;
    private Object redpackageId;
    private String createTime;
    private int goodsStatus;
    private Object auditUserId;
    private Object auditTime;
    private Object downTime;
    private Object remainRed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(Object goodsName) {
        this.goodsName = goodsName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getRedpackageId() {
        return redpackageId;
    }

    public void setRedpackageId(Object redpackageId) {
        this.redpackageId = redpackageId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public Object getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Object auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Object getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Object auditTime) {
        this.auditTime = auditTime;
    }

    public Object getDownTime() {
        return downTime;
    }

    public void setDownTime(Object downTime) {
        this.downTime = downTime;
    }

    public Object getRemainRed() {
        return remainRed;
    }

    public void setRemainRed(Object remainRed) {
        this.remainRed = remainRed;
    }
}
