package com.newxton.nxtframework.controller.api.front.delivery;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructDeliveryRegion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiDeliveryRegionTreeController {

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @RequestMapping("/api/delivery_region/tree")
    public NxtStructApiResult exec() {

        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.queryAllRegionForTree();

        Map<Long, NxtStructDeliveryRegion> mapRegionIdToRegion = new HashMap<>();
        List<NxtStructDeliveryRegion> nxtStructDeliveryRegionList = new ArrayList<>();

        for (NxtDeliveryRegion region : nxtDeliveryRegionList) {
            NxtStructDeliveryRegion nxtStructDeliveryRegion = new NxtStructDeliveryRegion();
            nxtStructDeliveryRegion.setRegionId(region.getId());
            nxtStructDeliveryRegion.setRegionName(region.getRegionName());
            mapRegionIdToRegion.put(region.getId(),nxtStructDeliveryRegion);
            if (region.getRegionPid()>0){
                NxtStructDeliveryRegion parentRegion = mapRegionIdToRegion.getOrDefault(region.getRegionPid(),null);
                if (parentRegion != null){
                    parentRegion.getChild().add(nxtStructDeliveryRegion);
                }
            }
            else {
                nxtStructDeliveryRegionList.add(nxtStructDeliveryRegion);
            }
        }

        return new NxtStructApiResult(nxtStructDeliveryRegionList);

    }

}
