package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserVerify;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessWithdraw;
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
public class NxtApiUserWithdrawCreateController {

    @Resource
    private NxtUserVerifyService nxtUserVerifyService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessWithdraw nxtProcessWithdraw;

    @RequestMapping(value = "/api/user/withdraw/create", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        Float amount = jsonParam.getFloat("amount");
        Long verifyCode = jsonParam.getLong("verifyCode");
        Integer verifyCodeType = jsonParam.getInteger("verifyCodeType");

        String platform = jsonParam.getString("platform");
        String platformAccount = jsonParam.getString("platformAccount");
        String platformPerson = jsonParam.getString("platformPerson");

        if (amount == null){
            return new NxtStructApiResult(54,"请提供金额");
        }
        if (verifyCode == null){
            return new NxtStructApiResult(54,"请提供验证码");
        }
        if (verifyCodeType == null){
            return new NxtStructApiResult(54,"请选择验证类型：手机、邮箱");
        }
        if (platformAccount == null || platformAccount.isEmpty()){
            return new NxtStructApiResult(54,"请填写提现账号");
        }
        if (platformPerson == null || platformPerson.isEmpty()){
            return new NxtStructApiResult(54,"请填写提现账号姓名");
        }

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        String phoneOrEmail = null;

        if (verifyCodeType.equals(1)){
            //邮箱
            if (user.getEmail() == null || user.getEmail().isEmpty()){
                return new NxtStructApiResult(54,"尚未绑定邮箱，请先绑定邮箱");
            }
            phoneOrEmail = user.getEmail();
        }
        else if (verifyCodeType.equals(2)){
            //手机
            if (user.getPhone() == null || user.getPhone().isEmpty()){
                return new NxtStructApiResult(54,"尚未绑定手机，请先绑定手机");
            }
            phoneOrEmail = user.getPhone();
        }
        else {
            return new NxtStructApiResult(54,"请选择正确的验证类型：手机、邮箱");
        }

        //查询验证码
        NxtUserVerify nxtUserVerify = nxtUserVerifyService.queryLastByPhoneOrEmail(phoneOrEmail);
        if (nxtUserVerify == null){
            return new NxtStructApiResult(54,"没有发送验证码的记录");
        }

        //验证
        if (!(
                nxtUserVerify.getStatus().equals(0) && nxtUserVerify.getType().equals(3) &&
                nxtUserVerify.getDateline() + 1800000 > System.currentTimeMillis() &&
                nxtUserVerify.getCode().equals(verifyCode)
        )){
            //验证不通过
            return new NxtStructApiResult(34,"验证码无效");
        }

        nxtUserVerify.setStatus(1);
        nxtUserVerifyService.update(nxtUserVerify);

        //创建提现申请
        try {
            nxtProcessWithdraw.create(userId,(long)(amount*100),platform,platformAccount,platformPerson);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

        return new NxtStructApiResult();

    }

}
