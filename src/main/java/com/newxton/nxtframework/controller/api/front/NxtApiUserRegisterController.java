package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hexiao
 * @description 用户注册接口
 * @date 2020/11/17 15:06
 */
@RestController
public class NxtApiUserRegisterController {

    @Resource
    private NxtUserService nxtUserService;


    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "password", required = false) String password,
                                    @RequestParam(value = "inviterCode", required = false) String inviterCode,
                                    @RequestParam(value = "phone", required = false) String phone,
                                    @RequestParam(value = "email", required = false) String email) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (StringUtils.isBlank(username)) {
            result.put("status", 52);
            result.put("message", "请输入手机号或邮箱");
            return result;
        }
        if (StringUtils.isBlank(password)) {
            result.put("status", 52);
            result.put("message", "请输入密码");
            return result;
        }
        String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱校验
        String regPhone = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";//手机号校验
        if (username.indexOf("@") != -1) {  //邮箱
            Pattern pattern = Pattern.compile(regEmail);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                result.put("status", 52);
                result.put("message", "请输入正确的邮箱");
                return result;
            }
            email = username;
        } else { //手机号
            Pattern pattern = Pattern.compile(regPhone);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                result.put("status", 52);
                result.put("message", "请输入正确的手机号");
                return result;
            }
            phone = username;
        }
        //查询该用户是否已注册
        NxtUser nxtUser = nxtUserService.queryByUsername(username);
        if (nxtUser != null) {
            result.put("status", 45);
            result.put("message", "此账号已经注册");
            return result;
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
        String saltNew = getRandomString(32);
        String pwdSaltNew = password + saltNew;
        password = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        newNxtUser.setUsername(username);
        newNxtUser.setPassword(password);
        newNxtUser.setSalt(saltNew);
        newNxtUser.setPhone(phone);
        newNxtUser.setEmail(email);
        newNxtUser.setStatus(0);
        nxtUserService.insert(newNxtUser);

        return result;
    }

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_";
        Random random = new Random();
        StringBuffer buffet = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length() - 1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }
}
