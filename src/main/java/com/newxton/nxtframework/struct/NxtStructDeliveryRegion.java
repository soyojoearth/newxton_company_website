package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 */
public class NxtStructDeliveryRegion {

    private Long regionId;
    private String regionName;
    private List<NxtStructDeliveryRegion> child = new ArrayList<>();

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

    public List<NxtStructDeliveryRegion> getChild() {
        return child;
    }

    public void setChild(List<NxtStructDeliveryRegion> child) {
        this.child = child;
    }

}
