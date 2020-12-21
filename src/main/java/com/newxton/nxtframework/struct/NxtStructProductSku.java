package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：产品详情中的sku详情
 *
 */
public class NxtStructProductSku {

    public NxtStructProductSku(){

    }

    public NxtStructProductSku(String keyName){
        this.setSkuKeyName(keyName);
    }

    public void addSkuValue(String valueName){
        this.skuValueList.add(new NxtStructProductSkuValue(valueName));
    }

    public Long skuId;
    public String skuKeyName;
    public List<NxtStructProductSkuValue> skuValueList = new ArrayList<>();

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuKeyName() {
        return skuKeyName;
    }

    public void setSkuKeyName(String skuKeyName) {
        this.skuKeyName = skuKeyName;
    }

    public List<NxtStructProductSkuValue> getSkuValueList() {
        return skuValueList;
    }

    public void setSkuValueList(List<NxtStructProductSkuValue> skuValueList) {
        this.skuValueList = skuValueList;
    }
}
