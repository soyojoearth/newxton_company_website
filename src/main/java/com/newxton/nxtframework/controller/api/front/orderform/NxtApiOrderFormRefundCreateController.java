package com.newxton.nxtframework.controller.api.front.orderform;

import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefundCreate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 提交售后申请
 */
@RestController
public class NxtApiOrderFormRefundCreateController {

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @RequestMapping("/api/order_form_refund/create")
    public NxtStructApiResult exec(@RequestBody String json, @RequestHeader(value = "user_id", required = true) Long userId) {

        Gson gson = new Gson();

        NxtStructOrderFormRefundCreate nxtStructOrderFormRefundCreate;

        try {
             nxtStructOrderFormRefundCreate = gson.fromJson(json, NxtStructOrderFormRefundCreate.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对");
        }

        try {
            nxtProcessOrderFormRefund.create(userId,nxtStructOrderFormRefundCreate);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

        return new NxtStructApiResult();

    }

}
