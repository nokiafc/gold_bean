package com.tianmi.goldbean.bean;

import java.io.Serializable;

public class MemberUpInfoBean implements Serializable {

    /**
     * userId : 25
     * retweetNo : 376995d6662d4c3396898e89e5afa22c
     * retweetImage : http://www.tianmi0319.com/img/2019-01-17/90bc3b625a6841c4b38e76ccaba8a26a.jpg
     * merchantRemark : null
     * merchantObjectionTime : null
     * auditStatus : 0
     * auditRemark : null
     * auditTime : null
     * auditUserId : null
     * createTime : null
     * pageNo : null
     * pageSize : null
     */

    private int userId;
    private String retweetNo;
    private String retweetImage;
    private Object merchantRemark;
    private Object merchantObjectionTime;
    private int auditStatus;
    private Object auditRemark;
    private Object auditTime;
    private Object auditUserId;
    private Object createTime;
    private Object pageNo;
    private Object pageSize;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRetweetNo() {
        return retweetNo;
    }

    public void setRetweetNo(String retweetNo) {
        this.retweetNo = retweetNo;
    }

    public String getRetweetImage() {
        return retweetImage;
    }

    public void setRetweetImage(String retweetImage) {
        this.retweetImage = retweetImage;
    }

    public Object getMerchantRemark() {
        return merchantRemark;
    }

    public void setMerchantRemark(Object merchantRemark) {
        this.merchantRemark = merchantRemark;
    }

    public Object getMerchantObjectionTime() {
        return merchantObjectionTime;
    }

    public void setMerchantObjectionTime(Object merchantObjectionTime) {
        this.merchantObjectionTime = merchantObjectionTime;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Object getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(Object auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Object getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Object auditTime) {
        this.auditTime = auditTime;
    }

    public Object getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Object auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
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
