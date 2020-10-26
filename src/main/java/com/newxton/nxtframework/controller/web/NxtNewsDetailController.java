package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.controller.api.front.NxtApiNewsDetailController;
import com.newxton.nxtframework.controller.api.front.NxtApiNormalNewsListController;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NxtNewsDetailController {

    @Resource
    NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    NxtApiNewsDetailController nxtApiNewsDetailController;

    @Resource
    NxtApiNormalNewsListController nxtApiNormalNewsListController;

    @RequestMapping(value = "/news/detail" )
    public ModelAndView index(Device device, ModelAndView model, @RequestParam("id") Long id) throws InterruptedException {

        boolean isMobile = device.isMobile();

        boolean isSpider = nxtWebUtilComponent.isSpider();

        if (!isSpider){
            /**
             * 前后端分离，无论PC端还是移动端全部用SPA开发
             * SPA是单页应用，所以无论那个Controler里面都渲染h5/index.html或pc/index.html这个编译单页
             * 但是，SPA应用也是可以自定义url路由的，可以定义成和pc端一样的url，保持一致
             */
            if (isMobile) {
                model.setViewName("mobile/index");
            } else {
                model.setViewName("pc/index");
            }
        }
        else {

            /**
             * 之所以准备一个"搜索引擎特供版渲染"，是为了前后端分离的同时也能照顾到搜索引擎收录
             * 后端工程师只要写个简单的不带css样式的页面就ok了
             * 大大降低前后端工程师的交流成本、后续的修改成本，且还可以专门针对搜索引擎优化
             */

            //SEO优化&搜索引擎特供版
            model.setViewName("seo/news_detail");

            //资讯详情
            Map<String,Object> apiResult = nxtApiNewsDetailController.exec(id);
            Map<String,Object> detail = (Map<String,Object>)apiResult.get("data");
            model.addObject("detail",detail);

            //相关资讯
            Long categoryId = Long.valueOf(detail.get("categoryId").toString());
            Map<String,Object> apiResultNewsList = nxtApiNormalNewsListController.exec(categoryId,3,null,null);
            List<Map<String,Object>> newsList = (List<Map<String,Object>>)apiResultNewsList.get("data");
            model.addObject("newsList_bottom",newsList);

            //相关资讯
            Map<String,Object> apiResultNewsListRight = nxtApiNormalNewsListController.exec(null,6,null,null);
            List<Map<String,Object>> newsListRight = (List<Map<String,Object>>)apiResultNewsListRight.get("data");
            model.addObject("newsList_right",newsListRight);

        }

        return model;

    }

}
