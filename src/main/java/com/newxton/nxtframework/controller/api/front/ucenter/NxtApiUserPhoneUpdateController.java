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
public class NxtApiUserPhoneUpdateController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @RequestMapping(value = "/api/user/phone/update", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        String phone = jsonParam.getString("phone");
        Long verifyCode = jsonParam.getLong("verifyCode");

        if (phone == null || phone.isEmpty()){
            return new NxtStructApiResult(54,"请提供手机号");
        }

        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        //旧手机号检查
        if (user.getPhone() != null && !user.getPhone().isEmpty()){
            return new NxtStructApiResult(53,"原绑定手机号需先解除绑定");
        }

        //新手机号验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(phone);
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"新手机号没有发送验证码");
        }
        if (!(
                nxtUserVerify.getStatus().equals(0) &&
                nxtUserVerify.getType().equals(1) && //-1：解除绑定 1：绑定账户 2：找回密码 3：提现验证
                nxtUserVerify.getCode().equals(verifyCode) &&
                nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis()
        )){
            return new NxtStructApiResult(53,"新手机号验证码无效");
        }

        //查询该手机号有没有被其它用户绑定
        NxtUser userRepeat = nxtUserService.queryByPhone(phone);
        if (userRepeat != null){
            return new NxtStructApiResult(53,"新手机号已有用户名绑定："+user.getUsername());
        }

        //验证完成，修改手机号
        user.setPhone(phone);
        nxtUserService.update(user);

        //验证码标记已使用
        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        return new NxtStructApiResult();

    }

}
