package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.service.NxtSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminSettingSaveController {

    @Resource
    private NxtSettingService nxtSettingService;

    @RequestMapping(value = "/api/admin/setting_save", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "setting_key", required = false) String settingKey,
            @RequestParam(value = "setting_value", required = false) String settingValue
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (settingKey == null || settingValue == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        NxtSetting setting = nxtSettingService.queryBySettingKey(settingKey);

        if (setting == null){
            result.put("status",46);
            result.put("message","该设置不存在");
            return result;
        }

        setting.setSettingValue(settingValue);
        setting.setDatelineUpdated(System.currentTimeMillis());

        nxtSettingService.update(setting);

        return result;

    }

}
