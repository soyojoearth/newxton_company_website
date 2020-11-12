package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtDeliveryConfigItemRegion)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public class NxtDeliveryConfigItemRegion implements Serializable {
    private static final long serialVersionUID = 994457849498015928L;
    /**
    * 运费模版条目所含地区
    */
    private Long id;
    
    private Long deliveryConfigItemId;
    
    private Long deliveryRegionId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryConfigItemId() {
        return deliveryConfigItemId;
    }

    public void setDeliveryConfigItemId(Long deliveryConfigItemId) {
        this.deliveryConfigItemId = deliveryConfigItemId;
    }

    public Long getDeliveryRegionId() {
        return deliveryRegionId;
    }

    public void setDeliveryRegionId(Long deliveryRegionId) {
        this.deliveryRegionId = deliveryRegionId;
    }

}