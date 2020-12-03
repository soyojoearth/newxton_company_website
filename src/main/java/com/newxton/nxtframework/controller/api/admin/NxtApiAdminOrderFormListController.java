package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public NxtStructApiResult index(@RequestBody JSONObject jsonParam) {

        Boolean isPaid = jsonParam.getBoolean("isPaid");
        Boolean isDelivery = jsonParam.getBoolean("isDelivery");
        Integer dealPlatform = jsonParam.getInteger("dealPlatform");

        String datelineBegin = jsonParam.getString("datelineBegin");
        String datelineEnd = jsonParam.getString("datelineEnd");

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少offset或limit");
        }

        try {
            List<NxtStructOrderForm> nxtStructOrderFormList = nxtProcessOrderForm.adminOrderFormList(offset,limit,isPaid,isDelivery,dealPlatform,datelineBegin,datelineEnd);
            return new NxtStructApiResult(nxtStructOrderFormList);
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage());
        }

    }

}
