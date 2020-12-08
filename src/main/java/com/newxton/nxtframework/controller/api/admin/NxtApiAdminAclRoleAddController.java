package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import com.newxton.nxtframework.struct.NxtStructAclRole;
import com.newxton.nxtframework.service.NxtAclRoleGroupService;
import com.newxton.nxtframework.service.NxtAclRoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/acl_role_add", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructAclRole nxtStructAclRole = gson.fromJson(json,NxtStructAclRole.class);

        String roleName = nxtStructAclRole.getRoleName();
        String roleRemark = nxtStructAclRole.getRoleRemark();

        List<Long> roleGroupList = nxtStructAclRole.getRoleGroupList();

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (roleName == null) {
            result.put("status", 52);
            result.put("message", "参数错误:role_name");
            return result;
        }

        NxtAclRole nxtAclRole = new NxtAclRole();
        nxtAclRole.setRoleName(roleName);

        if (roleRemark != null) {
            nxtAclRole.setRoleRemark(roleRemark);
        }

        //insert
        nxtAclRoleService.insert(nxtAclRole);

        //添加需要添加的groupId
        for (Long groupId :
                roleGroupList) {
            NxtAclRoleGroup nxtAclRoleGroupAdd = new NxtAclRoleGroup();
            nxtAclRoleGroupAdd.setRoleId(nxtAclRole.getId());
            nxtAclRoleGroupAdd.setGroupId(groupId);
            nxtAclRoleGroupService.insert(nxtAclRoleGroupAdd);
        }

        return result;

    }

}
