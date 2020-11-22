package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 */
@RestController
public class NxtApiUserTransactionBalanceController {

    @Resource
    private NxtTransactionService nxtTransactionService;

    @RequestMapping(value = "/api/user/transaction/balance", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {

        Long amountCount = nxtTransactionService.queryAmountCountByUserId(userId);

        Float amount = amountCount == null ? 0.00F : amountCount/100F;

        return new NxtStructApiResult(amount);

    }

}
