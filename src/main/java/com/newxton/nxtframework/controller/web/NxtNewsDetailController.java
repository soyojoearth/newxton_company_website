package com.newxton.nxtframework.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NxtNewsDetailController extends NxtBaseWebController {

    @RequestMapping(value = "/news/detail" )
    public ModelAndView index(ModelAndView model, @RequestParam("id") Long id) throws InterruptedException {

        model.addObject("title", "资讯列表");

        model.setViewName("news_detail");

        Map<String,Object> detail = new HashMap<>();

        //资讯详情
        Thread thread1 = new Thread(new Runnable()  {
            @Override
            public void run()  {

                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("id",id);
                    Map<String,Object> data = requestApiFromPostReturnMapData("/api/news/detail",params,null);
                    model.addObject("detail",data.get("data"));
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        thread1.start();
        thread1.join();

        detail = (Map<String,Object>)model.getModel().get("detail");

        Long categoryId = Long.valueOf(detail.get("categoryId").toString());

        //相关资讯
        Thread thread2 = new Thread(new Runnable()  {
            @Override
            public void run()  {

                try {
                    Map<String,Object> paramsNewsList1 = new HashMap<>();
                    paramsNewsList1.put("root_category_id",categoryId);
                    paramsNewsList1.put("limit",3);
                    Map<String,Object> paramsNewsList1_JSON = requestApiFromPostReturnMapData("/api/normal_news_list",paramsNewsList1,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)paramsNewsList1_JSON.get("data");
                    model.addObject("newsList_bottom",newsList);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });


        //最新资讯
        Thread thread3 = new Thread(new Runnable()  {
            @Override
            public void run()  {

                try {
                    Map<String,Object> paramsNewsList1 = new HashMap<>();
                    paramsNewsList1.put("limit",6);
                    Map<String,Object> paramsNewsList1_JSON = requestApiFromPostReturnMapData("/api/normal_news_list",paramsNewsList1,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)paramsNewsList1_JSON.get("data");
                    model.addObject("newsList_right",newsList);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });


        thread2.start();
        thread3.start();

        thread2.join();
        thread3.join();


        return model;

    }

}
