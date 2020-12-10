package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtDeliveryConfigItem)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public class NxtDeliveryConfigItem implements Serializable {
    private static final long serialVersionUID = -36303054432411668L;
    
    private Long id;
    
    private Long deliveryConfigId;
    /**
    * 最低计费数量
    */
    private Long billableQuantity;
    /**
    * 起步价
    */
    private Long billablePrice;
    /**
    * 最低增加单位量
    */
    private Long additionQuantity;
    /**
    * 最低单位续费
    */
    private Long additionPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryConfigId() {
        return deliveryConfigId;
    }

    public void setDeliveryConfigId(Long deliveryConfigId) {
        this.deliveryConfigId = deliveryConfigId;
    }

    public Long getBillableQuantity() {
        return billableQuantity;
    }

    public void setBillableQuantity(Long billableQuantity) {
        this.billableQuantity = billableQuantity;
    }

    public Long getBillablePrice() {
        return billablePrice;
    }

    public void setBillablePrice(Long billablePrice) {
        this.billablePrice = billablePrice;
    }

    public Long getAdditionQuantity() {
        return additionQuantity;
    }

    public void setAdditionQuantity(Long additionQuantity) {
        this.additionQuantity = additionQuantity;
    }

    public Long getAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(Long additionPrice) {
        this.additionPrice = additionPrice;
    }

}