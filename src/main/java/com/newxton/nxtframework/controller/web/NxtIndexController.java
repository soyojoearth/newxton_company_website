package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiBannerListController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListRecommendController;
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
public class NxtIndexController {

    private Logger logger = LoggerFactory.getLogger(NxtIndexController.class);

    @Resource
    NxtApiBannerListController nxtApiBannerListController;

    @Resource
    NxtApiProductListRecommendController nxtApiProductListRecommendController;


    @RequestMapping("/")
    public ModelAndView index(Device device,ModelAndView model) {

//        if (device.isMobile()){
//            model.setViewName("mobile/index");
//            logger.info("移动端访客");
//        }
//        else {
//            model.setViewName("pc/index");
//            logger.info("PC端访客");
//        }

        model.setViewName("pc/index");


        /**
         * 所有数据交互都必须通过接口调用，不得直接操作数据库
         * 移动端的App等前后端分离的面板就通过http调用接口，而这里直接依赖注入调用方法就好了
         */

        //轮播广告
        JSONObject jsonParamBanner= new JSONObject();
        jsonParamBanner.put("locationName","首页");
        NxtStructApiResult dataBanner = nxtApiBannerListController.exec(jsonParamBanner);
        model.addObject("bannerList",dataBanner.getResult());

        //两款新品
        JSONObject jsonParamNewProduct = new JSONObject();
        jsonParamNewProduct.put("limit",2);
        NxtStructApiResult dataNewProduct = nxtApiProductListRecommendController.exec(jsonParamNewProduct);
        model.addObject("productListNew",dataNewProduct.getResult());

        //四款优选产品
        JSONObject jsonParamRecommendProduct = new JSONObject();
        jsonParamRecommendProduct.put("limit",4);
        NxtStructApiResult dataRecommendProduct = nxtApiProductListRecommendController.exec(jsonParamRecommendProduct);
        model.addObject("productListRecommend",dataRecommendProduct.getResult());

        return model;

    }

}
