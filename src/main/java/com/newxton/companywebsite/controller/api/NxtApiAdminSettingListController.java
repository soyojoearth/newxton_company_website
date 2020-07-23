package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtSetting;
import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtSettingService;
import com.newxton.companywebsite.service.NxtUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminSettingListController {
    @Resource
    private NxtSettingService nxtSettingService;

    @RequestMapping(value = "/api/admin/setting_list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtSetting> list = nxtSettingService.queryAll(new NxtSetting());

        result.put("list",list);

        return result;

    }
}
