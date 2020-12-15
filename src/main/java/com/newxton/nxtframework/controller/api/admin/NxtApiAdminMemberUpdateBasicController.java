package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberUpdateBasicController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping(value = "/api/admin/member/update_basic", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long userId = jsonParam.getLong("userId");
        Integer levelNum = jsonParam.getInteger("levelNum");
        String phone = jsonParam.getString("phone");
        String email = jsonParam.getString("email");

        String newPwd = jsonParam.getString("newPwd");
        Integer gender = jsonParam.getInteger("gender");
        Boolean isBlock = jsonParam.getBoolean("isBlock");

        NxtUser nxtUser = nxtUserService.queryById(userId);

        if (nxtUser == null){
            return new NxtStructApiResult(53,"找不到该会员").toMap();
        }

        Boolean needUpdate = false;

        //更新会员等级
        if (levelNum != null){
            NxtUserLevel nxtUserLevel = nxtUserLevelService.queryByNum(levelNum);
            if (nxtUserLevel == null){
                return new NxtStructApiResult(53,"找不到该会员等级").toMap();
            }
            else {
                nxtUser.setLevelNum(nxtUserLevel.getNum());
                needUpdate = true;
            }
        }

        //更新手机号
        if (phone != null && !phone.trim().isEmpty()){
            String regPhone = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";//手机号校验
            Pattern pattern = Pattern.compile(regPhone);
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.matches()) {
                return new NxtStructApiResult(52,"请输入正确的手机号").toMap();
            }
            nxtUser.setPhone(phone);
            needUpdate = true;
        }

        //更新邮箱
        if (email != null && !email.trim().isEmpty()){
            String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱校验
            Pattern pattern = Pattern.compile(regEmail);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                return new NxtStructApiResult(52,"请输入正确的邮箱").toMap();
            }
            nxtUser.setEmail(email);
            needUpdate = true;
        }

        //更新密码
        if (newPwd != null && !newPwd.trim().isEmpty()){
            //和salt一起改
            String saltNew = nxtUtilComponent.getRandomString(32);
            String pwdSaltNew = newPwd+saltNew;
            newPwd = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();
            nxtUser.setSalt(saltNew);
            nxtUser.setPassword(newPwd);
            needUpdate = true;
        }

        //更改性别
        if (gender != null){
            Set<Integer> genderSet = Stream.of(0, 1, 2).collect(toSet());
            if (!genderSet.contains(gender)) {
                return new NxtStructApiResult(53, "性别参数仅接收：0、1、2").toMap();
            }
            nxtUser.setGender(gender);
            needUpdate = true;
        }

        //拉黑与解放
        if (isBlock != null){
            nxtUser.setStatus(isBlock ? -1 : 0);
            needUpdate = true;
        }

        if (needUpdate){
            nxtUserService.update(nxtUser);
        }

        return new NxtStructApiResult().toMap();

    }
}
