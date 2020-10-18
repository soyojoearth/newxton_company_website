package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtRequestSelfApiComponent;
import com.newxton.nxtframework.entity.NxtBanner;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NxtIndexController {

    @Resource
    NxtRequestSelfApiComponent nxtRequestSelfApiComponent;

    @RequestMapping("/")
    public ModelAndView index(Device device, ModelAndView model) throws IOException, InterruptedException {

        //这里判断，如果是手机访问，直接加载vue
        boolean isMobile = device.isMobile();
//        isMobile = true;
        if (isMobile) {

            model.setViewName("h5/index.html");

            return model;

        } else {

            model.setViewName("index");


            //轮播横幅
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Map<String, Object> paramsBannerList = new HashMap<>();
                        paramsBannerList.put("location_name", "首页");
                        Map<String, Object> bannerList_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/banner_list", paramsBannerList, null);
                        List<NxtBanner> bannerList = (List<NxtBanner>) bannerList_JSON.get("data");
                        model.addObject("bannerList", bannerList);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });


            //资讯1
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Map<String, Object> paramsNewsList1 = new HashMap<>();
                        paramsNewsList1.put("root_category_id", 1L);
                        paramsNewsList1.put("limit", 4);
                        Map<String, Object> paramsNewsList1_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list", paramsNewsList1, null);
                        List<Map<String, Object>> newsList1 = (List<Map<String, Object>>) paramsNewsList1_JSON.get("data");
                        model.addObject("newsList1", newsList1);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });


            //资讯2
            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Map<String, Object> paramsNewsList2 = new HashMap<>();
                        paramsNewsList2.put("root_category_id", 2L);
                        paramsNewsList2.put("limit", 4);
                        Map<String, Object> paramsNewsList2_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list", paramsNewsList2, null);
                        List<Map<String, Object>> newsList2 = (List<Map<String, Object>>) paramsNewsList2_JSON.get("data");
                        model.addObject("newsList2", newsList2);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });


            thread1.start();
            thread2.start();
            thread3.start();

            thread1.join();
            thread2.join();
            thread3.join();


            return model;

        }

    }

}
