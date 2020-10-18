package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtRequestSelfApiComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NxtNewsListController {

    @Resource
    NxtRequestSelfApiComponent nxtRequestSelfApiComponent;

    @RequestMapping("/news")
    public ModelAndView index(ModelAndView model,
                              @RequestParam(value = "category_id",required = false) Long categoryId,
                              @RequestParam(value = "page",required = false) Integer page
                              ) throws IOException, InterruptedException {

        model.addObject("title", "新闻资讯");

        model.setViewName("news");


        final Long cid = categoryId;

        model.addObject("categoryId", categoryId);

        if (page == null || page < 1){
            page = 1;
        }

        model.addObject("page", page);

        int limit = 10;
        int offset = limit * page - limit;

        //资讯大列表
        Thread thread0 = new Thread(new Runnable()  {
            @Override
            public void run()  {
                try {
                    Map<String,Object> params = new HashMap<>();
                    if (cid != null){
                        params.put("root_category_id",cid);
                    }
                    params.put("limit",limit);
                    params.put("offset",offset);
                    params.put("show_pages",1);
                    Map<String,Object> data_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list",params,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)data_JSON.get("data");
                    model.addObject("newsList",newsList);
                    model.addObject("pages",data_JSON.get("pages"));
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        //全部推荐资讯
        Thread thread1 = new Thread(new Runnable()  {
            @Override
            public void run()  {
                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("limit",3);
                    Map<String,Object> data_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list",params,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)data_JSON.get("data");
                    model.addObject("newsList_side_1",newsList);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        //推荐1
        Thread thread2 = new Thread(new Runnable()  {
            @Override
            public void run()  {
                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("root_category_id",1L);
                    params.put("limit",3);
                    Map<String,Object> data_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list",params,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)data_JSON.get("data");
                    model.addObject("newsList_side_2",newsList);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        //推荐2
        Thread thread3 = new Thread(new Runnable()  {
            @Override
            public void run()  {
                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("root_category_id",2L);
                    params.put("limit",3);
                    Map<String,Object> data_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/normal_news_list",params,null);
                    List<Map<String,Object>> newsList = (List<Map<String,Object>>)data_JSON.get("data");
                    model.addObject("newsList_side_3",newsList);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();

        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();

        return model;

    }

}
