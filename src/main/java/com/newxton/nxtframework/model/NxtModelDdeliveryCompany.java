package com.newxton.nxtframework.model;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 */
@Component
public class NxtModelDdeliveryCompany {

    @Resource
    private NxtDeliveryCompanyService nxtDeliveryCompanyService;

    public Map<String, Object> save(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        String name = jsonParam.getString("name");
        String code100 = jsonParam.getString("code100");
        Boolean activity = jsonParam.getBoolean("activity");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtDeliveryCompany nxtDeliveryCompany;

        if (id == null){
            nxtDeliveryCompany = new NxtDeliveryCompany();
        }
        else {
            nxtDeliveryCompany = nxtDeliveryCompanyService.queryById(id);
        }

        if (nxtDeliveryCompany == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        nxtDeliveryCompany.setId(id);
        nxtDeliveryCompany.setName(name);
        nxtDeliveryCompany.setCode100(code100);
        nxtDeliveryCompany.setActivity(activity ? 1 : 0);

        if (id == null){
            nxtDeliveryCompanyService.insert(nxtDeliveryCompany);
        }
        else {
            nxtDeliveryCompanyService.update(nxtDeliveryCompany);
        }

        result.put("detail", nxtDeliveryCompany);

        return result;

    }

}
