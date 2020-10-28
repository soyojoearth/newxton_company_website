package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtHTMLUtilComponent;
import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.controller.api.front.NxtApiBannerListController;
import com.newxton.nxtframework.controller.api.front.NxtApiNormalNewsListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class NxtIndexController {

    private Logger logger = LoggerFactory.getLogger(NxtIndexController.class);

    @Resource
    NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    NxtHTMLUtilComponent nxtHTMLUtilComponent;

    @Resource
    NxtApiBannerListController nxtApiBannerListController;

    @Resource
    NxtApiNormalNewsListController nxtApiNormalNewsListController;

    @RequestMapping("/")
    public ModelAndView index(Device device,ModelAndView model) {

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
                logger.info("移动端访客");
            } else {
                model.setViewName("pc/index");
                logger.info("PC端访客");
            }
        }
        else {

            /**
             * 为了前后端分离的同时也能照顾到搜索引擎收录，特此解决
             * 方法一：可以自动模拟浏览器解析PC版ajax SPA页面变成传统html再丢给搜索引擎
             * 方法二：SEO优化&搜索引擎特供版渲染（后端工程师写一个不含CSS样式的模版）
             * 使用方法一，一般都可以应付；如果特殊情况测试有问题，再用方法二；
             */

            /*方法一：自动解析PC版 ajax SPA页面后丢给搜索引擎*/
            /*
            自己抓取自己的PC版页面，别解析其中的ajax
            这里需阻塞一段时间执行其中的异步js，感觉2秒应该可以了
            太短了执行不完，太长了会让搜索引擎等太久，根据不同的页面的ajax量，自己判断时间吧
            */
            String html = nxtHTMLUtilComponent.parseAjaxHtmlWithSelfUrl(2000);
            model.addObject("html",html);
            model.setViewName("seo/auto");

            logger.info("被搜索引擎光顾了一下");

            /*方法二：SEO优化&搜索引擎特供版渲染*/
            /**
            model.setViewName("seo/index");

            //资讯1
            Map<String, Object> apiResult1 = nxtApiNormalNewsListController.exec(1L,4,null,null);
            List<Map<String, Object>> newsList1 = (List<Map<String, Object>>) apiResult1.get("data");
            model.addObject("newsList1", newsList1);

            //资讯2
            Map<String, Object> apiResult2 = nxtApiNormalNewsListController.exec(2L,4,null,null);
            List<Map<String, Object>> newsList2 = (List<Map<String, Object>>) apiResult2.get("data");
            model.addObject("newsList2", newsList2);
            **/
        }

        return model;

    }

}
