package com.newxton.companywebsite.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@Controller
public class NxtAdminController {

    /**
     * 管理后台技术采用前后端分离，前端使用Vue，后端调用/api/接口
     * @param model
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView index(ModelAndView model) {

        //这里直接加载前端工程师的打包页面即可，没其他啥事
        //model.setViewName("admin");

        model.setViewName("admin");

        return model;

    }

}
