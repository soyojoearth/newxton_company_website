package com.newxton.nxtframework.controller.api.front.ucenter;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.service.NxtCommissionService;
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
 */
@RestController
public class NxtApiUserTransactionBalanceDetailController {

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    @RequestMapping(value = "/api/user/transaction/balance_detail", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {


        Long balanceCount = nxtTransactionService.queryBalanceSumByUserId(userId);

        Float balance = balanceCount == null ? 0.00F : balanceCount/100F;
        Long balanceFrozen = 0L;//冻结金额
        Long balanceCanWithdraw = 0L;//可提现金额
        Long totalRecharge = 0L;//累计充值
        Long totalWithdraw = 0L;//累计提现
        Long totalWithdrawing = 0L;//正在提现

        Map<String,Object> data = new HashMap<>();
        data.put("balance",balance/100F);
        data.put("balanceFrozen",balanceFrozen/100F);
        data.put("balanceCanWithdraw",balanceCanWithdraw/100F);
        data.put("totalRecharge",totalRecharge/100F);
        data.put("totalWithdraw",totalWithdraw/100F);
        data.put("totalWithdrawing",totalWithdrawing/100F);

        return new NxtStructApiResult(data);

    }

}
