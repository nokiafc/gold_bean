package com.tianmi.goldbean.bean;

import java.math.BigDecimal;

public class RedPackage {
    private String  userId;
    private BigDecimal redAmoun;
    private String redCount;
    private String redWay;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getRedAmoun() {
        return redAmoun;
    }

    public void setRedAmoun(BigDecimal redAmoun) {
        this.redAmoun = redAmoun;
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
