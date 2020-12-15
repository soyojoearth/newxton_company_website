package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtOrderFormPayService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtTransactionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/14
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 使用余额付款
 */
@Component
public class NxtProcessOrderFormPayByBalance {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtOrderFormPayService nxtOrderFormPayService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    /**
     * 执行余额付款
     * @param serialNum
     * @throws NxtException
     */
    @Transactional(rollbackFor=Exception.class)
    public void exec (String serialNum) throws NxtException {

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryBySerialNum(serialNum);

        if (nxtOrderForm == null || nxtOrderForm.getStatusPaid() > 0){
            return;
        }

        Long amount = nxtOrderForm.getAmountFinally();
        if(nxtOrderForm.getDeliveryCost() != null){
            amount += nxtOrderForm.getDeliveryCost();
        }
        if (nxtOrderForm.getManualDeliveryCostDiscount() != null){
            amount += nxtOrderForm.getDeliveryCost();
        }

        //查询余额是否充足
        Long balanceCount = nxtTransactionService.queryBalanceSumByUserId(nxtOrderForm.getUserId());

        if (balanceCount == null || balanceCount < amount){
            throw new NxtException("余额不足");
        }

        //然后紧接着付款
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
        for (NxtCommission item : nxtCommissionList) {
            item.setDatelineEnd(System.currentTimeMillis());
            item.setIsPaid(1);
            nxtCommissionService.update(item);
        }

    }

}
