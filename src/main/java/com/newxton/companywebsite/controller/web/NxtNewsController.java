package com.newxton.companywebsite.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
public class NxtNewsController {

    @RequestMapping("/news")
    public ModelAndView index(ModelAndView model) {

        String newsTitle = "资讯标题";

        model.addObject("title", newsTitle+"-深圳牛小顿科技有限公司");

        model.setViewName("news");


        return model;

    }

}
