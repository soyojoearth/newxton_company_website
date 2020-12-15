package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminAclGroupDeleteController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/acl_group_delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long groupId = jsonParam.getLong("id");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtAclGroup nxtAclGroup = nxtAclGroupService.queryById(groupId);

        if (nxtAclGroup == null) {
            result.put("status", 49);
            result.put("message", "权限组不存在");
            return result;
        }

        //删除权限组的角色关联
        NxtAclRoleGroup nxtAclRoleGroup = new NxtAclRoleGroup();
        nxtAclRoleGroup.setGroupId(groupId);
        List<NxtAclRoleGroup> roleGroupList = nxtAclRoleGroupService.queryAll(nxtAclRoleGroup);
        for (NxtAclRoleGroup item :
                roleGroupList) {
            nxtAclRoleGroupService.deleteById(item.getId());
        }

        //删除权限组的权限资源关联
        NxtAclGroupAction nxtAclGroupAction = new NxtAclGroupAction();
        nxtAclGroupAction.setGroupId(groupId);
        List<NxtAclGroupAction> groupActionList = nxtAclGroupActionService.queryAll(nxtAclGroupAction);
        for (NxtAclGroupAction item :
                groupActionList) {
            nxtAclGroupActionService.deleteById(item.getId());
        }

        //删除权限组
        nxtAclGroupService.deleteById(groupId);

        return result;

    }

}
