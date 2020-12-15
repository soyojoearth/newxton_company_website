package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserVerify;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.service.NxtUserVerifyService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserEmailUpdateController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @RequestMapping(value = "/api/user/email/update", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        String email = jsonParam.getString("email");
        Long verifyCode = jsonParam.getLong("verifyCode");

        if (email == null || email.isEmpty()){
            return new NxtStructApiResult(54,"请提供email");
        }

        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        //旧邮箱检查
        if (user.getEmail() != null && !user.getEmail().isEmpty()){
            return new NxtStructApiResult(53,"原绑定邮箱需要先解绑");
        }

        //新邮箱验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(email);
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"新邮箱没有发送验证码");
        }
        if (!(
                nxtUserVerify.getStatus().equals(0) &&
                nxtUserVerify.getType().equals(1) && //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证
                nxtUserVerify.getCode().equals(verifyCode) &&
                nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis()
        )){
            return new NxtStructApiResult(53,"新邮箱验证码无效");
        }

        //查询该邮箱有没有被其它用户绑定
        NxtUser userRepeat = nxtUserService.queryByEmail(email);
        if (userRepeat != null){
            return new NxtStructApiResult(53,"新邮箱已有用户名绑定："+user.getUsername());
        }

        //验证完成，修改邮箱
        user.setEmail(email);
        nxtUserService.update(user);

        //验证码标记已使用
        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        return new NxtStructApiResult();

    }

}
