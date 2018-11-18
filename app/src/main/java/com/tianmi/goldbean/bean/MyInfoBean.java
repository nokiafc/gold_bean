package com.tianmi.goldbean.bean;

public class MyInfoBean {
    //{"accountBalance":"0.00","accountFrozenAmount":null,"totalCount":0,"monthCount":0}
    private String accountBalance;
    private String accountFrozenAmount;
    private int totalCount;
    private int monthCount;

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountFrozenAmount() {
        return accountFrozenAmount;
    }

    public void setAccountFrozenAmount(String accountFrozenAmount) {
        this.accountFrozenAmount = accountFrozenAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }
}
