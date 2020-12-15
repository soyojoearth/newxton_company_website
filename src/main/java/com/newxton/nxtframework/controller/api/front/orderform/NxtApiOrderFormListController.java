package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.struct.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormListController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping(value = "/api/order_form/list", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Boolean isPaid = jsonParam.getBoolean("isPaid");
        Boolean isDelivery = jsonParam.getBoolean("isDelivery");
        Boolean isReviews = jsonParam.getBoolean("isReviews");

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少offset或limit");
        }

        try {
            List<NxtStructOrderForm> nxtStructOrderFormList = nxtProcessOrderForm.userOrderFormList(userId,offset,limit,isPaid,isDelivery,isReviews);
            return new NxtStructApiResult(nxtStructOrderFormList);
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage());
        }

    }

}
