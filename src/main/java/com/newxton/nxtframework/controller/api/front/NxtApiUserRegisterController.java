package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hexiao
 * @description 用户注册接口
 * @date 2020/11/17 15:06
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserRegisterController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");
        String inviterCode = jsonParam.getString("inviterCode");
        String phone = jsonParam.getString("phone");
        String email = jsonParam.getString("email");

        if (StringUtils.isBlank(username)) {
            return new NxtStructApiResult(52,"请输入用户名");
        }
        if (StringUtils.isBlank(password)) {
            return new NxtStructApiResult(52,"请输入密码");
        }
        //暂时不需要手机号、邮箱
        /**
        String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱校验
        String regPhone = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";//手机号校验
        if (username.indexOf("@") != -1) {  //邮箱
            Pattern pattern = Pattern.compile(regEmail);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                return new NxtStructApiResult(52,"请输入正确的邮箱");
            }
            email = username;
        } else { //手机号
            Pattern pattern = Pattern.compile(regPhone);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                return new NxtStructApiResult(52,"请输入正确的手机号");
            }
            phone = username;
        }
         **/
        //查询该用户是否已注册
        NxtUser nxtUser = nxtUserService.queryByUsername(username);
        if (nxtUser != null) {
            return new NxtStructApiResult(52,"此账号已经注册");
        }
        NxtUser newNxtUser = new NxtUser();
        if (StringUtils.isNotBlank(inviterCode)) {
            nxtUser = nxtUserService.queryByInviteCode(inviterCode);
            if (nxtUser != null) {
                //关联上家
                newNxtUser.setInviterUserId(nxtUser.getId());
                Long inviteesCount = nxtUser.getInviteesCount() == null ? 0 : nxtUser.getInviteesCount();
                nxtUser.setInviteesCount(inviteesCount + 1);
                //更新下家数量
                nxtUserService.update(nxtUser);
            }
        }
        //创建salt和密码
        String saltNew = nxtUtilComponent.getRandomString(32);
        String pwdSaltNew = password + saltNew;
        password = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        //token
        String newToken = nxtUtilComponent.getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        newNxtUser.setUsername(username);
        newNxtUser.setPassword(password);
        newNxtUser.setSalt(saltNew);
        newNxtUser.setToken(newToken);
        newNxtUser.setPhone(phone);
        newNxtUser.setEmail(email);
        newNxtUser.setStatus(0);
        newNxtUser.setIsAdmin(0);
        newNxtUser.setLevelNum(1);//普通会员
        newNxtUser.setDatelineCreate(System.currentTimeMillis());
        nxtUserService.insert(newNxtUser);

        return new NxtStructApiResult();

    }

}
