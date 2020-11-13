package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.model.struct.NxtStructSettingEcConfig;
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
public class NxtApiAdminSettingEcConfigDetailController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting_ec_config/detail", method = RequestMethod.POST)
    public Map<String, Object> action() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List keys = new ArrayList();
        keys.add("keywords");
        keys.add("inventoryUpdateType");
        keys.add("freeShippingAmount");
        keys.add("automaticConfirmationOfReceiptTime");
        keys.add("afterSalesServiceTimeLimit");

        Map<String,NxtSetting> settingMap = nxtGlobalSettingComponent.getSettingsByKeys(keys);

        NxtStructSettingEcConfig nxtStructSettingEcConfig = new NxtStructSettingEcConfig();
        if (settingMap.get("keywords") != null) {
            NxtSetting nxtSetting = settingMap.get("keywords");
            nxtStructSettingEcConfig.keywords = nxtSetting.getSettingValue();
        }
        if (settingMap.get("inventoryUpdateType") != null) {
            NxtSetting nxtSetting = settingMap.get("inventoryUpdateType");
            nxtStructSettingEcConfig.inventoryUpdateType = Integer.valueOf(nxtSetting.getSettingValue());
        }
        if (settingMap.get("freeShippingAmount") != null) {
            NxtSetting nxtSetting = settingMap.get("freeShippingAmount");
            nxtStructSettingEcConfig.freeShippingAmount = Integer.valueOf(nxtSetting.getSettingValue());
        }
        if (settingMap.get("automaticConfirmationOfReceiptTime") != null) {
            NxtSetting nxtSetting = settingMap.get("automaticConfirmationOfReceiptTime");
            nxtStructSettingEcConfig.automaticConfirmationOfReceiptTime = Integer.valueOf(nxtSetting.getSettingValue());
        }
        if (settingMap.get("afterSalesServiceTimeLimit") != null) {
            NxtSetting nxtSetting = settingMap.get("afterSalesServiceTimeLimit");
            nxtStructSettingEcConfig.afterSalesServiceTimeLimit = Integer.valueOf(nxtSetting.getSettingValue());
        }

        result.put("detail",nxtStructSettingEcConfig);

        return result;
    }

}
