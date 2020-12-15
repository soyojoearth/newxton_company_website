package com.newxton.nxtframework.controller.web.pay;

import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.newxton.nxtframework.component.NxtPaymentAlipayComponent;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtRechargeService;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtPaymentAlipayController {

    @Resource
    private NxtPaymentAlipayComponent nxtPaymentAlipayComponent;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @RequestMapping("/payment/alipay")
    public ModelAndView index(Device device, ModelAndView model, @RequestParam("serial_num") String serialNum) {


        NxtRecharge nxtRecharge = nxtRechargeService.queryBySerialNum(serialNum);

        if (nxtRecharge == null){
            model.addObject("body","找不到支付订单");
            model.setViewName("common/payment_alipay");
            return model;
        }

        try {
            if (device.isMobile()){
                AlipayTradeWapPayResponse response = nxtPaymentAlipayComponent.createWapPayResponse(nxtRecharge.getSerialNum());
                String body = response.getBody();
                model.addObject("body",body);
            }
            else {
                AlipayTradePagePayResponse response = nxtPaymentAlipayComponent.createPagePayResponse(nxtRecharge.getSerialNum());
                String body = response.getBody();
                model.addObject("body",body);
            }

        }
        catch (NxtException e){
            // TODO
            model.addObject("body",e.getNxtExecptionMessage());
        }

        model.setViewName("common/payment_alipay");

        return model;

    }

}
