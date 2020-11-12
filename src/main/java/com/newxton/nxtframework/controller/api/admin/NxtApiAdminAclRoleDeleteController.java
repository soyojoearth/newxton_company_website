package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import com.newxton.nxtframework.entity.NxtAclUserRole;
import com.newxton.nxtframework.service.NxtAclGroupService;
import com.newxton.nxtframework.service.NxtAclRoleGroupService;
import com.newxton.nxtframework.service.NxtAclRoleService;
import com.newxton.nxtframework.service.NxtAclUserRoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminAclRoleDeleteController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Resource
    private NxtAclUserRoleService nxtAclUserRoleService;

    @Transactional
    @RequestMapping(value = "/api/admin/acl_role_delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "role_id", required = false) Long roleId) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtAclRole nxtAclRole = nxtAclRoleService.queryById(roleId);

        if (nxtAclRole == null) {
            result.put("status", 49);
            result.put("message", "角色不存在");
            return result;
        }

        //删除该角色的用户关联
        NxtAclUserRole nxtAclUserRole = new NxtAclUserRole();
        nxtAclUserRole.setRoleId(roleId);
        List<NxtAclUserRole> userRoleList = nxtAclUserRoleService.queryAll(nxtAclUserRole);
        for (NxtAclUserRole item :
                userRoleList) {
            nxtAclUserRoleService.deleteById(item.getId());
        }

        //删除角色的权限组关联
        NxtAclRoleGroup nxtAclRoleGroup = new NxtAclRoleGroup();
        nxtAclRoleGroup.setRoleId(roleId);
        List<NxtAclRoleGroup> groupList = nxtAclRoleGroupService.queryAll(nxtAclRoleGroup);
        for (NxtAclRoleGroup item :
                groupList) {
            nxtAclRoleGroupService.deleteById(item.getId());
        }

        //删除角色
        nxtAclRoleService.deleteById(roleId);

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
