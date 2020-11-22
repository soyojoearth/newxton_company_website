package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormRefundProduct)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:57
 */
public class NxtOrderFormRefundProduct implements Serializable {
    private static final long serialVersionUID = 486378299079781254L;

    private Long id;

    private Long orderFormProductId;

    private Long orderFormRefundId;
    /**
     * 退货数量
     */
    private Long quantity;
    /**
     * 成交价（放大100倍）
     */
    private Long priceDeal;
    /**
     * 退款额（放大100倍）
     */
    private Long amountRefund;


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

    public Long getOrderFormRefundId() {
        return orderFormRefundId;
    }

    public void setOrderFormRefundId(Long orderFormRefundId) {
        this.orderFormRefundId = orderFormRefundId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPriceDeal() {
        return priceDeal;
    }

    public void setPriceDeal(Long priceDeal) {
        this.priceDeal = priceDeal;
    }

    public Long getAmountRefund() {
        return amountRefund;
    }

    public void setAmountRefund(Long amountRefund) {
        this.amountRefund = amountRefund;
    }

}