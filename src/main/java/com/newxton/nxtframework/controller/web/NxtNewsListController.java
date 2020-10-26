package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtWebUtilComponent;
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
public class NxtNewsListController {

    @Resource
    NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    NxtApiNormalNewsListController nxtApiNormalNewsListController;

    @RequestMapping("/news")
    public ModelAndView index(Device device, ModelAndView model,
                              @RequestParam(value = "category_id",required = false) Long categoryId,
                              @RequestParam(value = "page",required = false) Integer page
                              ) throws InterruptedException {

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
            model.setViewName("seo/news_list");

            final Long cid = categoryId;
            model.addObject("categoryId", categoryId);
            if (page == null || page < 1){
                page = 1;
            }
            model.addObject("page", page);
            int limit = 10;
            int offset = limit * page - limit;

            ArrayList<Thread> threads = new ArrayList<>();

            //资讯大列表
            Map<String, Object> apiResult = nxtApiNormalNewsListController.exec(cid,limit,offset,1);
            List<Map<String,Object>> newsList = (List<Map<String,Object>>)apiResult.get("data");
            model.addObject("newsList",newsList);
            model.addObject("pages",apiResult.get("pages"));

            //全部推荐资讯
            Map<String, Object> apiResult1 = nxtApiNormalNewsListController.exec(null,3,null,1);
            List<Map<String,Object>> newsList1 = (List<Map<String,Object>>)apiResult1.get("data");
            model.addObject("newsList_side_1",newsList1);

            //推荐资讯1
            Map<String, Object> apiResult2 = nxtApiNormalNewsListController.exec(1L,3,null,1);
            List<Map<String,Object>> newsList2 = (List<Map<String,Object>>)apiResult2.get("data");
            model.addObject("newsList_side_2",newsList2);

            //推荐资讯2
            Map<String, Object> apiResult3 = nxtApiNormalNewsListController.exec(2L,3,null,1);
            List<Map<String,Object>> newsList3 = (List<Map<String,Object>>)apiResult3.get("data");
            model.addObject("newsList_side_3",newsList3);

        }

        return model;

    }

}
