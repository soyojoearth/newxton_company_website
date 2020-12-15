package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormDetailController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping(value = "/api/order_form/detail", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提交订单id");
        }

        try {
            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(id);
            if (nxtStructOrderForm == null){
                return new NxtStructApiResult(34,"没有该订单");
            }
            else if (nxtStructOrderForm.getUserId().equals(userId)){
                return new NxtStructApiResult(nxtStructOrderForm);
            }
            else {
                //该订单不属于此用户
                return new NxtStructApiResult(34,"没有该订单");
            }
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage());
        }

    }

}
