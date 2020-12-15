package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserPwdUpdateController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping(value = "/api/user/pwd/update", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        String passwordOld = jsonParam.getString("passwordOld");
        String passwordNew = jsonParam.getString("passwordNew");

        if (passwordOld == null || passwordOld.isEmpty()){
            return new NxtStructApiResult(54,"请提供原密码");
        }

        if (passwordNew == null || passwordNew.isEmpty()){
            return new NxtStructApiResult(54,"请提供新密码");
        }

        NxtUser user = nxtUserService.queryById(userId);

        String salt = user.getSalt();
        String pwdSalt = passwordOld + salt;
        passwordOld = DigestUtils.md5Hex(pwdSalt).toLowerCase();

        if (user.getPassword() == null || !user.getPassword().equals(passwordOld)) {
            return new NxtStructApiResult(42, "密码错误");
        }

        //和salt一起改
        String saltNew = nxtUtilComponent.getRandomString(32);
        String pwdSaltNew = passwordNew + saltNew;
        passwordNew = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        user.setSalt(saltNew);
        user.setPassword(passwordNew);

        //更新
        nxtUserService.update(user);

        return new NxtStructApiResult();

    }

}
