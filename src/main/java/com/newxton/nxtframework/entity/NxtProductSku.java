package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductSku)实体类
 *
 * @author makejava
 * @since 2020-08-26 09:16:25
 */
public class NxtProductSku implements Serializable {
    private static final long serialVersionUID = -12759281674424796L;
    
    private Long id;
    
    private Long productId;
    
    private String skuName;


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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

}