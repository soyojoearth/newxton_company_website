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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminAclRoleDetailUpdateController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/acl_role_detail_update", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructAclRole nxtStructAclRole = gson.fromJson(json,NxtStructAclRole.class);

        Long roleId = nxtStructAclRole.getId();
        String roleName = nxtStructAclRole.getRoleName();
        String roleRemark = nxtStructAclRole.getRoleRemark();

        List<Long> roleGroupList = nxtStructAclRole.getRoleGroupList();

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (roleId == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
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

        for (Long groupId :
                roleGroupList) {
            if (!groupIdSet.contains(groupId)) {
                listGroupIdAdd.add(groupId);
            }
            else {
                groupIdSet.remove(groupId);
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

        return result;

    }

}
