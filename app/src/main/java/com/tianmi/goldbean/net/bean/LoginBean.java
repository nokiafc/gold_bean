package com.tianmi.goldbean.net.bean;

public class LoginBean {

    /**
     * createTime : 2018-11-10T06:35:11.000+0000
     * userRecommendCode : 234714
     * userNo : 201841831711455250
     * userId : 14
     * userSex : 0
     * userPassword : FEB528D2BBA98BB2CB0321BA830AE49B
     * userPhone : 17326888239
     * userRole : 0
     * merchantsFlag : 0
     * recommendCod : 11
     * userAge : 0
     */

    private String createTime;
    private String userRecommendCode;
    private String userNo;
    private int userId;
    private int userSex;
    private String userPassword;
    private String userPhone;
    private int userRole;
    private int merchantsFlag;
    private String recommendCod;
    private int userAge;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserRecommendCode() {
        return userRecommendCode;
    }

    public void setUserRecommendCode(String userRecommendCode) {
        this.userRecommendCode = userRecommendCode;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getMerchantsFlag() {
        return merchantsFlag;
    }

    public void setMerchantsFlag(int merchantsFlag) {
        this.merchantsFlag = merchantsFlag;
    }

    public String getRecommendCod() {
        return recommendCod;
    }

    public void setRecommendCod(String recommendCod) {
        this.recommendCod = recommendCod;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
