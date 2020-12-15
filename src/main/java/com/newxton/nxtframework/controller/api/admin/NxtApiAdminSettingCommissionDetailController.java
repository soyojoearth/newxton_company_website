package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructSettingCommission;
import com.newxton.nxtframework.struct.NxtStructSettingEcConfig;
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
public class NxtApiAdminSettingCommissionDetailController {

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/setting/commission/detail", method = RequestMethod.POST)
    public Map<String, Object> action() {

        NxtStructSettingCommission nxtStructSettingCommission = nxtGlobalSettingComponent.getNxtStructSettingCommission();

        return new NxtStructApiResult(nxtStructSettingCommission).toMap();

    }

}
