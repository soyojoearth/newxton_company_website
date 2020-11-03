package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductSkuValuePriceEtc)实体类
 *
 * @author makejava
 * @since 2020-11-03 16:57:15
 */
public class NxtProductSkuValuePriceEtc implements Serializable {
    private static final long serialVersionUID = 633214465594291322L;
    
    private Long id;
    
    private Long skuValueId1;
    
    private Long skuValueId2;
    /**
    * 库存
    */
    private Long skuValueInventoryQuantity;
    /**
    * 价格
    */
    private Long skuValuePrice;
    /**
    * 折扣
    */
    private Long skuValuePriceDiscount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuValueId1() {
        return skuValueId1;
    }

    public void setSkuValueId1(Long skuValueId1) {
        this.skuValueId1 = skuValueId1;
    }

    public Long getSkuValueId2() {
        return skuValueId2;
    }

    public void setSkuValueId2(Long skuValueId2) {
        this.skuValueId2 = skuValueId2;
    }

    public Long getSkuValueInventoryQuantity() {
        return skuValueInventoryQuantity;
    }

    public void setSkuValueInventoryQuantity(Long skuValueInventoryQuantity) {
        this.skuValueInventoryQuantity = skuValueInventoryQuantity;
    }

    public Long getSkuValuePrice() {
        return skuValuePrice;
    }

    public void setSkuValuePrice(Long skuValuePrice) {
        this.skuValuePrice = skuValuePrice;
    }

    public Long getSkuValuePriceDiscount() {
        return skuValuePriceDiscount;
    }

    public void setSkuValuePriceDiscount(Long skuValuePriceDiscount) {
        this.skuValuePriceDiscount = skuValuePriceDiscount;
    }

}