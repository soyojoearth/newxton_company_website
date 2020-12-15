package com.newxton.nxtframework.struct;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/10
 * @address Shenzhen, China
 * @copyright NxtFramework
 * Api接口数据结构：运费模版中的条目的地区 2
 *
 * 有个前端地区选择的组件，需要这个数据（前端工程师太弱鸡，组装不了这个数据）
 *
 */
public class NxtStructDeliveryCofnigItemRegionForVueComponent {

    private String regionId;
    private String regionName;

    private List<String> regionIdList = new ArrayList<>();
    private List<String> regionNameList = new ArrayList<>();

    public NxtStructDeliveryCofnigItemRegionForVueComponent(NxtDeliveryRegion nxtDeliveryRegion, Map<Long, NxtDeliveryRegion> mapToRegion){

        this.prependRegion(nxtDeliveryRegion,mapToRegion);

    }

    private void prependRegion(NxtDeliveryRegion nxtDeliveryRegion, Map<Long, NxtDeliveryRegion> mapToRegion){

        regionIdList.add(0,nxtDeliveryRegion.getId().toString());
        regionNameList.add(0,nxtDeliveryRegion.getRegionName());
        this.regionId = String.join("-",regionIdList);
        this.regionName = String.join("-",regionNameList);
        if (nxtDeliveryRegion.getRegionPid() != null && nxtDeliveryRegion.getRegionPid() > 0){
            if (mapToRegion.containsKey(nxtDeliveryRegion.getRegionPid())) {
                this.prependRegion(mapToRegion.get(nxtDeliveryRegion.getRegionPid()), mapToRegion);
            }
        }

    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<String> getRegionIdList() {
        return regionIdList;
    }

    public void setRegionIdList(List<String> regionIdList) {
        this.regionIdList = regionIdList;
    }

    public List<String> getRegionNameList() {
        return regionNameList;
    }

    public void setRegionNameList(List<String> regionNameList) {
        this.regionNameList = regionNameList;
    }

}
