package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：产品详情中2各交叉sku条目的价格等
 *
 */
public class NxtStructProductSkuValuePriceEtc {
    public String skuValueName1;
    public String skuValueName2;
    public Long skuValueInventoryQuantity;
    public Float skuValuePrice;
    public Float skuValuePriceDiscount;

    public String getSkuValueName1() {
        return skuValueName1;
    }

    public void setSkuValueName1(String skuValueName1) {
        this.skuValueName1 = skuValueName1;
    }

    public String getSkuValueName2() {
        return skuValueName2;
    }

    public void setSkuValueName2(String skuValueName2) {
        this.skuValueName2 = skuValueName2;
    }

    public Long getSkuValueInventoryQuantity() {
        return skuValueInventoryQuantity;
    }

    public void setSkuValueInventoryQuantity(Long skuValueInventoryQuantity) {
        this.skuValueInventoryQuantity = skuValueInventoryQuantity;
    }

    public Float getSkuValuePrice() {
        return skuValuePrice;
    }

    public void setSkuValuePrice(Float skuValuePrice) {
        this.skuValuePrice = skuValuePrice;
    }

    public Float getSkuValuePriceDiscount() {
        return skuValuePriceDiscount;
    }

    public void setSkuValuePriceDiscount(Float skuValuePriceDiscount) {
        this.skuValuePriceDiscount = skuValuePriceDiscount;
    }
}
