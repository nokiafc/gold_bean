package com.tianmi.goldbean.bean;

public class WechatPayBean {

    /**
     * nonce_str : BtaYC1oBIdyE5VE5
     * appid : wx4f059b07213f6ac8
     * sign : 1792C18D864A83696046E3DBD12DF257
     * trade_type : APP
     * return_msg : OK
     * result_code : SUCCESS
     * mch_id : 1518819151
     * return_code : SUCCESS
     * prepay_id : wx27130036753380720e9fc05d2214605655
     */

    private String nonce_str;
    private String appid;//
    private String sign;
    private String trade_type;
    private String return_msg;
    private String result_code;
    private String mch_id;//
    private String return_code;
    private String prepay_id;//

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
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

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
