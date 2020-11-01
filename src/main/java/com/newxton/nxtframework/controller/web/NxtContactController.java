package com.newxton.nxtframework.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NxtContactController {

    private Logger logger = LoggerFactory.getLogger(NxtContactController.class);

    @RequestMapping("/contact")
    public ModelAndView index(Device device,ModelAndView model) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
//            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/index");
//            logger.info("PC端访客");
        }

        return model;

    }

}
