package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductSku)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:06
 */
public class NxtProductSku implements Serializable {
    private static final long serialVersionUID = 909357348279522898L;
    
    private Long id;
    /**
    * 产品ID
    */
    private Long productId;
    /**
    * sku键名称
    */
    private String skuKeyName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSkuKeyName() {
        return skuKeyName;
    }

    public void setSkuKeyName(String skuKeyName) {
        this.skuKeyName = skuKeyName;
    }

}