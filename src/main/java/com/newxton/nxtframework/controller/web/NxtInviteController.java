package com.newxton.nxtframework.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtInviteController {
    @RequestMapping("/invite")
    public String index(@RequestParam("id") Long inviterCode) {

        return "redirect:/ucenter/#/sign?inviter_code="+inviterCode;

    }

}
