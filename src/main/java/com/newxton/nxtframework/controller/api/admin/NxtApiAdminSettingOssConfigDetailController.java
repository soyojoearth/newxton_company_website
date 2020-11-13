package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.model.struct.NxtStructSettingEcConfig;
import com.newxton.nxtframework.model.struct.NxtStructSettingOssConfig;
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
public class NxtApiAdminSettingOssConfigDetailController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting_oss_config/detail", method = RequestMethod.POST)
    public Map<String, Object> action() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List keys = new ArrayList();
        keys.add("ossLocation");
        keys.add("ossQiniuAccessKey");
        keys.add("ossQiniuSecretKey");
        keys.add("ossQiniuBucket");
        keys.add("ossQiniuDomain");

        Map<String,NxtSetting> settingMap = nxtGlobalSettingComponent.getSettingsByKeys(keys);

        NxtStructSettingOssConfig nxtStructSettingOssConfig = new NxtStructSettingOssConfig();
        if (settingMap.get("ossLocation") != null) {
            NxtSetting nxtSetting = settingMap.get("ossLocation");
            nxtStructSettingOssConfig.ossLocation = nxtSetting.getSettingValue();
        }
        if (settingMap.get("ossQiniuAccessKey") != null) {
            NxtSetting nxtSetting = settingMap.get("ossQiniuSecretKey");
            nxtStructSettingOssConfig.ossQiniuAccessKey = nxtSetting.getSettingValue();
        }
        if (settingMap.get("ossQiniuSecretKey") != null) {
            NxtSetting nxtSetting = settingMap.get("ossQiniuSecretKey");
            nxtStructSettingOssConfig.ossQiniuSecretKey = nxtSetting.getSettingValue();
        }
        if (settingMap.get("ossQiniuBucket") != null) {
            NxtSetting nxtSetting = settingMap.get("ossQiniuBucket");
            nxtStructSettingOssConfig.ossQiniuBucket = nxtSetting.getSettingValue();
        }
        if (settingMap.get("ossQiniuDomain") != null) {
            NxtSetting nxtSetting = settingMap.get("ossQiniuDomain");
            nxtStructSettingOssConfig.ossQiniuDomain = nxtSetting.getSettingValue();
        }

        result.put("detail",nxtStructSettingOssConfig);

        return result;
    }

}
