package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：运费模版中的条目的地区
 *
 */
public class NxtStructDeliveryCofnigItemRegion {

    public Long id;
    public Long deliveryConfigItemId;
    public Long regionId;
    public String regionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getDeliveryConfigItemId() {
        return deliveryConfigItemId;
    }

    public void setDeliveryConfigItemId(Long deliveryConfigItemId) {
        this.deliveryConfigItemId = deliveryConfigItemId;
    }
}
