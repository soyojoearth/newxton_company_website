package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefund;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/6
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormRefundDetailController {

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping(value = "/api/admin/order_form_refund/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提交售后id").toMap();
        }

        try {
            NxtStructOrderFormRefund nxtStructOrderFormRefund = nxtProcessOrderFormRefund.allDetail(id);
            if (nxtStructOrderFormRefund == null){
                return new NxtStructApiResult(34,"没有该售后").toMap();
            }
            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(nxtStructOrderFormRefund.getOrderFormId());
            Map<String,Object> result = new HashMap<>();
            result.put("orderFormRefundDetail",nxtStructOrderFormRefund);
            result.put("orderFormDetail",nxtStructOrderForm);
            return new NxtStructApiResult(result).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage()).toMap();
        }

    }

}
