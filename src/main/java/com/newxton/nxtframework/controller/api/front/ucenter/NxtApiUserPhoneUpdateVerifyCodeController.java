package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessVerifyCode;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserPhoneUpdateVerifyCodeController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessVerifyCode nxtProcessVerifyCode;

    @RequestMapping(value = "/api/user/phone/update/verify_code", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        NxtUser user = nxtUserService.queryById(userId);

        //旧手机号检查
        if (user.getPhone() != null && !user.getPhone().isEmpty()){
            return new NxtStructApiResult(53,"原绑定手机号需先解除绑定");
        }

        String phone = jsonParam.getString("phone");

        if (phone == null || phone.isEmpty()){
            return new NxtStructApiResult(54,"请提供手机号");
        }

        //检查手机格式
        String regPhone = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";//手机号校验
        Pattern pattern = Pattern.compile(regPhone);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            return new NxtStructApiResult(54,"请输入正确的手机号");
        }

        //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证

        try {
            //发送新手机号验证码
            Long code = nxtProcessVerifyCode.createAndSendPhoneOrEmailVerifyCode(phone,1);
            return new NxtStructApiResult(53,"验证码【"+code+"】。本开源系统不提供验证码发送功能，请自行开发或联系我们进行二次开发。");
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
