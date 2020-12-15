package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessVerifyCode;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 获取验证码，自动判断手机或邮箱
 */
@RestController
public class NxtApiUserPwdResetVerifyCodeController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessVerifyCode nxtProcessVerifyCode;

    @RequestMapping(value = "/api/user/pwd_reset/verify_code", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestBody JSONObject jsonParam) {

        String phoneOrEmail = jsonParam.getString("phoneOrEmail");

        if (phoneOrEmail == null || phoneOrEmail.isEmpty()){
            return new NxtStructApiResult(54,"请提供手机或邮箱");
        }

        NxtUser user;
        if (phoneOrEmail.contains("@")) {//邮箱
            user = nxtUserService.queryByEmail(phoneOrEmail);
        }
        else {
            user = nxtUserService.queryByPhone(phoneOrEmail);
        }

        if (user == null){
            return new NxtStructApiResult("该用户不存在");
        }

        //-1：修改绑定 1：绑定账户 2：找回密码 3：提现验证
        try {
            Long code = nxtProcessVerifyCode.createAndSendPhoneOrEmailVerifyCode(phoneOrEmail,2);
            return new NxtStructApiResult("开发调试阶段直接告诉你验证码："+code);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
