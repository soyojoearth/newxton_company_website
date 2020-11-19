package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserLoginController {

    @Value("${newxton.config.multi-device-login}")
    private boolean multiDeviceLogin;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;


    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public Map<String,Object> index(@RequestBody JSONObject jsonParam) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");

        if (username == null || password == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        username = username.trim();

        NxtUser user = nxtUserService.queryByUsername(username);
        if (user == null) {
            result.put("status", 44);
            result.put("message", "用户不存在");
            return result;
        }

        String salt = user.getSalt();
        String pwdSalt = password + salt;
        password = DigestUtils.md5Hex(pwdSalt).toLowerCase();

        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            result.put("status", 42);
            result.put("message", "密码错误");
            return result;
        }

        if (user.getStatus().equals(-1)) {
            result.put("status", -1);
            result.put("message", "禁止登录");
            return result;
        }

        if (multiDeviceLogin) {
            //允许多设备登录
            result.put("token", user.getToken());
            result.put("user_id", user.getId());
        }
        else{
            //不允许多设备登录

            String newToken = nxtUtilComponent.getRandomString(32);
            newToken = DigestUtils.md5Hex(newToken).toLowerCase();

            //更新token
            user.setToken(newToken);
            nxtUserService.update(user);

            result.put("token", newToken);
            result.put("user_id", user.getId());
        }

        return result;

    }

}
