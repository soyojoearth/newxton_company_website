package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclAction;
import com.newxton.nxtframework.service.NxtAclActionService;
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
public class NxtApiAdminAclActionListController {

    @Resource
    private NxtAclActionService nxtAclActionService;

    @RequestMapping(value = "/api/admin/acl_action_list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtAclAction> list = nxtAclActionService.queryAll(new NxtAclAction());
        result.put("list",list);

        return result;

    }

}
