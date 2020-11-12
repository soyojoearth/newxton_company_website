package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.service.NxtAclGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminAclGroupListController {

    @Resource
    private NxtAclGroupService nxtAclGroupService;

    @RequestMapping(value = "/api/admin/acl_group_list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtAclGroup> list = nxtAclGroupService.queryAll(new NxtAclGroup());
        result.put("list",list);

        return result;

    }

}
