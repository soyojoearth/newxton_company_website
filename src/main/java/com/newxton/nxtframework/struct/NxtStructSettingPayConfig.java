package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructSettingPayConfig {

    //微信支付APPID
    public String wxpayAPPID;

    //微信支付商户号
    public String wxpayClinetID;

    //微信支付商户密钥
    public String wxpaySecretKey;

    //商户私钥
    public String alipayAPPID;

    //商户私钥
    public String alipaySecretKey;

    //支付宝公钥
    public String alipayPublicKey;

    public String getWxpayAPPID() {
        return wxpayAPPID;
    }

    public void setWxpayAPPID(String wxpayAPPID) {
        this.wxpayAPPID = wxpayAPPID;
    }

    public String getWxpayClinetID() {
        return wxpayClinetID;
    }

    public void setWxpayClinetID(String wxpayClinetID) {
        this.wxpayClinetID = wxpayClinetID;
    }

    public String getWxpaySecretKey() {
        return wxpaySecretKey;
    }

    public void setWxpaySecretKey(String wxpaySecretKey) {
        this.wxpaySecretKey = wxpaySecretKey;
    }

    public String getAlipayAPPID() {
        return alipayAPPID;
    }

    public void setAlipayAPPID(String alipayAPPID) {
        this.alipayAPPID = alipayAPPID;
    }

    public String getAlipaySecretKey() {
        return alipaySecretKey;
    }

    public void setAlipaySecretKey(String alipaySecretKey) {
        this.alipaySecretKey = alipaySecretKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

}
