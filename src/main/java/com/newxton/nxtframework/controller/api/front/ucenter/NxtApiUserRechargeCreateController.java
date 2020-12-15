package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtRechargeService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserRechargeCreateController {

    @Resource
    private NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @RequestMapping(value = "/api/user/recharge/create", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId, @RequestBody JSONObject jsonParam) {

        Float amount = jsonParam.getFloat("amount");
        String paymentMethod = jsonParam.getString("paymentMethod");

        if (amount == null){
            return new NxtStructApiResult(54,"请填写充值金额");
        }
        if (paymentMethod == null){
            return new NxtStructApiResult(54,"请选择充值方式");
        }

        Map<String,Object> data = new HashMap<>();

        Integer platform = null;
        if (paymentMethod.equals("alipay")){
            platform = 2;
            //生成支付宝付款链接或其它付款凭证放入data
        }
        else if (paymentMethod.equals("wxpay")){
            platform = 1;
            //生成微信付款链接或其它付款凭证放入data
        }
        else if (paymentMethod.equals("paypal")){
            platform = 3;
            //生成paypal付款链接或其它付款凭证放入data
        }
        if (platform == null){
            return new NxtStructApiResult(54,"请选择正确的充值方式");
        }

        NxtRecharge nxtRecharge = new NxtRecharge();
        nxtRecharge.setSerialNum(this.createSerialNum(System.currentTimeMillis()));
        nxtRecharge.setUserId(userId);
        nxtRecharge.setStatus(0);//状态（0:正在充值 1:成功 -1:失败）
        nxtRecharge.setPlatform(platform);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
        nxtRecharge.setDateline(System.currentTimeMillis());
        nxtRecharge.setAmount((long)(amount * 100));
        nxtRecharge.setRemark("测试自动充值");

        nxtRechargeService.insert(nxtRecharge);

        if (paymentMethod.equals("alipay")){
            //生成支付宝付款链接或其它付款凭证放入data
            data.put("redirectURL", nxtWebUtilComponent.getDomainPath() + "/payment/alipay?serial_num=" + nxtRecharge.getSerialNum());
        }
        else if (paymentMethod.equals("wxpay")){
            //生成微信付款链接
            data.put("redirectURL", nxtWebUtilComponent.getDomainPath() + "/payment/wxpay?serial_num=" + nxtRecharge.getSerialNum());
        }
        else if (paymentMethod.equals("paypal")){
            //生成paypal付款链接
            data.put("redirectURL", nxtWebUtilComponent.getDomainPath() + "/payment/paypal?serial_num=" + nxtRecharge.getSerialNum());
        }

        return new NxtStructApiResult(data);

    }

    /**
     * 生成充值订单号码
     * @return
     */
    private String createSerialNum(Long dateline){
        String serialNum = "NXT-RH-" + dateline + this.getRandomUppercaseLetter(6);
        return serialNum;
    }

    /**
     * 取随机大写字母
     * @param length
     * @return
     */
    public String getRandomUppercaseLetter(int length) {
        String str = "ACFHKLPQSTUVWXYZ";
        Random random = new Random();
        StringBuffer buffet = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length() - 1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }


}
