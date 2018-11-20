package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class MerchantWaterBean implements Serializable{

    /**
     * id : 8
     * goodsName : null
     * userId : 10
     * goodsUrl : http://59.111.105.183/img/2018-11-20/b410b3bac0ee40548cd945c5741ac784.jpg,http://59.111.105.183/img/2018-11-20/b410b3bac0ee40548cd945c5741ac784.jpg,http://59.111.105.183/img/2018-11-20/5d032899ae1442bcbd6226473c08a0bc.jpg,http://59.111.105.183/img/2018-11-20/96c542a7d9ee4a639cfc794d4126a851.jpg,http://59.111.105.183/img/2018-11-20/23c93c05bc344c0db8717fd553d93ed2.jpg,http://59.111.105.183/img/2018-11-20/24dfb78558e34a7c8dfb3cec8acda3ef.jpg
     * remark : null
     * redpackageId : null
     * createTime : 2018-11-20T03:47:43.000+0000
     * goodsStatus : 1
     * auditUserId : 9
     * auditTime : 2018-11-20T03:48:07.000+0000
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
    private int auditUserId;
    private String auditTime;
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

    public int getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(int auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
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
