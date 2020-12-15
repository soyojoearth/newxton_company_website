package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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
public class NxtApiAdminMemberLevelDeleteController {

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/member_level/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Integer levelNum = jsonParam.getInteger("levelNum");

        if (levelNum == null){
            return new NxtStructApiResult(53,"缺少参数：levelNum").toMap();
        }

        NxtUserLevel nxtUserLevel = nxtUserLevelService.queryByNum(levelNum);

        if (nxtUserLevel == null){
            return new NxtStructApiResult(53,"没找到该会员等级").toMap();
        }

        NxtUserLevel nxtUserLevelMax = nxtUserLevelService.queryMaxOne();

        if (!nxtUserLevelMax.getId().equals(nxtUserLevel.getId())){
            return new NxtStructApiResult(53,"必须从最大的等级开始删").toMap();
        }

        Long count = nxtUserService.adminQueryMemberCount(null,null,levelNum,null,null);

        if (count > 0){
            return new NxtStructApiResult(53,"存在该等级会员，不可删除该等级").toMap();
        }

        nxtUserLevelService.deleteById(nxtUserLevel.getId());

        return new NxtStructApiResult().toMap();

    }

}
