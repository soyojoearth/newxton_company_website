package com.newxton.nxtframework.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/14
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtJoinUsController extends NxtBaseWebController {

    @RequestMapping("/join_us")
    public ModelAndView index(ModelAndView model) throws  InterruptedException {

        model.setViewName("join_us");


        Thread thread1 = new Thread(new Runnable()  {
            @Override
            public void run()  {

                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("id",3L);
                    Map<String,Object> webContentData = requestApiFromPostReturnMapData("/api/web_content/detail",params,null);
                    model.addObject("detail",webContentData.get("data"));
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        thread1.start();

        thread1.join();

        return model;

    }

}
