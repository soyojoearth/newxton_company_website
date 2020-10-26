package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtWebUtilComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtAdminController {

    @Resource
    NxtWebUtilComponent nxtWebUtilComponent;

    /**
     * 管理后台技术采用前后端分离，前端使用Vue，后端调用/api/接口
     * @param model
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView index(ModelAndView model) {

        if (nxtWebUtilComponent.isMSBrowser()){
            //后台不支持微软的浏览器：IE和Edge
            model.setViewName("admin_no_ie");
        }
        else {
            //这里直接加载前端工程师的打包页面即可，没其他啥事
            model.setViewName("admin");
        }

        return model;

    }

}
