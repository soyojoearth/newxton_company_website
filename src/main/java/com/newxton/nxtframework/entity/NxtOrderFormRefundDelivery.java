package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormRefundDelivery)实体类
 *
 * @author makejava
 * @since 2020-12-08 15:39:40
 */
public class NxtOrderFormRefundDelivery implements Serializable {
    private static final long serialVersionUID = -89780013402504384L;
    
    private Long id;
    
    private Long orderFormRefundId;
    
    private String deliveryCompanyName;
    
    private String deliverySerialNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormRefundId() {
        return orderFormRefundId;
    }

    public void setOrderFormRefundId(Long orderFormRefundId) {
        this.orderFormRefundId = orderFormRefundId;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getDeliverySerialNum() {
        return deliverySerialNum;
    }

    public void setDeliverySerialNum(String deliverySerialNum) {
        this.deliverySerialNum = deliverySerialNum;
    }

}