package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
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
public class NxtApiAdminMemberUpdateCommissionController {

    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/member/update_commission", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long userId = jsonParam.getLong("userId");
        Boolean canInvite = jsonParam.getBoolean("canInvite");
        String inviterUsername = jsonParam.getString("inviterUsername");

        NxtUser nxtUser = nxtUserService.queryById(userId);

        if (nxtUser == null) {
            return new NxtStructApiResult(53, "找不到该会员").toMap();
        }

        Boolean needUpdate = false;

        //更新分销权限
        if (canInvite != null) {
            nxtUser.setCanInvite(canInvite ? 1 : 0);
            needUpdate = true;
        }

        //更新推荐人
        if (inviterUsername != null && !inviterUsername.trim().isEmpty()){
            NxtUser nxtUserInviter = nxtUserService.queryByUsername(inviterUsername);
            if (nxtUserInviter != null){
                if (nxtUserInviter.getId() > userId){
                    return new NxtStructApiResult(53,"推荐人不可以注册得比被推荐人更晚，否则系统会出错").toMap();
                }
                if (nxtUserInviter.getId().equals(userId)){
                    return new NxtStructApiResult(53,"不可以自己推荐自己").toMap();
                }
                nxtUser.setInviterUserId(nxtUserInviter.getId());
                needUpdate = true;
            }
            else {
                return new NxtStructApiResult(53,"找不到该推荐人："+inviterUsername).toMap();
            }
        }

        if (needUpdate){
            nxtUserService.update(nxtUser);
        }

        return new NxtStructApiResult().toMap();

    }
}
