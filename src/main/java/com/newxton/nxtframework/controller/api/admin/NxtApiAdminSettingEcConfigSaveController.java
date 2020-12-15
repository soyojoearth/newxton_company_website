package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.struct.NxtStructSettingEcConfig;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminSettingEcConfigSaveController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting_ec_config/save", method = RequestMethod.POST)
    public Map<String, Object> action(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructSettingEcConfig nxtStructSettingEcConfig = gson.fromJson(json,NxtStructSettingEcConfig.class);

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        nxtGlobalSettingComponent.saveNxtStructSettingEcConfig(nxtStructSettingEcConfig);

        return result;
    }

}
