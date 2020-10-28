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
public class NxtApiAdminAclRoleDetailUpdateController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Transactional
    @RequestMapping(value = "/api/admin/acl_role_detail_update", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "role_id", required = false) Long roleId,
            @RequestParam(value = "role_name", required = false) String roleName,
            @RequestParam(value = "role_remark", required = false) String roleRemark,
            @RequestParam(value = "role_group_list", required = false) String roleGroupList
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (roleId == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
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


        NxtAclRole nxtAclRole = nxtAclRoleService.queryById(roleId);

        if (nxtAclRole == null) {
            result.put("status", 49);
            result.put("message", "角色不存在");
            return result;
        }

        if (roleName != null) {
            nxtAclRole.setRoleName(roleName);
        }
        if (roleRemark != null) {
            nxtAclRole.setRoleRemark(roleRemark);
        }

        //更新基本内容
        nxtAclRoleService.update(nxtAclRole);


        //查询权限组关联
        NxtAclRoleGroup nxtAclRoleGroup = new NxtAclRoleGroup();
        nxtAclRoleGroup.setRoleId(roleId);
        List<NxtAclRoleGroup> groupList = nxtAclRoleGroupService.queryAll(nxtAclRoleGroup);
        Set<Long> groupIdSet = new HashSet<>();
        for (NxtAclRoleGroup item :
                groupList) {
            groupIdSet.add(item.getGroupId());
        }

        //需要删除的groupId
        ArrayList<Long> listGroupIdDelete = new ArrayList<>();
        //需要添加的groupId
        ArrayList<Long> listGroupIdAdd = new ArrayList<>();

        for (Long groupIdUpdate :
                groupIdListUpdate) {
            if (!groupIdSet.contains(groupIdUpdate)) {
                listGroupIdAdd.add(groupIdUpdate);
            }
            else {
                groupIdSet.remove(groupIdUpdate);
            }
        }

        if (groupIdSet.size() > 0) {
            listGroupIdDelete.addAll(groupIdSet);
        }

        //添加需要添加的groupId
        for (Long groupIdAdd :
                listGroupIdAdd) {
            NxtAclRoleGroup nxtAclRoleGroupAdd = new NxtAclRoleGroup();
            nxtAclRoleGroupAdd.setRoleId(nxtAclRole.getId());
            nxtAclRoleGroupAdd.setGroupId(groupIdAdd);
            nxtAclRoleGroupService.insert(nxtAclRoleGroupAdd);
        }

        //删除需要删除的groupId
        for (Long groupIdDelete :
                listGroupIdDelete) {
            NxtAclRoleGroup nxtAclRoleGroupDelete = new NxtAclRoleGroup();
            nxtAclRoleGroupDelete.setGroupId(groupIdDelete);
            nxtAclRoleGroupDelete.setRoleId(nxtAclRole.getId());
            List<NxtAclRoleGroup> listDelete = nxtAclRoleGroupService.queryAll(nxtAclRoleGroupDelete);
            for (NxtAclRoleGroup item :
                    listDelete) {
                nxtAclRoleGroupService.deleteById(item.getId());
            }
        }

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
