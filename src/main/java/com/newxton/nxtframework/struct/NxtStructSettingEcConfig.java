package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructSettingEcConfig {

    //推荐搜索关键词 用 , 隔开
    public String keywords;

    //1 下单减库存 2 付款减库存
    public Integer inventoryUpdateType = 1;

    //满多少包邮。0表示不包邮
    public Integer freeShippingAmount = 0;

    //发货后多少天自动收货
    public Integer automaticConfirmationOfReceiptTime = 14;

    //多少天内可申请售后
    public Integer afterSalesServiceTimeLimit = 14;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getInventoryUpdateType() {
        return inventoryUpdateType;
    }

    public void setInventoryUpdateType(Integer inventoryUpdateType) {
        this.inventoryUpdateType = inventoryUpdateType;
    }

    public Integer getFreeShippingAmount() {
        return freeShippingAmount;
    }

    public void setFreeShippingAmount(Integer freeShippingAmount) {
        this.freeShippingAmount = freeShippingAmount;
    }

    public Integer getAutomaticConfirmationOfReceiptTime() {
        return automaticConfirmationOfReceiptTime;
    }

    public void setAutomaticConfirmationOfReceiptTime(Integer automaticConfirmationOfReceiptTime) {
        this.automaticConfirmationOfReceiptTime = automaticConfirmationOfReceiptTime;
    }

    public Integer getAfterSalesServiceTimeLimit() {
        return afterSalesServiceTimeLimit;
    }

    public void setAfterSalesServiceTimeLimit(Integer afterSalesServiceTimeLimit) {
        this.afterSalesServiceTimeLimit = afterSalesServiceTimeLimit;
    }

}
