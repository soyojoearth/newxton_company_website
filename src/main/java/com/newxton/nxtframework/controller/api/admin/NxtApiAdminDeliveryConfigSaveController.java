package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.process.NxtProcessDeliveryConfig;
import com.newxton.nxtframework.struct.NxtStructDeliveryConfig;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryConfigSaveController {

    @Resource
    private NxtProcessDeliveryConfig nxtProcessDeliveryConfig;

    @RequestMapping(value = "/api/admin/delivery_config/save", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructDeliveryConfig nxtStructDeliveryConfig = gson.fromJson(json,NxtStructDeliveryConfig.class);

        return nxtProcessDeliveryConfig.save(nxtStructDeliveryConfig);

    }


}
