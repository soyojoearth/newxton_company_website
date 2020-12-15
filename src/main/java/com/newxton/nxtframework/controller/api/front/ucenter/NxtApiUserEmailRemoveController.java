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
public class NxtApiUserEmailRemoveController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @RequestMapping(value = "/api/user/email/remove", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        if (user.getEmail() == null || user.getEmail().isEmpty()){
            return new NxtStructApiResult(53,"您未绑定邮箱");
        }

        Long verifyCode = jsonParam.getLong("verifyCode");

        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }

        //邮箱验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(user.getEmail());
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"邮箱没有发送验证码");
        }
        if (!(
                nxtUserVerify.getStatus().equals(0) &&
                nxtUserVerify.getType().equals(-1) && //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证
                nxtUserVerify.getCode().equals(verifyCode) &&
                nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis()
        )){
            return new NxtStructApiResult(53,"邮箱验证码无效");
        }

        //验证完成，解绑邮箱
        nxtUserService.removeEmailById(user.getId());

        //验证码标记已使用
        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        return new NxtStructApiResult();

    }

}
