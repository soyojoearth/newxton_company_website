package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.model.struct.NxtStructSettingPayConfig;
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
public class NxtApiAdminSettingPayConfigDetailController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting_pay_config/detail", method = RequestMethod.POST)
    public Map<String, Object> action() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List keys = new ArrayList();
        keys.add("wxpayAPPID");
        keys.add("wxpayClinetID");
        keys.add("wxpaySecretKey");
        keys.add("alipayAPPID");
        keys.add("alipaySecretKey");
        keys.add("alipayPublicKey");

        Map<String,NxtSetting> settingMap = nxtGlobalSettingComponent.getSettingsByKeys(keys);

        NxtStructSettingPayConfig nxtStructSettingPayConfig = new NxtStructSettingPayConfig();
        if (settingMap.get("wxpayAPPID") != null) {
            NxtSetting nxtSetting = settingMap.get("wxpayAPPID");
            nxtStructSettingPayConfig.wxpayAPPID = nxtSetting.getSettingValue();
        }
        if (settingMap.get("wxpayClinetID") != null) {
            NxtSetting nxtSetting = settingMap.get("wxpayClinetID");
            nxtStructSettingPayConfig.wxpayClinetID = nxtSetting.getSettingValue();
        }
        if (settingMap.get("wxpaySecretKey") != null) {
            NxtSetting nxtSetting = settingMap.get("wxpaySecretKey");
            nxtStructSettingPayConfig.wxpaySecretKey = nxtSetting.getSettingValue();
        }
        if (settingMap.get("alipayAPPID") != null) {
            NxtSetting nxtSetting = settingMap.get("alipayAPPID");
            nxtStructSettingPayConfig.alipayAPPID = nxtSetting.getSettingValue();
        }
        if (settingMap.get("alipaySecretKey") != null) {
            NxtSetting nxtSetting = settingMap.get("alipaySecretKey");
            nxtStructSettingPayConfig.alipaySecretKey = nxtSetting.getSettingValue();
        }
        if (settingMap.get("alipayPublicKey") != null) {
            NxtSetting nxtSetting = settingMap.get("alipayPublicKey");
            nxtStructSettingPayConfig.alipayPublicKey = nxtSetting.getSettingValue();
        }

        result.put("detail",nxtStructSettingPayConfig);

        return result;

    }

}
