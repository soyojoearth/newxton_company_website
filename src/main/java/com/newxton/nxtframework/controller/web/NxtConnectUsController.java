package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtSaaSCoreComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/14
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtConnectUsController {

    private Logger logger = LoggerFactory.getLogger(NxtConnectUsController.class);

    @Resource
    NxtSaaSCoreComponent nxtSaaSCoreComponent;

    @RequestMapping("/connect_us")
    public ModelAndView index(Device device,ModelAndView model) {

//        if (device.isMobile()){
//            model.setViewName("mobile/"+nxtSaaSCoreComponent.findTenantTempleteMobile()+"/connect_us");
////            logger.info("移动端访客");
//        }
//        else {
            model.setViewName("pc/"+nxtSaaSCoreComponent.findTenantTempletePc()+"/connect_us");
//            logger.info("PC端访客");
//        }

        return model;

    }

}
