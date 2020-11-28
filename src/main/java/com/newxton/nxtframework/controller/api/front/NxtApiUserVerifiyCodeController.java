package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUserVerifiy;
import com.newxton.nxtframework.service.NxtUserVerifiyService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * 获取验证码，自动判断手机或邮箱
 */
@RestController
public class NxtApiUserVerifiyCodeController {

    @Resource
    private NxtUserVerifiyService nxtUserVerifiyService;

    @RequestMapping(value = "/api/user/verifiy_code", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestBody JSONObject jsonParam) {

        String phoneOrEmail = jsonParam.getString("phoneOrEmail");
        Integer type = jsonParam.getInteger("type");

        if (phoneOrEmail == null || phoneOrEmail.isEmpty()){
            return new NxtStructApiResult(54,"请提供手机号或邮箱");
        }

        if (type == null){
            return new NxtStructApiResult(54,"缺少参数：验证码类型");
        }

        int max=999999;
        int min=111111;
        Random random = new Random();
        Long code = (long)random.nextInt(max)%(max-min+1) + min;

        NxtUserVerifiy nxtUserVerifiy = new NxtUserVerifiy();
        nxtUserVerifiy.setPhoneOrEmail(phoneOrEmail);
        nxtUserVerifiy.setDateline(System.currentTimeMillis());
        nxtUserVerifiy.setStatus(0);
        nxtUserVerifiy.setType(type);
        nxtUserVerifiy.setCode(code);
        nxtUserVerifiyService.insert(nxtUserVerifiy);

        return new NxtStructApiResult("开发调试阶段直接告诉你验证码："+code);

    }

}
