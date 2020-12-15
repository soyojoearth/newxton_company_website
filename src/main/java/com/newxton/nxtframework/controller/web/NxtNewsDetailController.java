package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiNewsDetailController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListRecommendController;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.ibatis.annotations.Param;
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
public class NxtNewsDetailController {

    private Logger logger = LoggerFactory.getLogger(NxtNewsDetailController.class);

    @Resource
    NxtApiNewsDetailController nxtApiNewsDetailController;

    @Resource
    NxtApiProductListRecommendController nxtApiProductListRecommendController;

    @RequestMapping(value = "/news/detail" )
    public ModelAndView index(Device device, ModelAndView model, @Param("id") Long id) {

        if (device.isMobile()){
            model.setViewName("mobile/index");
//            logger.info("移动端访客");
        }
        else {
            model.setViewName("pc/news_detail");
//            logger.info("PC端访客");
        }

        //资讯详情
        JSONObject jsonParamDetail = new JSONObject();
        jsonParamDetail.put("id", id);
        NxtStructApiResult newsDetailData = nxtApiNewsDetailController.exec(jsonParamDetail);
        model.addObject("newsDetail",newsDetailData.getResult());

        //五款推荐产品
        JSONObject jsonParamRecommendProduct = new JSONObject();
        jsonParamRecommendProduct.put("limit", 5);
        NxtStructApiResult dataRecommendProduct = nxtApiProductListRecommendController.exec(jsonParamRecommendProduct);
        model.addObject("productListRecommend",dataRecommendProduct.getResult());

        return model;

    }

}
