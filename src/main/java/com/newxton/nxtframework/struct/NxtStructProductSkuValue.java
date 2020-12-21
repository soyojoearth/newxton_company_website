package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：产品详情中sku的条目详情
 *
 */
public class NxtStructProductSkuValue {

    public NxtStructProductSkuValue(){

    }

    public NxtStructProductSkuValue(String valueName){
        this.setSkuValueName(valueName);
    }

    public Long id;

    public Long skuId;

    public String skuValueName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuValueName() {
        return skuValueName;
    }

    public void setSkuValueName(String skuValueName) {
        this.skuValueName = skuValueName;
    }
}
