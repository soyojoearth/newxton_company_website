package com.newxton.nxtframework.controller.api.front.ucenter;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtRechargeService;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.service.NxtWithdrawService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserTransactionBalanceDetailController {

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @RequestMapping(value = "/api/user/transaction/balance_detail", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {

        //可提现金额
        Long balanceTotal = nxtTransactionService.queryBalanceSumByUserId(userId);
        //累计充值
        Long totalRechargeSuccess = nxtRechargeService.queryTotalRechargeSuccessByUserId(userId);
        //冻结金额
        Long totalWithdrawRejected = nxtWithdrawService.queryTotalWithdrawRejectedByUserId(userId);
        //累计提现
        Long totalWithdrawSuccess = nxtWithdrawService.queryTotalWithdrawSuccessByUserId(userId);
        //正在提现
        Long totalWithdrawing = nxtWithdrawService.queryTotalWithdrawSuccessByUserId(userId);

        balanceTotal = balanceTotal == null ? 0L : balanceTotal;
        totalRechargeSuccess = totalRechargeSuccess == null ? 0L : totalRechargeSuccess;
        totalWithdrawRejected = totalWithdrawRejected == null ? 0L : totalWithdrawRejected;
        totalWithdrawSuccess = totalWithdrawSuccess == null ? 0L : totalWithdrawSuccess;
        totalWithdrawing = totalWithdrawing == null ? 0L : totalWithdrawing;

        Map<String,Object> data = new HashMap<>();
        data.put("balanceTotal",balanceTotal/100F);
        data.put("balanceCanWithdraw",balanceTotal/100F);//可提现金额
        data.put("totalRechargeSuccess",totalRechargeSuccess/100F);
        data.put("totalWithdrawRejected",totalWithdrawRejected/100F);
        data.put("totalWithdrawSuccess",totalWithdrawSuccess/100F);
        data.put("totalWithdrawing",totalWithdrawing/100F);

        return new NxtStructApiResult(data);

    }

}
