package com.newxton.nxtframework.controller.web;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtInviteController {
    @RequestMapping("/invite")
    public String index(Device device, ModelAndView model , @RequestParam("id") Long inviterCode) {

        if (device.isMobile()){
            //H5版本有了，再换掉这里的url
            return "redirect:/ucenter/#/sign?inviter_code="+inviterCode;
        }
        else {
            model.setViewName("pc/index");
            return "redirect:/ucenter/#/sign?inviter_code="+inviterCode;
        }

    }

}
