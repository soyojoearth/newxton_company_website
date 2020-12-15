package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductDetailController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListNewController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListRecommendController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductReviewsListController;
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
 * @time 2020/11/22
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtProductDetailController {


    private Logger logger = LoggerFactory.getLogger(NxtProductDetailController.class);

    @Resource
    NxtApiProductDetailController nxtApiProductDetailController;

    @Resource
    NxtApiProductListRecommendController nxtApiProductListRecommendController;

    @Resource
    NxtApiProductListNewController nxtApiProductListNewController;

    @RequestMapping("/product/detail")
    public ModelAndView index(Device device, ModelAndView model, @Param("productId") Long productId, @Param("page") Long page) {

        //产品明细
        JSONObject jsonParamProductDetail = new JSONObject();
        jsonParamProductDetail.put("product_id", productId);
        NxtStructApiResult dataProductDetail = nxtApiProductDetailController.exec(jsonParamProductDetail);

        //三款推荐产品
        JSONObject jsonParamRecommendProduct = new JSONObject();
        jsonParamRecommendProduct.put("limit", 3);
        NxtStructApiResult dataRecommendProduct = nxtApiProductListRecommendController.exec(jsonParamRecommendProduct);

        //三款新品
        JSONObject jsonParamNewProduct = new JSONObject();
        jsonParamNewProduct.put("limit", 3);
        NxtStructApiResult dataNewProduct = nxtApiProductListNewController.exec(jsonParamRecommendProduct);

        model.addObject("productDetail", dataProductDetail.getResult());
        model.addObject("productListRecommend", dataRecommendProduct.getResult());
        model.addObject("productListNew", dataNewProduct.getResult());
        model.addObject("page",page);

        if (device.isMobile()) {
            model.setViewName("mobile/product_list");
            logger.info("移动端访客");
        } else {
            model.setViewName("pc/product_detail");
            logger.info("PC端访客");
        }


        return model;

    }


}
