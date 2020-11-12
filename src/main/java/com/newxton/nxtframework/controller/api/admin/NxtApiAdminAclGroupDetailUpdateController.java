package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSON;
import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.service.NxtAclGroupActionService;
import com.newxton.nxtframework.service.NxtAclGroupService;
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
public class NxtApiAdminAclGroupDetailUpdateController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Transactional
    @RequestMapping(value = "/api/admin/acl_group_detail_update", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "group_id", required = false) Long groupId,
            @RequestParam(value = "group_name", required = false) String groupName,
            @RequestParam(value = "group_remark", required = false) String groupRemark,
            @RequestParam(value = "group_action_list", required = false) String groupActionList
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (groupId == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        List<Long> actionIdListUpdate = new ArrayList<>();

        if (groupActionList != null) {
            try {
                actionIdListUpdate = JSON.parseArray(groupActionList).toJavaList(Long.class);
            } catch (Exception e) {
                result.put("status", 52);
                result.put("message", "参数错误:group_action_list");
                return result;
            }
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

        for (Long actionIdUpdate :
                actionIdListUpdate) {
            if (!actionIdSet.contains(actionIdUpdate)) {
                listActionIdAdd.add(actionIdUpdate);
            }
            else {
                actionIdSet.remove(actionIdUpdate);
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

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
