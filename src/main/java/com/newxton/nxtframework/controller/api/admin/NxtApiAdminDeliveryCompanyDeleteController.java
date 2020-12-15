package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryCompanyDeleteController {

    @Resource
    private NxtDeliveryCompanyService nxtDeliveryCompanyService;

    @RequestMapping(value = "/api/admin/delivery_company/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtDeliveryCompany content = nxtDeliveryCompanyService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        nxtDeliveryCompanyService.deleteById(content.getId());

        return result;

    }

}
