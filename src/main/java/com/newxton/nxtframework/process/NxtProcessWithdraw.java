package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.entity.NxtWithdraw;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.service.NxtWithdrawService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/28
 * @address Shenzhen, China
 */
@Component
public class NxtProcessWithdraw {

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    /**
     * 某用户创建提现申请
     * @param userId
     * @param amount
     * @param platformAccount
     * @param platformPerson
     * @throws NxtException
     */
    @Transactional(rollbackFor=Exception.class)
    public void create(Long userId,Long amount,String platform, String platformAccount,String platformPerson) throws NxtException {

        Integer platformType = 0;//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
        if (platform.equals("alipay")){
            platformType = 2;
        }
        else if (platform.equals("wxpay")){
            platformType = 1;
        }
        else if (platform.equals("paypal")){
            platformType = 3;
        }
        else if (platform.equals("bank")){
            platformType = 0;
        }
        else {
            throw new NxtException("该提现渠道不支持:"+platform);
        }

        //检查可提现金额
        Long balanceTotal = nxtTransactionService.queryBalanceSumByUserId(userId);
        if (!(balanceTotal != null && balanceTotal > amount)){
            //可提现金额不够
            throw new NxtException("可提现金额不足");
        }

        //账本记录一条提现记录
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(userId);
        nxtTransaction.setDateline(System.currentTimeMillis());
        nxtTransaction.setAmount(-amount);//提现是负数,账本金额（正数进，负数出）
        nxtTransaction.setType(4);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransactionService.insert(nxtTransaction);

        //插入提现申请
        NxtWithdraw nxtWithdraw = new NxtWithdraw();
        nxtWithdraw.setUserId(userId);
        nxtWithdraw.setTransactionId(nxtTransaction.getId());
        nxtWithdraw.setStatus(0);//状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
        nxtWithdraw.setAmount(amount);
        nxtWithdraw.setDatelineCreate(System.currentTimeMillis());
        nxtWithdraw.setPlatform(platformType);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
        nxtWithdraw.setPlatformPerson(platformPerson);
        nxtWithdraw.setPlatformAccount(platformAccount);

        nxtWithdrawService.insert(nxtWithdraw);

        //更新账本提现id
        nxtTransaction.setOuterId(nxtWithdraw.getId());
        nxtTransactionService.update(nxtTransaction);

    }

}
