package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.controller.api.front.NxtApiNormalNewsListController;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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
public class NxtNewsListController {

    private Logger logger = LoggerFactory.getLogger(NxtNewsListController.class);

    @Resource
    NxtApiNormalNewsListController nxtApiNormalNewsListController;

    @RequestMapping("/news")
    public ModelAndView index(Device device,ModelAndView model) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/index");
            logger.info("PC端访客");
        }

        //资讯1
        NxtStructApiResult apiData1 = nxtApiNormalNewsListController.exec(1L,4,null,null);
        Map<String,Object> result1 = (Map<String,Object>)apiData1.getResult();
        model.addObject("newsList1", result1.get("newsList"));

        //资讯2
        NxtStructApiResult apiData2 = nxtApiNormalNewsListController.exec(2L,4,null,null);
        Map<String,Object> result2 = (Map<String,Object>)apiData2.getResult();
        model.addObject("newsList2", result2.get("newsList"));


        return model;

    }

}
