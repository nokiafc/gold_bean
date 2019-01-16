package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class FriendInfoBean implements Serializable {

    /**
     * goodsId : 30
     * retweetNo : 376995d6662d4c339b898e89e5afa22c
     * remark : 嘻嘻嘻
     * retweetImage : http://www.tianmi0319.com/img/2019-01-16/20b604b739b9483296564448bac9fddf.jpg
     * retweetTotalCount : 1
     * retweetTotalBonus : 1
     * retweetBonus : 1
     * retweetRemainBonus : 1
     * retweetRemainCount : 1
     * auditStatus : 0
     * auditUserId : null
     * auditTime : null
     * userId : 25
     * createTime : 2019-01-16 22:08:42
     * downTime : null
     * pageNo : null
     * pageSize : null
     */

    private int goodsId;
    private String retweetNo;
    private String remark;
    private String retweetImage;
    private int retweetTotalCount;
    private int retweetTotalBonus;
    private int retweetBonus;
    private int retweetRemainBonus;
    private int retweetRemainCount;
    private int auditStatus;
    private Object auditUserId;
    private Object auditTime;
    private int userId;
    private String createTime;
    private Object downTime;
    private Object pageNo;
    private Object pageSize;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getRetweetNo() {
        return retweetNo;
    }

    public void setRetweetNo(String retweetNo) {
        this.retweetNo = retweetNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRetweetImage() {
        return retweetImage;
    }

    public void setRetweetImage(String retweetImage) {
        this.retweetImage = retweetImage;
    }

    public int getRetweetTotalCount() {
        return retweetTotalCount;
    }

    public void setRetweetTotalCount(int retweetTotalCount) {
        this.retweetTotalCount = retweetTotalCount;
    }

    public int getRetweetTotalBonus() {
        return retweetTotalBonus;
    }

    public void setRetweetTotalBonus(int retweetTotalBonus) {
        this.retweetTotalBonus = retweetTotalBonus;
    }

    public int getRetweetBonus() {
        return retweetBonus;
    }

    public void setRetweetBonus(int retweetBonus) {
        this.retweetBonus = retweetBonus;
    }

    public int getRetweetRemainBonus() {
        return retweetRemainBonus;
    }

    public void setRetweetRemainBonus(int retweetRemainBonus) {
        this.retweetRemainBonus = retweetRemainBonus;
    }

    public int getRetweetRemainCount() {
        return retweetRemainCount;
    }

    public void setRetweetRemainCount(int retweetRemainCount) {
        this.retweetRemainCount = retweetRemainCount;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getDownTime() {
        return downTime;
    }

    public void setDownTime(Object downTime) {
        this.downTime = downTime;
    }

    public Object getPageNo() {
        return pageNo;
    }

    public void setPageNo(Object pageNo) {
        this.pageNo = pageNo;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }
}
