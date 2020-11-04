package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.model.NxtModelDdeliveryCompany;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminDdeliveryCompanyCreateController {

    @Resource
    private NxtModelDdeliveryCompany nxtModelDdeliveryCompany;

    @RequestMapping(value = "/api/admin/delivery_company/create", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        jsonParam.put("id",null);

        return nxtModelDdeliveryCompany.save(jsonParam);

    }

}
