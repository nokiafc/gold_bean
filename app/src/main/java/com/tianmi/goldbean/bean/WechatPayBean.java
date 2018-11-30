package com.tianmi.goldbean.bean;

import com.google.gson.annotations.SerializedName;

public class WechatPayBean {

    /**
     * package : Sign=WXPay
     * appid : wxab96184a9724d08d
     * sign : 2B9332B492C535D444BFD0F52D3AECEA
     * partnerid : 1518819151
     * prepayid : wx30224859743011896329b4b52368633875
     * noncestr : MC42MDI4MTI5NjM3Nzk5NDE5OjpGcm
     * timestamp : 1543589339
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
