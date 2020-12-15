package com.newxton.nxtframework.component;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtRechargeService;
import com.newxton.nxtframework.struct.NxtStructSettingPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtPaymentAlipayComponent {

    private Logger logger = LoggerFactory.getLogger(NxtPaymentAlipayComponent.class);

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Resource
    private NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    /**
     * Callback 验签
     * @param params
     * @return
     * @throws Exception
     */
    public Boolean verifyNotify(Map<String,String> params) throws Exception {
        this.initConfig();
        return Factory.Payment.Common().verifyNotify(params);
    }

    /**
     * 创建电脑端支付
     * @param serialNum
     * @return
     * @throws NxtException
     */
    public AlipayTradePagePayResponse createPagePayResponse(String serialNum) throws NxtException {

        NxtRecharge nxtRecharge = nxtRechargeService.queryBySerialNum(serialNum);

        if (nxtRecharge == null){
            throw new NxtException("该充值订单不存在");
        }

        if (!(nxtRecharge.getStatus() != null && nxtRecharge.getStatus().equals(0))){
            throw new NxtException("该充值订单无效");
        }

        String subject = "Recharge";
        if (nxtRecharge.getOrderFormId() != null) {
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtRecharge.getOrderFormId());
            if (nxtOrderForm == null){
                throw new NxtException("找不到订单");
            }
            subject = nxtOrderForm.getSerialNum();
        }

        this.initConfig();

        Float totalAmount = nxtRecharge.getAmount()/100F;

        String returnUrl = nxtWebUtilComponent.getDomainPath()+"/ucenter/#/order";

        if (nxtRecharge.getOrderFormId() == null){
            returnUrl = nxtWebUtilComponent.getDomainPath()+"/ucenter/";
        }

        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page().pay(subject, nxtRecharge.getSerialNum(),totalAmount.toString(),returnUrl);
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                logger.info("Alipay创建订单功："+nxtRecharge.getSerialNum());
                return response;
            } else {
                logger.warn("Alipay创建订单失败："+nxtRecharge.getSerialNum()+" "+response.getBody());
                throw new NxtException("Alipay创建订单失败，请重试");
            }
        } catch (Exception e) {
            logger.warn("Alipay调用遭遇异常，订单号："+nxtRecharge.getSerialNum()+"，原因：" + e.getMessage());
            throw new NxtException("Alipay调用遭遇异常，请系统管理员查看该订单创建支付日志：" +nxtRecharge.getSerialNum());
        }
    }

    /**
     * 创建手机端支付
     * @param serialNum
     * @return
     * @throws NxtException
     */
    public AlipayTradeWapPayResponse createWapPayResponse(String serialNum) throws NxtException {

        NxtRecharge nxtRecharge = nxtRechargeService.queryBySerialNum(serialNum);

        if (nxtRecharge == null){
            throw new NxtException("该充值订单不存在");
        }

        if (nxtRecharge.getStatus() != null && !nxtRecharge.getStatus().equals(1)){
            throw new NxtException("该充值订单无效");
        }

        String subject = "Recharge";
        if (nxtRecharge.getOrderFormId() != null) {
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtRecharge.getOrderFormId());
            if (nxtOrderForm == null){
                throw new NxtException("找不到订单");
            }
            subject = nxtOrderForm.getSerialNum();
        }

        this.initConfig();

        Float totalAmount = nxtRecharge.getAmount()/100F;

        String returnUrl = nxtWebUtilComponent.getDomainPath()+"/ucenter/#/order";

        if (nxtRecharge.getOrderFormId() == null){
            returnUrl = nxtWebUtilComponent.getDomainPath()+"/ucenter/";
        }

        try {
            AlipayTradeWapPayResponse response = Factory.Payment.Wap().pay(subject, nxtRecharge.getSerialNum(),totalAmount.toString(),returnUrl,returnUrl);
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                logger.info("Alipay创建订单功："+nxtRecharge.getSerialNum());
                return response;
            } else {
                logger.warn("Alipay创建订单失败："+nxtRecharge.getSerialNum()+" "+response.getBody());
                throw new NxtException("Alipay创建订单失败，请重试");
            }
        } catch (Exception e) {
            logger.warn("Alipay调用遭遇异常，订单号："+nxtRecharge.getSerialNum()+"，原因：" + e.getMessage());
            throw new NxtException("Alipay调用遭遇异常，请系统管理员查看该订单创建支付日志：" +nxtRecharge.getSerialNum());
        }
    }

    /**
     * 初始化配置
     * @throws NxtException
     */
    private void initConfig() throws NxtException {
        // 1. 设置参数（全局只需设置一次）
        // 【但是后台可以随时修改最新的配置，还是需要每次创建支付时设置Config，没什么大不了的】
        Factory.setOptions(getOptions());
    }

    private Config getOptions() throws NxtException {

        NxtStructSettingPayConfig nxtStructSettingPayConfig = nxtGlobalSettingComponent.getNxtStructSettingPayConfig();

        if (
                nxtStructSettingPayConfig.getAlipayAPPID() == null || nxtStructSettingPayConfig.getAlipayAPPID().trim().isEmpty() ||
                nxtStructSettingPayConfig.getAlipaySecretKey() == null || nxtStructSettingPayConfig.getAlipaySecretKey().trim().isEmpty() ||
                nxtStructSettingPayConfig.getAlipayPublicKey() == null || nxtStructSettingPayConfig.getAlipayPublicKey().trim().isEmpty()

        ){
            throw new NxtException("Require:Alipay config");
        }

        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = nxtStructSettingPayConfig.getAlipayAPPID();
        config.merchantPrivateKey = nxtStructSettingPayConfig.getAlipaySecretKey();
        config.alipayPublicKey = nxtStructSettingPayConfig.getAlipayPublicKey();

        //设置自定义callback路径
        config.notifyUrl = nxtWebUtilComponent.getDomainPath() + "/api/callback/alipay";

        return config;
    }


}
