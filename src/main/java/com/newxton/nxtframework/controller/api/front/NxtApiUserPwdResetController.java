package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserVerify;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.service.NxtUserVerifyService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 重置密码
 */
@RestController
public class NxtApiUserPwdResetController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping(value = "/api/user/pwd_reset", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestBody JSONObject jsonParam) {

        String phoneOrEmail = jsonParam.getString("phoneOrEmail");
        Long verifyCode = jsonParam.getLong("verifyCode");
        String password = jsonParam.getString("password");

        if (phoneOrEmail == null || phoneOrEmail.isEmpty()){
            return new NxtStructApiResult(54,"请提供手机号或邮箱");
        }
        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }
        if (password == null || password.isEmpty()){
            return new NxtStructApiResult(54,"请填写密码");
        }
        if (password.length() < 6){
            return new NxtStructApiResult(54,"密码至少6位");
        }

        //查询该用户
        NxtUser user;
        if (phoneOrEmail.contains("@")) {
            user = nxtUserService.queryByEmail(phoneOrEmail);
        }
        else {
            user = nxtUserService.queryByPhone(phoneOrEmail);
        }
        if (user == null){
            return new NxtStructApiResult(54,"该用户不存在");
        }

        //对比验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(phoneOrEmail);
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"没有发送验证码");
        }
        if (!(
                nxtUserVerify.getStatus().equals(0) &&
                        nxtUserVerify.getType().equals(2) && //-1：修改绑定 1：绑定账户 2：找回密码 3：提现验证
                        nxtUserVerify.getCode().equals(verifyCode) &&
                        nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis()
        )){
            return new NxtStructApiResult(53,"验证码无效");
        }

        if (user.getStatus().equals(-1)) {
            return new NxtStructApiResult(54,"该用户已被加入黑名单");
        }

        if (user.getIsAdmin() != null && user.getIsAdmin().equals(1)){
            return new NxtStructApiResult(54,"管理员用户不可使用找回密码功能，请超级管理员修改");
        }

        //验证成功，修改密码
        //创建salt和密码
        String saltNew = nxtUtilComponent.getRandomString(32);
        String pwdSaltNew = password + saltNew;
        password = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        //token
        String newToken = nxtUtilComponent.getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        user.setPassword(password);
        user.setSalt(saltNew);
        user.setToken(newToken);

        nxtUserService.update(user);

        //验证码标记已使用
        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        return new NxtStructApiResult();

    }

}
