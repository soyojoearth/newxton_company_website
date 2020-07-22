package com.newxton.companywebsite.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
public class NxtIndexController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView model) {

        //这里判断，如果是手机访问，直接加载vue
        boolean isMobile = false;
        if (isMobile){
            /**
             * H5移动端技术采用前后端分离，前端使用Vue，后端调用/api/接口
             * 这里直接加载前端工程师编译打包的h5.html页面即可，没其他啥事
             */
            model.setViewName("h5");
            return model;
        }

        model.setViewName("index");
        model.addObject("title", "首页-深圳牛小顿科技有限公司");

        return model;

    }

}
