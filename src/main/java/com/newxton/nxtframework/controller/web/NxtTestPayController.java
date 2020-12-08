package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 */
@Controller
public class NxtTestPayController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtOrderFormPayService nxtOrderFormPayService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    @RequestMapping("/test_pay")
    public ModelAndView index(Device device, ModelAndView model, @Param("id") Long id,@Param("paymentMethod") String paymentMethod) {

        /**
         * 开发期间自动付款成功
         */

        model.setViewName("pc/test_pay");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //8秒后自动付款
                    Thread.sleep(8000);
                    //订单支付
                    autoPay(id,paymentMethod);
                }
                catch (Exception e){

                }

            }
        }).start();

        return model;

    }

    /**
     * 测试自动付款
     * @param id
     * @param paymentMethod
     */
    private void autoPay(Long id,String paymentMethod) {

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);

        if (nxtOrderForm == null || nxtOrderForm.getStatusPaid() > 0){
            return;
        }

        Integer platform = 888;

        //余额支付：自动充值一笔现金，然后自动付款成功
        if (paymentMethod.equals("balance")) {
            platform = 888;
        } else if (paymentMethod.equals("alipay")) {
            platform = 2;
        } else if (paymentMethod.equals("wxpay")) {
            platform = 1;
        } else if (paymentMethod.equals("paypal")) {
            platform = 3;
        }

        Long amount = nxtOrderForm.getAmountFinally()+nxtOrderForm.getDeliveryCost();
        if (nxtOrderForm.getManualDeliveryCostDiscount() != null){
            amount += nxtOrderForm.getManualDeliveryCostDiscount();
        }


        NxtRecharge nxtRecharge = new NxtRecharge();

        nxtRecharge.setUserId(nxtOrderForm.getUserId());
        nxtRecharge.setStatus(1);//状态（0:正在充值 1:成功 -1:失败）
        nxtRecharge.setPlatform(platform);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
        nxtRecharge.setDateline(System.currentTimeMillis());
        nxtRecharge.setAmount(amount);
        nxtRecharge.setRemark("测试自动充值");

        nxtRechargeService.insert(nxtRecharge);

        //插入Transaction表
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(nxtOrderForm.getUserId());
        nxtTransaction.setAmount(nxtRecharge.getAmount());
        nxtTransaction.setDateline(nxtRecharge.getDateline());
        nxtTransaction.setType(1);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setOuterId(nxtRecharge.getId());

        nxtTransactionService.insert(nxtTransaction);

        //更新充值TransactionId
        nxtRecharge.setTransactionId(nxtTransaction.getId());
        nxtRechargeService.update(nxtRecharge);

        //充值成功了，然后紧接着付款


        NxtTransaction nxtTransactionPay = new NxtTransaction();
        nxtTransactionPay.setUserId(nxtOrderForm.getUserId());
        nxtTransactionPay.setAmount(-amount);//消费记负数
        nxtTransactionPay.setDateline(System.currentTimeMillis());
        nxtTransactionPay.setType(2);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransactionService.insert(nxtTransactionPay);


        NxtOrderFormPay nxtOrderFormPay = new NxtOrderFormPay();
        nxtOrderFormPay.setOrderFormId(nxtOrderForm.getId());
        nxtOrderFormPay.setPriceDeal(amount);
        nxtOrderFormPay.setTransactionId(nxtTransactionPay.getId());

        nxtOrderFormPayService.insert(nxtOrderFormPay);

        //付款完成
        nxtTransactionPay.setOuterId(nxtOrderFormPay.getId());
        nxtTransactionService.update(nxtTransactionPay);

        //标记付款完成
        nxtOrderForm.setStatusPaid(1);
        nxtOrderForm.setDatelinePaid(nxtTransactionPay.getDateline());

        nxtOrderFormService.update(nxtOrderForm);

        //佣金表更新支付
        NxtCommission nxtCommissionCondition = new NxtCommission();
        nxtCommissionCondition.setOrderFormId(nxtOrderForm.getId());

        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAll(nxtCommissionCondition);
        for (NxtCommission item :
                nxtCommissionList) {
            item.setDatelineEnd(System.currentTimeMillis());
            item.setIsPaid(1);
            nxtCommissionService.update(item);
        }

    }

}
