package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class NxtApiAdminLoginController {

    @Resource
    private NxtUserService nxtUserService;


    @RequestMapping(value = "/api/admin/login", method = RequestMethod.POST)
    public Map<String,Object> index(@RequestParam(value="username", required=false) String username,
                                    @RequestParam(value="password", required=false) String password
                                    ) {

        Map<String,Object> result = new HashMap<>();
        result.put("status",0);
        result.put("message","");


        if (username == null || password == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        username = username.trim();

        NxtUser user = nxtUserService.queryByUsername(username);
        if (user == null){
            result.put("status",44);
            result.put("message","用户不存在");
            return result;
        }

        String salt = user.getSalt();
        password = DigestUtils.md5Hex(password+salt).toLowerCase();

        if (user.getPassword() == null || !user.getPassword().equals(password)){
            result.put("status",42);
            result.put("message","密码错误");
            return result;
        }

        String newToken = getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        //更新token
        user.setToken(newToken);
        nxtUserService.update(user);

        result.put("token",newToken);

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
