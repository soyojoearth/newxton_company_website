package com.newxton.nxtframework.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiNewsCategoryListController;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiNormalNewsListController;
import com.newxton.nxtframework.controller.api.front.cms.NxtApiRecommandNewsListController;
import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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

    @Resource
    NxtApiNewsCategoryListController nxtApiNewsCategoryListController;

    @Resource
    NxtApiRecommandNewsListController nxtApiRecommandNewsListController;

    @RequestMapping("/news")
    public ModelAndView index(Device device, ModelAndView model, @Param("page") Long page,@Param("categoryId") Long categoryId) {

        if (device.isMobile()) {
            model.setViewName("mobile/index");
            logger.info("移动端访客");
        } else {
            model.setViewName("pc/news_list");
            logger.info("PC端访客");
        }

        //资讯分类
        NxtStructApiResult newsCategoryData = nxtApiNewsCategoryListController.index();
        Map<String, Object> result1 = (Map<String, Object>) newsCategoryData.getResult();
        List<Map<String, Object>> listMap = (List<Map<String, Object>>) result1.get("list");
        model.addObject("newsCategoryList", listMap);
        //获取首个资讯分类
        if (!CollectionUtils.isEmpty(listMap) && categoryId == null) {
            NxtNewsCategory nxtNewsCategory = (NxtNewsCategory) listMap.get(0).get("category");
            categoryId = nxtNewsCategory.getId();
        }
        //资讯1
        Long limit = 4L;
        page = page == null ? 1 : page;
        JSONObject jsonParamNormalNews = new JSONObject();
        jsonParamNormalNews.put("root_category_id", categoryId);
        jsonParamNormalNews.put("limit", limit);
        jsonParamNormalNews.put("offset", (page - 1) * limit);
        jsonParamNormalNews.put("show_pages", 1);
        NxtStructApiResult normalNewsListData = nxtApiNormalNewsListController.exec(jsonParamNormalNews);
        model.addObject("normalNewsList", normalNewsListData.getResult());
        model.addObject("page", page);
        model.addObject("categoryId",categoryId == null ? "" : categoryId);

        //资讯2
        JSONObject jsonParamRecommandNews = new JSONObject();
        jsonParamRecommandNews.put("limit",8);
        jsonParamRecommandNews.put("offset",0);
        NxtStructApiResult recommandNewsListData = nxtApiRecommandNewsListController.exec(jsonParamRecommandNews);
        Map<String, Object> result2 = (Map<String, Object>) recommandNewsListData.getResult();
        model.addObject("recommandNewsList", result2.get("newsList"));

        return model;

    }

}
