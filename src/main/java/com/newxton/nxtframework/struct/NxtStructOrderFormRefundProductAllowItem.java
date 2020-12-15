package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/25
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFormRefundProductAllowItem {

    private Long orderFormProductId;
    private Long quantityAll;
    private Long quantityAllowRefund;
    private Long quantityIsRefund;
    private Boolean allowRefund;

    private String productName;
    private String picUrl;
    private List<NxtStructOrderFormProductSku> sku = new ArrayList<>();

    /**
     * 成交价(单价)
     */
    private Float productPriceDeal;

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
    }

    public Long getQuantityAll() {
        return quantityAll;
    }

    public void setQuantityAll(Long quantityAll) {
        this.quantityAll = quantityAll;
    }

    public Long getQuantityAllowRefund() {
        return quantityAllowRefund;
    }

    public void setQuantityAllowRefund(Long quantityAllowRefund) {
        this.quantityAllowRefund = quantityAllowRefund;
    }

    public Long getQuantityIsRefund() {
        return quantityIsRefund;
    }

    public void setQuantityIsRefund(Long quantityIsRefund) {
        this.quantityIsRefund = quantityIsRefund;
    }

    public Boolean getAllowRefund() {
        return allowRefund;
    }

    public void setAllowRefund(Boolean allowRefund) {
        this.allowRefund = allowRefund;
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

}
