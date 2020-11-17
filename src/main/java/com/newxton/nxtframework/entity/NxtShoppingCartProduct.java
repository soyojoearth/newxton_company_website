package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtShoppingCartProduct)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:47
 */
public class NxtShoppingCartProduct implements Serializable {
    private static final long serialVersionUID = 412932394083447822L;
    /**
     * 购物车里的东西
     */
    private Long id;

    private Long shoppingCartId;

    private Long productId;

    private Long quantity;
    /**
     * sku（存json）
     */
    private String sku;
    /**
     * 加入时间
     */
    private Long dateline;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

}