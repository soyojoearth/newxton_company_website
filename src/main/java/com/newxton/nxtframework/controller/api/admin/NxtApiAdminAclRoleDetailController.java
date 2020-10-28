package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import com.newxton.nxtframework.service.NxtAclGroupService;
import com.newxton.nxtframework.service.NxtAclRoleGroupService;
import com.newxton.nxtframework.service.NxtAclRoleService;
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
public class NxtApiAdminAclRoleDetailController {

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @RequestMapping(value = "/api/admin/acl_role_detail", method = RequestMethod.POST)
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

        Map<String, Object> detail = new HashMap<>();

        detail.put("roleId",nxtAclRole.getId());
        detail.put("roleName",nxtAclRole.getRoleName());
        detail.put("roleRemark",nxtAclRole.getRoleRemark());

        //查询权限组关联
        NxtAclRoleGroup nxtAclRoleGroup = new NxtAclRoleGroup();
        nxtAclRoleGroup.setRoleId(roleId);
        List<NxtAclRoleGroup> groupList = nxtAclRoleGroupService.queryAll(nxtAclRoleGroup);
        Set<Long> groupIdSet = new HashSet<>();
        for (NxtAclRoleGroup item :
                groupList) {
            groupIdSet.add(item.getGroupId());
        }

        //查询所有权限组，打勾或不打勾
        List<Map<String,Object>> listRoleGroupDetail = new ArrayList<>();

        List<NxtAclGroup> allGroupList = nxtAclGroupService.queryAll(new NxtAclGroup());
        for (NxtAclGroup item :
                allGroupList) {
            Map<String,Object> roleGroupDetail = new HashMap<>();
            roleGroupDetail.put("groupId",item.getId());
            roleGroupDetail.put("groupName",item.getGroupName());
            roleGroupDetail.put("groupRemark",item.getGroupRemark());
            if (groupIdSet.contains(item.getId())){
                //打勾
                roleGroupDetail.put("selected",true);
            }
            else {
                //不打勾
                roleGroupDetail.put("selected",false);
            }
            listRoleGroupDetail.add(roleGroupDetail);
        }

        detail.put("listRoleGroupDetail",listRoleGroupDetail);

        result.put("detail",detail);

        return result;

    }

}
