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
public class NxtApiAdminAclGroupAddController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Transactional
    @RequestMapping(value = "/api/admin/acl_group_add", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "group_name", required = false) String groupName,
            @RequestParam(value = "group_remark", required = false) String groupRemark,
            @RequestParam(value = "group_action_list", required = false) String groupActionList
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (groupName == null) {
            result.put("status", 52);
            result.put("message", "参数错误:groupName");
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


        NxtAclGroup nxtAclGroup = new NxtAclGroup();
        nxtAclGroup.setGroupName(groupName);

        if (groupRemark != null) {
            nxtAclGroup.setGroupRemark(groupRemark);
        }

        //insert
        nxtAclGroupService.insert(nxtAclGroup);

        //添加需要添加的actionId
        for (Long actionIdAdd :
                actionIdListUpdate) {
            NxtAclGroupAction nxtAclGroupActionAdd = new NxtAclGroupAction();
            nxtAclGroupActionAdd.setActionId(actionIdAdd);
            nxtAclGroupActionAdd.setGroupId(nxtAclGroup.getId());
            nxtAclGroupActionService.insert(nxtAclGroupActionAdd);
        }

        //提交cronjob任务，清除Acl缓存
        nxtAclComponent.addJobForCleanCache();

        return result;

    }

}
