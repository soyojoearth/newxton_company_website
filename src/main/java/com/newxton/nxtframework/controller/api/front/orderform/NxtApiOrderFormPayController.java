package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.process.NxtProcessOrderFormPayByBalance;
import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtRechargeService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormPayController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtProcessOrderFormPayByBalance nxtProcessOrderFormPayByBalance;

    @RequestMapping("/api/order_form/pay")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");

        String paymentMethod = jsonParam.getString("paymentMethod");

        if (id == null){
            return new NxtStructApiResult(54,"请提供订单id");
        }
        if (paymentMethod == null){
            return new NxtStructApiResult(54,"请提交付款方式");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);

        if (!(nxtOrderForm != null && nxtOrderForm.getUserId().equals(userId))){
            return new NxtStructApiResult(34,"未找到订单或该订单不属于你");
        }

        if (nxtOrderForm.getStatusPaid() > 0){
            //已经付款过了
            return new NxtStructApiResult(24,"该订单已经付过了，请勿重复支付");
        }


        if (paymentMethod.equals("balance")){
            //余额付款
            try {
                //支付
                nxtProcessOrderFormPayByBalance.exec(nxtOrderForm.getSerialNum());
                //支付完成
                return new NxtStructApiResult(0,"支付完成，请到个人中心查看订单列表");
            }
            catch (NxtException e){
                // 支付错误
                return new NxtStructApiResult(53,e.getNxtExecptionMessage());
            }
        }
        else {
            //第三方充值付款
            Long amount = nxtOrderForm.getAmountFinally();
            if(nxtOrderForm.getDeliveryCost() != null){
                amount += nxtOrderForm.getDeliveryCost();
            }
            if (nxtOrderForm.getManualDeliveryCostDiscount() != null){
                amount += nxtOrderForm.getDeliveryCost();
            }

            //创建充值&顺带订单支付
            NxtRecharge nxtRecharge = new NxtRecharge();
            nxtRecharge.setSerialNum(this.createSerialNum(System.currentTimeMillis()));
            nxtRecharge.setUserId(nxtOrderForm.getUserId());
            nxtRecharge.setOrderFormId(nxtOrderForm.getId());
            nxtRecharge.setStatus(0);//状态（0:正在充值 1:成功 -1:失败）
            nxtRecharge.setDateline(System.currentTimeMillis());
            nxtRecharge.setAmount(amount);
            nxtRecharge.setRemark("订单付款");

            Map<String,Object> data = new HashMap<>();

            if (paymentMethod.equals("alipay")){
                //生成支付宝付款链接或其它付款凭证放入data
                nxtRecharge.setPlatform(2);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
                nxtRechargeService.insert(nxtRecharge);
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
            else {
                return new NxtStructApiResult(53,"请选择正确的支付方式");
            }

            return new NxtStructApiResult(data);

        }

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
