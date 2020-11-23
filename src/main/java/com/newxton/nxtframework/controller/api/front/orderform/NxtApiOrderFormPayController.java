package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 */
@RestController
public class NxtApiOrderFormPayController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtWebUtilComponent nxtWebUtilComponent;

    @RequestMapping("/api/order_form/pay")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");

        String paymentMethod = jsonParam.getString("paymentMethod");

        if (id == null){
            return new NxtStructApiResult(54,"请提供订单id");
        }
        if (paymentMethod == null){
            return new NxtStructApiResult(54,"请提交付款方式");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);

        if (!(nxtOrderForm != null && nxtOrderForm.getUserId().equals(userId))){
            return new NxtStructApiResult(34,"未找到订单或该订单不属于你");
        }

        if (nxtOrderForm.getStatusPaid() > 0){
            //已经付款过了
            return new NxtStructApiResult(24,"该订单已经付过了，请勿重复支付");
        }

        Map<String,Object> data = new HashMap<>();

        if (paymentMethod.equals("alipay")){
            //生成支付宝付款链接或其它付款凭证放入data
        }
        else if (paymentMethod.equals("wxpay")){
            //生成微信付款链接或其它付款凭证放入data
        }
        else if (paymentMethod.equals("paypal")){
            //生成paypal付款链接或其它付款凭证放入data
        }
        else if (paymentMethod.equals("balance")){
            //生成余额付款链接或其它付款凭证放入data
        }

        //开发阶段测试自动付款
        data.put("redirectURL",nxtWebUtilComponent.getDomainPath()+"/test_pay?id="+id+"&paymentMethod="+paymentMethod);

        return new NxtStructApiResult(data);

    }

}
