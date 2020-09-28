package com.newxton.nxtframework.controller.web;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class NxtAboutUsController extends NxtBaseWebController {

    @RequestMapping("/about")
    public ModelAndView index(Device device, ModelAndView model) throws InterruptedException {

        boolean isMobile = device.isMobile();
//        isMobile = true;
        if (isMobile) {

            model.setViewName("h5/about.html");

            return model;

        } else {

            model.setViewName("about");

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        Map<String, Object> json = requestApiFromPostReturnMapData("/api/about_us", null, null);

                        Map<String, Object> data = new HashMap<>();
                        if (json.containsKey("data")) {
                            data = (Map<String, Object>) json.get("data");
                        }

                        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Object> entry = iterator.next();
                            model.addObject(entry.getKey(), entry.getValue());
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });

            thread1.start();

            thread1.join();

            return model;

        }

    }

}
