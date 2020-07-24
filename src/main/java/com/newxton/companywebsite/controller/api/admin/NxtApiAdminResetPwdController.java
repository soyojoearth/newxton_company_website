package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtUserService;
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
public class NxtApiAdminResetPwdController {

    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/reset_pwd", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestHeader("user_id") Long user_id,
                                     @RequestParam(value="password_old", required=false) String passwordOld,
                                     @RequestParam(value="password_new", required=false) String passwordNew
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtUser user = nxtUserService.queryById(user_id);

        String salt = user.getSalt();
        String pwdSalt = passwordOld+salt;
        passwordOld = DigestUtils.md5Hex(pwdSalt).toLowerCase();

        if (user.getPassword() == null || !user.getPassword().equals(passwordOld)){
            result.put("status",42);
            result.put("message","密码错误");
            return result;
        }

        //和salt一起改
        String saltNew = getRandomString(32);
        String pwdSaltNew = passwordNew+saltNew;
        passwordNew = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        user.setSalt(saltNew);
        user.setPassword(passwordNew);

        //更新
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
