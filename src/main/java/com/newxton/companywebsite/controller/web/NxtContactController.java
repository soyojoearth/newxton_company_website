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
public class NxtContactController {

    @RequestMapping("/contact")
    public ModelAndView index(ModelAndView model) {

        model.setViewName("contact");
        model.addObject("title", "联系我们-深圳牛小顿科技有限公司");

        return model;

    }

}
