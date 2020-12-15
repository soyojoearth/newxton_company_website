package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructSettingCommission;
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
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminSettingCommissionSaveController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting/commission/save", method = RequestMethod.POST)
    public Map<String, Object> action(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructSettingCommission nxtStructSettingCommission = gson.fromJson(json,NxtStructSettingCommission.class);

        Long rateTotal = nxtStructSettingCommission.getCommissionRateLevel1()+nxtStructSettingCommission.getCommissionRateLevel2()+ nxtStructSettingCommission.getCommissionRateLevel3();
        if (!rateTotal.equals(100L)){
            return new NxtStructApiResult(53,"3级分佣率相加的和必须等于100").toMap();
        }

        nxtGlobalSettingComponent.saveNxtStructSettingCommission(nxtStructSettingCommission);

        return new NxtStructApiResult().toMap();
    }

}
