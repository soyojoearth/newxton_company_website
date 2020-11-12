package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclUserRole;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtAclUserRoleService;
import com.newxton.nxtframework.service.NxtUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/22
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminResetUserRoleController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserRoleService nxtAclUserRoleService;

    @Resource
    private NxtAclComponent nxtAclComponent;

    @RequestMapping(value = "/api/admin/reset_user_role", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("user_id") Long adminUserId,
            @RequestParam(value = "reset_user_id", required = false) Long resetUserId,
            @RequestParam(value = "reset_user_role", required = false) Long resetUserRoleId
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (resetUserId == null || resetUserRoleId == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        NxtUser user = nxtUserService.queryById(resetUserId);

        if (user == null){
            result.put("status",44);
            result.put("message","用户不存在");
            return result;
        }

        if (adminUserId.equals(user.getId())){
            //不能调整自己
            result.put("status",53);
            result.put("message","不能调整自己");
            return result;
        }

        NxtAclUserRole nxtAclUserRole = nxtAclUserRoleService.queryByUserId(resetUserId);

        if (nxtAclUserRole == null){
            nxtAclUserRole = new NxtAclUserRole();
            nxtAclUserRole.setUserId(user.getId());
            nxtAclUserRole.setRoleId(resetUserRoleId);
            nxtAclUserRoleService.insert(nxtAclUserRole);
        }
        else {
            nxtAclUserRole.setRoleId(resetUserRoleId);
            nxtAclUserRoleService.update(nxtAclUserRole);
        }

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
