package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtRequestSelfApiComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class NxtContactController {

    @Resource
    NxtRequestSelfApiComponent nxtRequestSelfApiComponent;

    @RequestMapping("/contact")
    public ModelAndView index(ModelAndView model) throws  InterruptedException {

        model.setViewName("contact");

        Thread thread1 = new Thread(new Runnable()  {
            @Override
            public void run()  {

                try {
                    Map<String,Object> params = new HashMap<>();
                    params.put("id",1L);
                    Map<String,Object> data_JSON = nxtRequestSelfApiComponent.postFormAndReturnMap("/api/web_content/detail",params,null);
                    model.addObject("detail",data_JSON.get("data"));
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
