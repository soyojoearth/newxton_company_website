package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/25
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFormRefundProduct {

    /**
     * OrderFormRefundProduct表主键
     */
    private Long id;

    private Long orderFormProductId;
    private Long quantity;

    private String productName;
    private String picUrl;
    private List<NxtStructOrderFormProductSku> sku = new ArrayList<>();

    /**
     * 成交价(单价)
     */
    private Float productPriceDeal;
    /**
     * 该物品总退款额
     */
    private Float amountRefund;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
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

    public List<NxtStructOrderFormProductSku> getSku() {
        return sku;
    }

    public void setSku(List<NxtStructOrderFormProductSku> sku) {
        this.sku = sku;
    }

    public Float getProductPriceDeal() {
        return productPriceDeal;
    }

    public void setProductPriceDeal(Float productPriceDeal) {
        this.productPriceDeal = productPriceDeal;
    }

    public Float getAmountRefund() {
        return amountRefund;
    }

    public void setAmountRefund(Float amountRefund) {
        this.amountRefund = amountRefund;
    }
}
