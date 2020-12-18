package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductCategoryListController;
import com.newxton.nxtframework.controller.api.front.product.NxtApiProductListController;
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
public class NxtProductListController {

    private Logger logger = LoggerFactory.getLogger(NxtProductListController.class);

    @Resource
    NxtApiProductListController nxtApiProductListController;

    @Resource
    NxtApiProductCategoryListController nxtApiProductCategoryListController;

    @RequestMapping("/product/list")
    public ModelAndView index(Device device, ModelAndView model, @Param("categoryId") Long categoryId,
                              @Param("searchKeyword") String searchKeyword, @Param("page") Long page,
                              @Param("orderType") Long orderType) {

        //产品列表
        JSONObject jsonParamProductList = new JSONObject();
        Long limit = 6L;
        page = page == null ? 1 : page;
        jsonParamProductList.put("categoryId", categoryId);
        jsonParamProductList.put("searchKeyword", searchKeyword);
        jsonParamProductList.put("requirePages", 1);
        jsonParamProductList.put("offset", (page - 1) * limit);
        jsonParamProductList.put("limit", limit);
        jsonParamProductList.put("orderType", orderType);
//        jsonParamProductList.put("orderType", null);//1按价格从高到低 -1按价格从低到高 2按更新时间从近到远 -2按更新时间从远到近

        NxtStructApiResult dataProductList = nxtApiProductListController.exec(jsonParamProductList);

        //产品分类
        NxtStructApiResult dataProductCategoryList = nxtApiProductCategoryListController.index();

        model.addObject("page", page);
        model.addObject("categoryId", categoryId == null ? "" : categoryId);
        model.addObject("searchKeyword", searchKeyword == null ? "" : searchKeyword);
        model.addObject("orderType", orderType == null ? "" : orderType);
        model.addObject("productList", dataProductList.getResult());
        model.addObject("productCategoryList", dataProductCategoryList.getResult());

        if (device.isMobile()) {
            model.setViewName("mobile/product_list");
            logger.info("移动端访客");
        } else {
            model.setViewName("pc/product_list");
            logger.info("PC端访客");
        }


        return model;

    }


}
