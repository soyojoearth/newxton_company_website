package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormDelivery)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:51
 */
public class NxtOrderFormDelivery implements Serializable {
    private static final long serialVersionUID = 740227055002073987L;
    /**
     * 订单物流表
     */
    private Long id;

    private Long orderFormId;

    private Long deliveryCompanyId;

    private String deliveryCompanyName;

    private String deliverySerialNum;


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

    public Long getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(Long deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
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