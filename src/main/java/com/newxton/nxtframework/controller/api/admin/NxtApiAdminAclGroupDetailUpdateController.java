package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.struct.NxtStructAclGroup;
import com.newxton.nxtframework.service.NxtAclGroupActionService;
import com.newxton.nxtframework.service.NxtAclGroupService;
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
public class NxtApiAdminAclGroupDetailUpdateController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/acl_group_detail_update", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructAclGroup nxtStructAclGroup = gson.fromJson(json,NxtStructAclGroup.class);

        Long groupId = nxtStructAclGroup.getId();
        String groupName = nxtStructAclGroup.getGroupName();
        String groupRemark = nxtStructAclGroup.getGroupRemark();

        List<Long> groupActionList = nxtStructAclGroup.getGroupActionList();

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (groupId == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtAclGroup nxtAclGroup = nxtAclGroupService.queryById(groupId);

        if (nxtAclGroup == null) {
            result.put("status", 49);
            result.put("message", "权限组不存在");
            return result;
        }

        if (groupName != null) {
            nxtAclGroup.setGroupName(groupName);
        }
        if (groupRemark != null) {
            nxtAclGroup.setGroupRemark(groupRemark);
        }

        //更新基本内容
        nxtAclGroupService.update(nxtAclGroup);


        //查询权限组关联
        NxtAclGroupAction nxtAclGroupAction = new NxtAclGroupAction();
        nxtAclGroupAction.setGroupId(groupId);
        List<NxtAclGroupAction> groupList = nxtAclGroupActionService.queryAll(nxtAclGroupAction);
        Set<Long> actionIdSet = new HashSet<>();
        for (NxtAclGroupAction item :
                groupList) {
            actionIdSet.add(item.getActionId());
        }

        //需要删除的actionId
        ArrayList<Long> listActionIdDelete = new ArrayList<>();
        //需要添加的actionId
        ArrayList<Long> listActionIdAdd = new ArrayList<>();

        for (Long actionId :
                groupActionList) {
            if (!actionIdSet.contains(actionId)) {
                listActionIdAdd.add(actionId);
            }
            else {
                actionIdSet.remove(actionId);
            }
        }

        if (actionIdSet.size() > 0) {
            listActionIdDelete.addAll(actionIdSet);
        }

        //添加需要添加的actionId
        for (Long actionIdAdd :
                listActionIdAdd) {
            NxtAclGroupAction nxtAclGroupActionAdd = new NxtAclGroupAction();
            nxtAclGroupActionAdd.setActionId(actionIdAdd);
            nxtAclGroupActionAdd.setGroupId(nxtAclGroup.getId());
            nxtAclGroupActionService.insert(nxtAclGroupActionAdd);
        }

        //删除需要删除的actionId
        for (Long actionIdDelete :
                listActionIdDelete) {
            NxtAclGroupAction nxtAclGroupActionDelete = new NxtAclGroupAction();
            nxtAclGroupActionDelete.setGroupId(nxtAclGroup.getId());
            nxtAclGroupActionDelete.setActionId(actionIdDelete);
            List<NxtAclGroupAction> listDelete = nxtAclGroupActionService.queryAll(nxtAclGroupActionDelete);
            for (NxtAclGroupAction item :
                    listDelete) {
                nxtAclGroupActionService.deleteById(item.getId());
            }
        }

        return result;

    }

}
