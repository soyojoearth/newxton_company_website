package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtProductSkuValue)实体类
 *
 * @author makejava
 * @since 2020-08-26 09:16:41
 */
public class NxtProductSkuValue implements Serializable {
    private static final long serialVersionUID = -81083614757103949L;
    
    private Long id;
    
    private Long skuId;
    
    private String skuValue;


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

    public String getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

}