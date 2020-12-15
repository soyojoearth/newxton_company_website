package com.newxton.nxtframework.controller.api.front.delivery;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormDeliveryRegionListController {

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @RequestMapping("/api/order_form/delivery_region/list")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Long parentId = jsonParam.getLong("parentId");

        if (parentId == null){
            return new NxtStructApiResult(54,"请提供parentId");
        }

        NxtDeliveryRegion nxtDeliveryRegionCondition = new NxtDeliveryRegion();
        nxtDeliveryRegionCondition.setRegionPid(parentId);

        List<NxtDeliveryRegion> nxtDeliveryConfigList = nxtDeliveryRegionService.queryAll(nxtDeliveryRegionCondition);

        return new NxtStructApiResult(nxtDeliveryConfigList);

    }

}
