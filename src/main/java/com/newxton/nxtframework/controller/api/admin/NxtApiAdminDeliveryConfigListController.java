package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.model.NxtModelDeliveryConfig;
import com.newxton.nxtframework.model.struct.NxtStructDeliveryConfig;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
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
 */
@RestController
public class NxtApiAdminDeliveryConfigListController {

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtModelDeliveryConfig nxtModelDeliveryConfig;

    @RequestMapping(value = "/api/admin/delivery_config/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtDeliveryConfig> nxtDeliveryConfigList = nxtDeliveryConfigService.queryAllByLimit(0,Integer.MAX_VALUE);

        List<NxtStructDeliveryConfig> list = new ArrayList<>();

        for (NxtDeliveryConfig nxtDeliveryConfig :
                nxtDeliveryConfigList) {
            NxtStructDeliveryConfig mapItemAllDetailG = nxtModelDeliveryConfig.getDeliveryConfigAllDetail(nxtDeliveryConfig);
            list.add(mapItemAllDetailG);
        }

        result.put("list",list);

        return result;

    }

}
