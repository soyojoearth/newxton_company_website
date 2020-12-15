package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.service.NxtAclRoleService;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryCompanyListController {

    @Resource
    private NxtDeliveryCompanyService nxtDeliveryCompanyService;

    @RequestMapping(value = "/api/admin/delivery_company/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtDeliveryCompany> nxtDeliveryCompanyList = nxtDeliveryCompanyService.queryAllByLimit(0,Integer.MAX_VALUE);

        List<Map<String,Object>> list = new ArrayList<>();

        for (NxtDeliveryCompany item :
                nxtDeliveryCompanyList) {
            Map<String, Object> mapItem = new HashMap<>();
            mapItem.put("id",item.getId());
            mapItem.put("name",item.getName());
            mapItem.put("code100",item.getCode100());
            mapItem.put("activity",item.getActivity() != null && item.getActivity() > 0);
            list.add(mapItem);
        }

        result.put("list",list);

        return result;

    }

}
