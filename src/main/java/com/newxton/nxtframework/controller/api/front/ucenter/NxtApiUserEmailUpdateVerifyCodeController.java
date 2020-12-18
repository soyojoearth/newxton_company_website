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
public class NxtApiUserEmailUpdateVerifyCodeController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessVerifyCode nxtProcessVerifyCode;

    @RequestMapping(value = "/api/user/email/update/verify_code", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = false) Long userId, @RequestBody JSONObject jsonParam) {

        NxtUser user = nxtUserService.queryById(userId);

        //旧邮箱检查
        if (user.getEmail() != null && !user.getEmail().isEmpty()){
            return new NxtStructApiResult(53,"原绑定邮箱需要先解绑");
        }

        String email = jsonParam.getString("email");

        if (email == null || email.isEmpty()){
            return new NxtStructApiResult(54,"请提供email");
        }

        //检查email格式
        String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱校验
        Pattern pattern = Pattern.compile(regEmail);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return new NxtStructApiResult(54,"请输入正确的email");
        }

        //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证

        try {
            //发送新email验证码
            Long code = nxtProcessVerifyCode.createAndSendPhoneOrEmailVerifyCode(email,1);
            return new NxtStructApiResult(53,"验证码已存入数据库表【nxt_user_verify】，请到数据库查看。本系统暂不免费提供邮件发送功能，请自行开发或联系我们进行二次开发。");
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
