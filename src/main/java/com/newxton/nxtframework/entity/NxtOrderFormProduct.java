package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormProduct)实体类
 *
 * @author makejava
 * @since 2020-12-07 17:26:06
 */
public class NxtOrderFormProduct implements Serializable {
    private static final long serialVersionUID = 229535902208318713L;
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
    
    private Long productPicUploadfileId;
    /**
    * 产品名称
    */
    private String productName;
    /**
    * 单位（克）
    */
    private Long unitWeight;
    /**
    * 单位（立法米）放大100万倍
    */
    private Long unitVolume;
    
    private Long productPrice;
    /**
    * 价格折扣（放大100倍）
    */
    private Long productPriceDiscount;
    /**
    * 订单商品价格调整（后台手动）（正数加、负数减）
    */
    private Long manualPriceDiscount;
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
    * 退货数量
    */
    private Long quantityRefund;
    
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

    public Long getProductPicUploadfileId() {
        return productPicUploadfileId;
    }

    public void setProductPicUploadfileId(Long productPicUploadfileId) {
        this.productPicUploadfileId = productPicUploadfileId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Long unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Long getUnitVolume() {
        return unitVolume;
    }

    public void setUnitVolume(Long unitVolume) {
        this.unitVolume = unitVolume;
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

    public Long getManualPriceDiscount() {
        return manualPriceDiscount;
    }

    public void setManualPriceDiscount(Long manualPriceDiscount) {
        this.manualPriceDiscount = manualPriceDiscount;
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

    public Long getQuantityRefund() {
        return quantityRefund;
    }

    public void setQuantityRefund(Long quantityRefund) {
        this.quantityRefund = quantityRefund;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }

}