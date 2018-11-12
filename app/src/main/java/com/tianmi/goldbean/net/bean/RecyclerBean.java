package com.tianmi.goldbean.net.bean;

public class RecyclerBean {

    /**
     * id : 1
     * goodsName : null
     * userId : 3
     * goodsUrl : ,http://59.111.105.170/img//2018-11-12/311da743940443b78b2fc3c3dd0b9e19.jpg,http://59.111.105.170/img//2018-11-12/e6ccf32b074344d090a1875e6a2c4d90.jpg
     * remark : null
     * redpackageId : null
     * createTime : 2018-11-12T09:35:17.000+0000
     * goodsStatus : 1
     * auditUserId : null
     * auditTime : 2018-11-12T10:57:56.000+0000
     * downTime : null
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
    private String auditTime;
    private Object downTime;
    private String remainRed;

    public String getRemainRed() {
        return remainRed;
    }

    public void setRemainRed(String remainRed) {
        this.remainRed = remainRed;
    }

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
}
