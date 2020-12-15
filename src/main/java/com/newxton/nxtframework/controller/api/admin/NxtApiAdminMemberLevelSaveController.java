package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminMemberLevel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberLevelSaveController {

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @RequestMapping(value = "/api/admin/member_level/save", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();
        NxtStructAdminMemberLevel nxtStructAdminMemberLevel;

        try {
            nxtStructAdminMemberLevel = gson.fromJson(json, NxtStructAdminMemberLevel.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对").toMap();
        }

        if (nxtStructAdminMemberLevel.getLevelName() == null || nxtStructAdminMemberLevel.getLevelName().trim().isEmpty()){
            return new NxtStructApiResult(53,"缺少：等级名称").toMap();
        }
        if (nxtStructAdminMemberLevel.getLevelCost() == null){
            return new NxtStructApiResult(53,"缺少：消费额度").toMap();
        }
        if (nxtStructAdminMemberLevel.getLevelCost() < 0){
            return new NxtStructApiResult(53,"消费额度不对：不能小于0").toMap();
        }
        if (nxtStructAdminMemberLevel.getLevelDiscount() == null){
            return new NxtStructApiResult(53,"缺少：折扣率").toMap();
        }
        if (nxtStructAdminMemberLevel.getLevelDiscount() > 100 || nxtStructAdminMemberLevel.getLevelDiscount() < 0){
            return new NxtStructApiResult(53,"折扣不对：仅0-100").toMap();
        }

        NxtUserLevel nxtUserLevel = nxtUserLevelService.queryByNum(nxtStructAdminMemberLevel.getLevelNum());

        if (nxtUserLevel == null){
            nxtUserLevel = new NxtUserLevel();
            NxtUserLevel nxtUserLevelMax = nxtUserLevelService.queryMaxOne();
            if (nxtUserLevelMax == null){
                nxtUserLevel.setNum(1);
            }
            else {
                nxtUserLevel.setNum(nxtUserLevelMax.getNum()+1);
            }
        }
        nxtUserLevel.setName(nxtStructAdminMemberLevel.getLevelName());
        nxtUserLevel.setCost((long)(nxtStructAdminMemberLevel.getLevelCost()*100));
        nxtUserLevel.setDiscount(nxtStructAdminMemberLevel.getLevelDiscount());

        if (nxtUserLevel.getId() == null){
            nxtUserLevelService.insert(nxtUserLevel);
        }
        else {
            nxtUserLevelService.update(nxtUserLevel);
        }

        return new NxtStructApiResult().toMap();

    }

}
