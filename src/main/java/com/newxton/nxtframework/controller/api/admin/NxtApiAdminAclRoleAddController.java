package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSON;
import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import com.newxton.nxtframework.service.NxtAclRoleGroupService;
import com.newxton.nxtframework.service.NxtAclRoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminAclRoleAddController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Transactional
    @RequestMapping(value = "/api/admin/acl_role_add", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "role_name", required = false) String roleName,
            @RequestParam(value = "role_remark", required = false) String roleRemark,
            @RequestParam(value = "role_group_list", required = false) String roleGroupList
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (roleName == null) {
            result.put("status", 52);
            result.put("message", "参数错误:role_name");
            return result;
        }

        List<Long> groupIdListUpdate = new ArrayList<>();

        if (roleGroupList != null) {
            try {
                groupIdListUpdate = JSON.parseArray(roleGroupList).toJavaList(Long.class);
            } catch (Exception e) {
                result.put("status", 52);
                result.put("message", "参数错误:role_group_list");
                return result;
            }
        }

        NxtAclRole nxtAclRole = new NxtAclRole();
        nxtAclRole.setRoleName(roleName);

        if (roleRemark != null) {
            nxtAclRole.setRoleRemark(roleRemark);
        }

        //insert
        nxtAclRoleService.insert(nxtAclRole);

        //添加需要添加的groupId
        for (Long groupIdAdd :
                groupIdListUpdate) {
            NxtAclRoleGroup nxtAclRoleGroupAdd = new NxtAclRoleGroup();
            nxtAclRoleGroupAdd.setRoleId(nxtAclRole.getId());
            nxtAclRoleGroupAdd.setGroupId(groupIdAdd);
            nxtAclRoleGroupService.insert(nxtAclRoleGroupAdd);
        }

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
