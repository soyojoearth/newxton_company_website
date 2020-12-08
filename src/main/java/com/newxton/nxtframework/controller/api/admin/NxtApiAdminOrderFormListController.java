package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormListController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping(value = "/api/admin/order_form/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Boolean isPaid = jsonParam.getBoolean("isPaid");
        Boolean isDelivery = jsonParam.getBoolean("isDelivery");
        Integer dealPlatform = jsonParam.getInteger("dealPlatform");

        String datelineBegin = jsonParam.getString("datelineBegin");
        String datelineEnd = jsonParam.getString("datelineEnd");

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少offset或limit").toMap();
        }

        try {
            List<NxtStructOrderForm> nxtStructOrderFormList = nxtProcessOrderForm.adminOrderFormList(offset,limit,isPaid,isDelivery,dealPlatform,datelineBegin,datelineEnd);
            Long count = nxtProcessOrderForm.adminOrderFormCount(isPaid,isDelivery,dealPlatform,datelineBegin,datelineEnd);
            Map<String,Object> result = new HashMap<>();
            result.put("list",nxtStructOrderFormList);
            result.put("count",count);
            return new NxtStructApiResult(result).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage()).toMap();
        }

    }

}
