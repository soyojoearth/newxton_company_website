package com.newxton.nxtframework.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtUCenterController {

    private Logger logger = LoggerFactory.getLogger(NxtUCenterController.class);

    @RequestMapping("/ucenter/")
    public ModelAndView index(Device device, ModelAndView model) {

        if (device.isMobile()){

            model.setViewName("pc/ucenter");
//            model.setViewName("mobile/ucenter");
            logger.info("移动端访客");

        }
        else {
            model.setViewName("pc/ucenter");
            logger.info("PC端访客");
        }

        /**
         * UCenter包含用户登录、注册、注销、购物车、下单、登录后的用户中心等对于SEO无意义的全部界面
         * 这些界面全部由SPA完成
         */

        return model;

    }


}
