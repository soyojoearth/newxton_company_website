package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.process.NxtProcessDeliveryConfig;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import com.newxton.nxtframework.struct.NxtStructDeliveryConfig;
import com.newxton.nxtframework.service.NxtDeliveryConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryConfigListController {

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtProcessDeliveryConfig nxtProcessDeliveryConfig;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @RequestMapping(value = "/api/admin/delivery_config/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        //有个前端地区选择的组件，需要这个数据（前端工程师太弱鸡，组装不了这个数据）
        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.queryAllRegionForTree();
        Map<Long,NxtDeliveryRegion> mapRegion = new HashMap<>();
        for (NxtDeliveryRegion item :
                nxtDeliveryRegionList) {
            mapRegion.put(item.getId(), item);
        }
        //end

        List<NxtDeliveryConfig> nxtDeliveryConfigList = nxtDeliveryConfigService.queryAllByLimit(0,Integer.MAX_VALUE);

        List<NxtStructDeliveryConfig> list = new ArrayList<>();

        for (NxtDeliveryConfig nxtDeliveryConfig :
                nxtDeliveryConfigList) {
            NxtStructDeliveryConfig mapItemAllDetailG = nxtProcessDeliveryConfig.getDeliveryConfigAllDetail(nxtDeliveryConfig);
            list.add(mapItemAllDetailG);
            //有个前端地区选择的组件，需要这个数据（前端工程师太弱鸡，组装不了这个数据）
            mapItemAllDetailG.createItemRegionForVueComponent(mapRegion);
        }

        result.put("list",list);

        return result;

    }

}
