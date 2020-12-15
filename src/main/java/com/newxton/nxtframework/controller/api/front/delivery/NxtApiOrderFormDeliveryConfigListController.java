package com.newxton.nxtframework.controller.api.front.delivery;

import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.service.NxtDeliveryConfigService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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
public class NxtApiOrderFormDeliveryConfigListController {

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @RequestMapping("/api/order_form/delivery_config/list")
    public NxtStructApiResult exec() {

        List<NxtDeliveryConfig> nxtDeliveryConfigList = nxtDeliveryConfigService.queryAll(new NxtDeliveryConfig());

        return new NxtStructApiResult(nxtDeliveryConfigList);

    }

}
