package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/14
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessFinishRechargeCallback {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtProcessOrderFormPayByBalance nxtProcessOrderFormPayByBalance;

    /**
     * 执行充值成功且检查订单付款
     * @param serialNum
     * @param paymentMethod
     * @param trade_no
     * @param notifyData
     */
    public void didComplete(String serialNum,Long amount,String paymentMethod,String trade_no,String notifyData) throws NxtException {

        NxtRecharge nxtRecharge = nxtRechargeService.queryBySerialNum(serialNum);

        if (nxtRecharge == null || !nxtRecharge.getStatus().equals(0)){
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

        nxtRecharge.setStatus(1);//状态（0:正在充值 1:成功 -1:失败）
        nxtRecharge.setPlatform(platform);
        nxtRecharge.setAmount(amount);
        nxtRecharge.setNotifyDateline(System.currentTimeMillis());
        nxtRecharge.setNotifySerialNum(trade_no);
        nxtRecharge.setNotifyData(notifyData);
        nxtRechargeService.update(nxtRecharge);

        //插入Transaction表
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(nxtRecharge.getUserId());
        nxtTransaction.setAmount(nxtRecharge.getAmount());
        nxtTransaction.setDateline(nxtRecharge.getDateline());
        nxtTransaction.setType(1);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setOuterId(nxtRecharge.getId());

        nxtTransactionService.insert(nxtTransaction);

        //更新充值TransactionId
        nxtRecharge.setTransactionId(nxtTransaction.getId());
        nxtRechargeService.update(nxtRecharge);

        //充值成功了，如果有附带订单，就紧接着付款
        if (nxtRecharge.getOrderFormId() == null){
            return;
        }
        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtRecharge.getOrderFormId());
        //订单不存在，略过
        if (nxtOrderForm == null){
            return;
        }
        //订单已支付，略过
        if (nxtOrderForm.getStatusPaid().equals(1)){
            return;
        }

        //使用余额付款
        nxtProcessOrderFormPayByBalance.exec(nxtOrderForm.getSerialNum());

    }
}
