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
 */
@RestController
public class NxtApiUserPhoneRemoveController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @RequestMapping(value = "/api/user/phone/remove", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        if (user.getPhone() == null || user.getPhone().isEmpty()){
            return new NxtStructApiResult(53,"您未绑定手机号");
        }

        Long verifyCode = jsonParam.getLong("verifyCode");

        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }

        //手机号验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(user.getPhone());
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"手机号没有发送验证码");
        }
        if (!(
                nxtUserVerify.getStatus().equals(0) &&
                nxtUserVerify.getType().equals(-1) && //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证
                nxtUserVerify.getCode().equals(verifyCode) &&
                nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis()
        )){
            return new NxtStructApiResult(53,"手机号验证码无效");
        }

        //验证完成，解绑手机号
        nxtUserService.removePhoneById(user.getId());

        //验证码标记已使用
        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        return new NxtStructApiResult();

    }

}
