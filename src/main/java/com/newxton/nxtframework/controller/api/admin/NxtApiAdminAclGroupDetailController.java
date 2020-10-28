package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
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
public class NxtApiAdminAclGroupDetailController {

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @RequestMapping(value = "/api/admin/acl_group_detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "group_id", required = false) Long groupId) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtAclGroup nxtAclGroup = nxtAclGroupService.queryById(groupId);

        if (nxtAclGroup == null) {
            result.put("status", 49);
            result.put("message", "权限组不存在");
            return result;
        }

        Map<String, Object> detail = new HashMap<>();

        detail.put("groupId",nxtAclGroup.getId());
        detail.put("groupName",nxtAclGroup.getGroupName());
        detail.put("groupRemark",nxtAclGroup.getGroupRemark());

        //查询权限资源关联
        NxtAclGroupAction nxtAclGroupAction = new NxtAclGroupAction();
        nxtAclGroupAction.setGroupId(groupId);
        List<NxtAclGroupAction> groupActionList = nxtAclGroupActionService.queryAll(nxtAclGroupAction);
        Set<Long> actionIdSet = new HashSet<>();
        for (NxtAclGroupAction item :
                groupActionList) {
            actionIdSet.add(item.getActionId());
        }

        //查询所有权限资源，打勾或不打勾
        List<Map<String,Object>> listGroupActionDetail = new ArrayList<>();

        List<NxtAclAction> allActionList = nxtAclActionService.queryAll(new NxtAclAction());
        for (NxtAclAction item :
                allActionList) {
            Map<String,Object> groupActionDetail = new HashMap<>();
            groupActionDetail.put("actionId",item.getId());
            groupActionDetail.put("actionName",item.getActionName());
            groupActionDetail.put("actionRemark",item.getActionRemark());
            if (actionIdSet.contains(item.getId())){
                //打勾
                groupActionDetail.put("selected",true);
            }
            else {
                //不打勾
                groupActionDetail.put("selected",false);
            }
            listGroupActionDetail.add(groupActionDetail);
        }

        detail.put("listGroupActionDetail",listGroupActionDetail);

        result.put("detail",detail);

        return result;

    }

}
