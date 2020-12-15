package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefund;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/25
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 售后订单列表
 */
@RestController
public class NxtApiOrderFormRefundListController {

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @RequestMapping(value = "/api/order_form_refund/list", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        Boolean isDone = jsonParam.getBoolean("isDone");
        Boolean isShippedOrWaitShipping = jsonParam.getBoolean("isShippedOrWaitShipping");
        Boolean isApplied = jsonParam.getBoolean("isApplied");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少offset或limit");
        }

        try {
            List<NxtStructOrderFormRefund> nxtStructOrderFormRefundList = nxtProcessOrderFormRefund.userOrderFormRefundList(userId,offset,limit,isDone,isShippedOrWaitShipping,isApplied);
            return new NxtStructApiResult(nxtStructOrderFormRefundList);
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage());
        }



    }

}
