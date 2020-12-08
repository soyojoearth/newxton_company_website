package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
public class NxtStructOrderFormDelivery {

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
