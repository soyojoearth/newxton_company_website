package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.struct.NxtStructSettingNormal;
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
public class NxtApiAdminSettingNormalDetailController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting_normal/detail", method = RequestMethod.POST)
    public Map<String, Object> action() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtStructSettingNormal nxtStructSettingNormal = nxtGlobalSettingComponent.getNxtStructSettingNormal();

        result.put("detail",nxtStructSettingNormal);

        return result;
    }

}
