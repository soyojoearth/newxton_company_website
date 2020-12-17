package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormConfirmReceivedController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping("/api/order_form/confirm_received")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提供订单id");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);

        if (nxtOrderForm != null && nxtOrderForm.getUserId().equals(userId)){
            try {
                nxtProcessOrderForm.orderFormConfirmReceived(nxtOrderForm.getId());
                return new NxtStructApiResult();
            }
            catch (NxtException e){
                return new NxtStructApiResult(54,e.getNxtExecptionMessage());
            }
        }
        else {
            return new NxtStructApiResult(34,"未找到订单或该订单不属于你");
        }

    }

}
