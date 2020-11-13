package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.model.struct.NxtStructSettingNormal;
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
 * @time 2020/11/12
 * @address Shenzhen, China
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

        List keys = new ArrayList();
        keys.add("statCode");
        keys.add("contactCode");
        keys.add("contactLink");
        keys.add("beianCode");

        Map<String,NxtSetting> settingMap = nxtGlobalSettingComponent.getSettingsByKeys(keys);

        NxtStructSettingNormal nxtStructSettingNormal = new NxtStructSettingNormal();
        if (settingMap.get("statCode") != null) {
            NxtSetting nxtSetting = settingMap.get("statCode");
            nxtStructSettingNormal.statCode = nxtSetting.getSettingValue();
        }
        if (settingMap.get("contactCode") != null) {
            NxtSetting nxtSetting = settingMap.get("contactCode");
            nxtStructSettingNormal.contactCode = nxtSetting.getSettingValue();
        }
        if (settingMap.get("contactLink") != null) {
            NxtSetting nxtSetting = settingMap.get("contactLink");
            nxtStructSettingNormal.contactLink = nxtSetting.getSettingValue();
        }
        if (settingMap.get("beianCode") != null) {
            NxtSetting nxtSetting = settingMap.get("beianCode");
            nxtStructSettingNormal.beianCode = nxtSetting.getSettingValue();
        }

        result.put("detail",nxtStructSettingNormal);

        return result;
    }

}
