package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtAclRoleService;
import com.newxton.nxtframework.service.NxtBannerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/22
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminAclRoleListController {

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @RequestMapping(value = "/api/admin/acl_role_list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtAclRole> list = nxtAclRoleService.queryAll(new NxtAclRole());
        result.put("list",list);

        return result;

    }

}
