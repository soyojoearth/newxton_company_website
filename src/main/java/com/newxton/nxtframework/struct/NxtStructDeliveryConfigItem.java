package com.newxton.nxtframework.struct;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 有个前端地区选择的组件，需要这个数据（前端工程师太弱鸡，组装不了这个数据）
     */
    public List<NxtStructDeliveryCofnigItemRegionForVueComponent> selectRegionList = new ArrayList<>();

    public void createItemRegionForVueComponent(Map<Long,NxtDeliveryRegion> mapRegion){
        for (NxtStructDeliveryCofnigItemRegion item : regionList) {
            if (mapRegion.containsKey(item.getRegionId())){
                NxtStructDeliveryCofnigItemRegionForVueComponent regionForVueComponent = new NxtStructDeliveryCofnigItemRegionForVueComponent(mapRegion.get(item.getRegionId()),mapRegion);
                selectRegionList.add(regionForVueComponent);
            }
        }
    }
    //end for Vue component

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
