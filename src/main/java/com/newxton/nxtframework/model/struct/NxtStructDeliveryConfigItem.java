package com.newxton.nxtframework.model.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：运费模版中的条目
 *
 */
public class NxtStructDeliveryConfigItem {

    public Long id;
    public Long deliveryConfigId;
    public Long billableQuantity;
    public Float billablePrice;
    public Long additionQuantity;
    public Float additionPrice;
    public List<NxtStructDeliveryCofnigItemRegion> regionList = new ArrayList<>();

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

    public Float getBillablePrice() {
        return billablePrice;
    }

    public void setBillablePrice(Float billablePrice) {
        this.billablePrice = billablePrice;
    }

    public Long getAdditionQuantity() {
        return additionQuantity;
    }

    public void setAdditionQuantity(Long additionQuantity) {
        this.additionQuantity = additionQuantity;
    }

    public Float getAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(Float additionPrice) {
        this.additionPrice = additionPrice;
    }

    public List<NxtStructDeliveryCofnigItemRegion> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<NxtStructDeliveryCofnigItemRegion> regionList) {
        this.regionList = regionList;
    }

}
