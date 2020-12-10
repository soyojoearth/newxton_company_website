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
 * Api接口数据结构：运费模版
 *
 */
public class NxtStructDeliveryConfig {
    public Long id;
    public String name;
    public List<NxtStructDeliveryConfigItem> itemList = new ArrayList<>();

    //有个前端地区选择的组件，需要这个数据（前端工程师太弱鸡，组装不了这个数据）
    public void createItemRegionForVueComponent(Map<Long, NxtDeliveryRegion> mapRegion){
        for (NxtStructDeliveryConfigItem item : itemList) {
            item.createItemRegionForVueComponent(mapRegion);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NxtStructDeliveryConfigItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<NxtStructDeliveryConfigItem> itemList) {
        this.itemList = itemList;
    }
}
