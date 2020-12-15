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

        // TODO

        model.addObject("body","微信付款还在调试对接中...请更换付款方式");

        model.setViewName("common/payment_wxpay");

        return model;

    }


}
