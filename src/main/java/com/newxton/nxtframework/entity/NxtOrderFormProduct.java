package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormProduct)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:53
 */
public class NxtOrderFormProduct implements Serializable {
    private static final long serialVersionUID = 158409188356376632L;
    /**
     * 订单管理商品
     */
    private Long id;

    private Long orderFormId;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 成交数量
     */
    private Long quantity;
    /**
     * 产品名称
     */
    private String productName;

    private Long productPrice;
    /**
     * 价格折扣（放大100倍）
     */
    private Long productPriceDiscount;
    /**
     * 用户等级
     */
    private Integer levelNum;
    /**
     * 用户等级折扣（放大100倍）
     */
    private Long levelDiscount;
    /**
     * 成交价（放大100倍）
     */
    private Long productPriceDeal;
    /**
     * Sku（json）
     */
    private String productSku;
    /**
     * 是否退货了（0否 1是）
     */
    private Integer isRefund;

    private Long commissionRate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductPriceDiscount() {
        return productPriceDiscount;
    }

    public void setProductPriceDiscount(Long productPriceDiscount) {
        this.productPriceDiscount = productPriceDiscount;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Long getLevelDiscount() {
        return levelDiscount;
    }

    public void setLevelDiscount(Long levelDiscount) {
        this.levelDiscount = levelDiscount;
    }

    public Long getProductPriceDeal() {
        return productPriceDeal;
    }

    public void setProductPriceDeal(Long productPriceDeal) {
        this.productPriceDeal = productPriceDeal;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }

}