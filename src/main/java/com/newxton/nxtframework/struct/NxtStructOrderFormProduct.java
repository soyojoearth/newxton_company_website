package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
public class NxtStructOrderFormProduct {

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
    /**
     * 产品主图地址
     */
    private String picUrl;
    /**
     * 单位（千克）
     */
    private Float unitWeight;
    /**
     * 单位（立方米）
     */
    private Float unitVolume;

    private Float productPrice;
    /**
     * 价格折扣
     */
    private Float productPriceDiscount;
    /**
     * 用户等级
     */
    private Integer levelNum;
    /**
     * 用户等级折扣
     */
    private Float levelDiscount;
    /**
     * 成交价
     */
    private Float productPriceDeal;
    /**
     * Sku（NxtStructOrderFormProductSku）
     */
    private List<NxtStructOrderFormProductSku> productSku = new ArrayList<>();
    /**
     * 退货数量
     */
    private Long quantityRefund;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Float getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Float unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Float getUnitVolume() {
        return unitVolume;
    }

    public void setUnitVolume(Float unitVolume) {
        this.unitVolume = unitVolume;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Float getProductPriceDiscount() {
        return productPriceDiscount;
    }

    public void setProductPriceDiscount(Float productPriceDiscount) {
        this.productPriceDiscount = productPriceDiscount;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Float getLevelDiscount() {
        return levelDiscount;
    }

    public void setLevelDiscount(Float levelDiscount) {
        this.levelDiscount = levelDiscount;
    }

    public Float getProductPriceDeal() {
        return productPriceDeal;
    }

    public void setProductPriceDeal(Float productPriceDeal) {
        this.productPriceDeal = productPriceDeal;
    }

    public List<NxtStructOrderFormProductSku> getProductSku() {
        return productSku;
    }

    public void setProductSku(List<NxtStructOrderFormProductSku> productSku) {
        this.productSku = productSku;
    }

    public Long getQuantityRefund() {
        return quantityRefund;
    }

    public void setQuantityRefund(Long quantityRefund) {
        this.quantityRefund = quantityRefund;
    }

}
