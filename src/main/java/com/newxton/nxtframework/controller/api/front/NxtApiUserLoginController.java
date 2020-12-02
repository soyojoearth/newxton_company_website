package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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
    public NxtStructApiResult index(@RequestBody JSONObject jsonParam) {

        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");

        if (username == null || password == null) {
            return new NxtStructApiResult(52,"参数错误");
        }

        username = username.trim();

        NxtUser user = nxtUserService.queryByUsername(username);
        if (user == null) {
            return new NxtStructApiResult(42,"用户不存在");
        }

        String salt = user.getSalt();
        String pwdSalt = password + salt;
        password = DigestUtils.md5Hex(pwdSalt).toLowerCase();

        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            return new NxtStructApiResult(42,"密码错误");
        }

        if (user.getStatus().equals(-1)) {
            return new NxtStructApiResult(-1,"禁止登录");
        }

        Map<String,Object> detail = new HashMap<>();
        if (multiDeviceLogin) {
            //允许多设备登录
            if (user.getToken() == null){
                //更新token
                String newToken = nxtUtilComponent.getRandomString(32);
                newToken = DigestUtils.md5Hex(newToken).toLowerCase();
                user.setToken(newToken);
                nxtUserService.update(user);
            }
            detail.put("token", user.getToken());
            detail.put("user_id", user.getId());
        }
        else{
            //不允许多设备登录

            String newToken = nxtUtilComponent.getRandomString(32);
            newToken = DigestUtils.md5Hex(newToken).toLowerCase();

            //更新token
            user.setToken(newToken);
            nxtUserService.update(user);

            detail.put("token", newToken);
            detail.put("user_id", user.getId());
        }

        return new NxtStructApiResult(detail);

    }

}
