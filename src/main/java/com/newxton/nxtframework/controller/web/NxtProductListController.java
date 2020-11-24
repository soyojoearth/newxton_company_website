package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 */
@Controller
public class NxtProductListController {

    private Logger logger = LoggerFactory.getLogger(NxtProductListController.class);

    @Resource
    NxtApiProductListController nxtApiProductListController;

    @RequestMapping("/product/list")
    public ModelAndView index(Device device, ModelAndView model) {

//        if (device.isMobile()){
//            model.setViewName("mobile/product_list");
//            logger.info("移动端访客");
//        }
//        else {
//            model.setViewName("pc/product_list");
//            logger.info("PC端访客");
//        }


        return model;

    }


}
