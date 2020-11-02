package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductSkuValue)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:04:34
 */
public class NxtProductSkuValue implements Serializable {
    private static final long serialVersionUID = -18670751705196904L;
    
    private Long id;
    /**
    * sku的ID
    */
    private Long skuId;
    /**
    * 值名
    */
    private String skuValueName;
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