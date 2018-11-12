package com.tianmi.goldbean.bean;

import java.math.BigDecimal;

public class RedPackage {
    private String  userId;
    private BigDecimal redAmount;
    private String redCount;
    private String redWay;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(BigDecimal redAmount) {
        this.redAmount = redAmount;
    }

    public String getRedCount() {
        return redCount;
    }

    public void setRedCount(String redCount) {
        this.redCount = redCount;
    }

    public String getRedWay() {
        return redWay;
    }

    public void setRedWay(String redWay) {
        this.redWay = redWay;
    }
}
