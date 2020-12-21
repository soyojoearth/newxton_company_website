package com.newxton.nxtframework.controller.api.front.ucenter;

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
 * 提现验证码
 */
@RestController
public class NxtApiUserWithdrawVerifyCodeController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessVerifyCode nxtProcessVerifyCode;

    @RequestMapping(value = "/api/user/withdraw/verify_code", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        NxtUser user = nxtUserService.queryById(userId);

        Integer verifyCodeType = jsonParam.getInteger("verifyCodeType");

        if (verifyCodeType == null){
            return new NxtStructApiResult(54,"请提供email");
        }

        //-1：修改绑定 1：绑定账户 2：找回密码 3：提现验证
        try {
            Long code = 0L;
            //发送新email验证码
            if (verifyCodeType.equals(1)) {//邮箱
                if (user.getEmail() == null || user.getEmail().isEmpty()){
                    return new NxtStructApiResult(54,"尚未绑定邮箱");
                }
                code = nxtProcessVerifyCode.createAndSendPhoneOrEmailVerifyCode(user.getEmail(), 3);
            }
            else if (verifyCodeType.equals(2)){//手机
                if (user.getPhone() == null || user.getPhone().isEmpty()){
                    return new NxtStructApiResult(54,"尚未绑定手机");
                }
                code = nxtProcessVerifyCode.createAndSendPhoneOrEmailVerifyCode(user.getPhone(), 3);
            }
            else {
                return new NxtStructApiResult(54,"请选择正确的验证方式");
            }

            return new NxtStructApiResult(53,"验证码【"+code+"】。本开源系统不提供验证码发送功能，请自行开发或联系我们进行二次开发。");
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
