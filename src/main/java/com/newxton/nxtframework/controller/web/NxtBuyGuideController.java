package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtSaaSCoreComponent;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiWebPageDetailController;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtBuyGuideController {

    private Logger logger = LoggerFactory.getLogger(NxtBuyGuideController.class);

    @Resource
    NxtSaaSCoreComponent nxtSaaSCoreComponent;

    @Resource
    NxtApiWebPageDetailController nxtApiWebPageDetailController;

    @RequestMapping("/buy_guide")
    public ModelAndView index(Device device,ModelAndView model) {

//        if (device.isMobile()){
//            model.setViewName("mobile/"+nxtSaaSCoreComponent.findTenantTempleteMobile()+"/buy_guide");
////            logger.info("移动端访客");
//        }
//        else {
            model.setViewName("pc/"+nxtSaaSCoreComponent.findTenantTempletePc()+"/buy_guide");
//            logger.info("PC端访客");
//        }

        //购买指南
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("key","buy_guide");
        NxtStructApiResult webPageDetail = nxtApiWebPageDetailController.exec(jsonParam);
        model.addObject("webPageDetail",webPageDetail.getResult());

        return model;

    }


}
