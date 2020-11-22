package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

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
    public Integer type;
    public List<NxtStructDeliveryConfigItem> itemList = new ArrayList<>();

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<NxtStructDeliveryConfigItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<NxtStructDeliveryConfigItem> itemList) {
        this.itemList = itemList;
    }
}
