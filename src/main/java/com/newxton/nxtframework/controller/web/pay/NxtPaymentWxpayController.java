package com.newxton.nxtframework.controller.web.pay;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtPaymentWxpayController {

    @RequestMapping("/payment/wxpay")
    public ModelAndView index(Device device, ModelAndView model, @RequestParam("serial_num") String serialNum) {


        model.addObject("body","微信支付功能暂不免费提供，请联系我们进行二次开发。或者自行开发。");

        model.setViewName("common/payment_wxpay");

        return model;

    }


}
