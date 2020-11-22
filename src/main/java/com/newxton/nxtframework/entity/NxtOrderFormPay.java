package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormPay)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:52
 */
public class NxtOrderFormPay implements Serializable {
    private static final long serialVersionUID = -78049600854799666L;

    private Long id;

    private Long orderFormId;
    /**
     * 成交价（放大100倍）
     */
    private Long priceDeal;

    private Long transactionId;


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

    public Long getPriceDeal() {
        return priceDeal;
    }

    public void setPriceDeal(Long priceDeal) {
        this.priceDeal = priceDeal;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

}