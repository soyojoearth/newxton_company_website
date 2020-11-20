package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class NxtApiUserLogoutController {

    @Value("${newxton.config.multi-device-login}")
    private boolean multiDeviceLogin;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping(value = "/api/user/logout", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestHeader("user_id") Long user_id, @RequestHeader("token") String token) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtUser user = nxtUserService.queryById(user_id);

        if (user == null || user.getToken() == null){
            //未登录状态，直接提示注销成功
            return result;
        }

        if (!user.getToken().equals(token)){
            //未登录状态，直接提示注销成功
            return result;
        }

        if (!multiDeviceLogin){

            //不允许多设备登录时

            //已登录状态，注销时有权重置token（让旧token失效）
            String newToken = nxtUtilComponent.getRandomString(32);
            newToken = DigestUtils.md5Hex(newToken).toLowerCase();

            //更新token
            user.setToken(newToken);
            nxtUserService.update(user);

        }

        return result;

    }

}