package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiWebPageDetailController;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class NxtTermsStateController {

    private Logger logger = LoggerFactory.getLogger(NxtTermsStateController.class);

    @Resource
    NxtApiWebPageDetailController nxtApiWebPageDetailController;

    @RequestMapping("/terms_state")
    public ModelAndView index(Device device,ModelAndView model) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
//            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/terms_state");
//            logger.info("PC端访客");
        }

        //条款声明
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("key","terms_state");
        NxtStructApiResult webPageDetail = nxtApiWebPageDetailController.exec(jsonParam);
        model.addObject("webPageDetail",webPageDetail.getResult());

        return model;

    }


}
