package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtUser;
import com.newxton.supercompanyapi.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminLogoutController {

    @Resource
    private NxtUserService nxtUserService;


    @RequestMapping(value = "/api/admin/logout", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestHeader("user_id") Long user_id, @RequestHeader("token") String token) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtUser user = nxtUserService.queryById(user_id);

        if (user == null){
            //未登录状态，直接提示注销成功
            result.put("status", 0);
            result.put("message", "");
            return result;
        }

        if (!user.getToken().equals(token)){
            //未登录状态，直接提示注销成功
            result.put("status", 0);
            result.put("message", "");
            return result;
        }

        //已登录状态，注销时有权重置token（让旧token失效）
        String newToken = getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        //更新token
        user.setToken(newToken);
        nxtUserService.update(user);

        return result;

    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_";
        Random random=new Random();
        StringBuffer buffet=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length()-1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }

}