package com.newxton.companywebsite.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@Controller
public class NxtNewsDetailController {

    @RequestMapping(value = "/news/detail" )
    public ModelAndView index(ModelAndView model, @RequestParam("id") Long id) {

        model.addObject("title", "资讯列表-深圳牛小顿科技有限公司");

        model.setViewName("news_detail");


        return model;

    }

}
