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

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtAboutUsController {

    private Logger logger = LoggerFactory.getLogger(NxtAboutUsController.class);

    @Resource
    NxtApiWebPageDetailController nxtApiWebPageDetailController;

    @RequestMapping("/about")
    public ModelAndView index(Device device,ModelAndView model) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
//            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/about_us");
//            logger.info("PC端访客");
        }

        //关于我们
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("key","about_us");
        NxtStructApiResult webPageDetail = nxtApiWebPageDetailController.exec(jsonParam);
        model.addObject("webPageDetail",webPageDetail.getResult());

        return model;

    }


}
