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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminAclGroupAddController {

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/acl_group_add", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructAclGroup nxtStructAclGroup = gson.fromJson(json,NxtStructAclGroup.class);

        String groupName = nxtStructAclGroup.getGroupName();
        String groupRemark = nxtStructAclGroup.getGroupRemark();

        List<Long> groupActionList = nxtStructAclGroup.getGroupActionList();

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (groupName == null) {
            result.put("status", 52);
            result.put("message", "参数错误:groupName");
            return result;
        }

        NxtAclGroup nxtAclGroup = new NxtAclGroup();
        nxtAclGroup.setGroupName(groupName);

        if (groupRemark != null) {
            nxtAclGroup.setGroupRemark(groupRemark);
        }

        //insert
        nxtAclGroupService.insert(nxtAclGroup);

        //添加需要添加的actionId
        for (Long actionId :
                groupActionList) {
            NxtAclGroupAction nxtAclGroupActionAdd = new NxtAclGroupAction();
            nxtAclGroupActionAdd.setActionId(actionId);
            nxtAclGroupActionAdd.setGroupId(nxtAclGroup.getId());
            nxtAclGroupActionService.insert(nxtAclGroupActionAdd);
        }

        return result;

    }

}
