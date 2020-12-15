package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefund;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormRefundDetailController {

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @RequestMapping(value = "/api/order_form_refund/detail", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提交售后id");
        }

        try {
            NxtStructOrderFormRefund nxtStructOrderFormRefund = nxtProcessOrderFormRefund.allDetail(id);
            if (nxtStructOrderFormRefund == null){
                return new NxtStructApiResult(34,"没有该售后");
            }
            else if (nxtStructOrderFormRefund.getUserId().equals(userId)){
                return new NxtStructApiResult(nxtStructOrderFormRefund);
            }
            else {
                //该售后不属于此用户
                return new NxtStructApiResult(34,"没有该售后");
            }
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage());
        }

    }

}
