package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminMemberLevel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberLevelListController {

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @RequestMapping(value = "/api/admin/member_level/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        List<NxtUserLevel> nxtUserLevelList = nxtUserLevelService.queryAll(new NxtUserLevel());
        List<NxtStructAdminMemberLevel> nxtStructAdminMemberLevelList = new ArrayList<>();

        for (NxtUserLevel nxtUserLevel : nxtUserLevelList) {
            NxtStructAdminMemberLevel nxtStructAdminMemberLevel = new NxtStructAdminMemberLevel();
            nxtStructAdminMemberLevel.setLevelNum(nxtUserLevel.getNum());
            nxtStructAdminMemberLevel.setLevelName(nxtUserLevel.getName());
            nxtStructAdminMemberLevel.setLevelCost(nxtUserLevel.getCost()/100F);
            nxtStructAdminMemberLevel.setLevelDiscount(nxtUserLevel.getDiscount());
            nxtStructAdminMemberLevelList.add(nxtStructAdminMemberLevel);
        }

        return new NxtStructApiResult(nxtStructAdminMemberLevelList).toMap();

    }

}
