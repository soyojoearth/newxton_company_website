package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.controller.api.front.NxtApiNormalNewsListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class NxtIndexController {

    private Logger logger = LoggerFactory.getLogger(NxtIndexController.class);

    @Resource
    NxtApiNormalNewsListController nxtApiNormalNewsListController;

    @RequestMapping("/")
    public ModelAndView index(Device device,ModelAndView model) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
//            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/index");
//            logger.info("PC端访客");
        }

        /**
         * 所有数据交互都必须通过接口调用，不得直接操作数据库
         * 移动端的App等前后端分离的面板就通过http调用接口，而这里直接依赖注入调用方法就好了
         */

        //资讯1
        Map<String, Object> apiResult1 = nxtApiNormalNewsListController.exec(1L,4,null,null);
        List<Map<String, Object>> newsList1 = (List<Map<String, Object>>) apiResult1.get("data");
        model.addObject("newsList1", newsList1);

        //资讯2
        Map<String, Object> apiResult2 = nxtApiNormalNewsListController.exec(2L,4,null,null);
        List<Map<String, Object>> newsList2 = (List<Map<String, Object>>) apiResult2.get("data");
        model.addObject("newsList2", newsList2);

        return model;

    }

}
