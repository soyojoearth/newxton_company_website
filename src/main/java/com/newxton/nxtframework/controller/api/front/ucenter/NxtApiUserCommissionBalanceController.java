package com.newxton.nxtframework.controller.api.front.ucenter;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.service.NxtCommissionService;
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
 * @time 2020/11/22
 * @address Shenzhen, China
 */
@RestController
public class NxtApiUserCommissionBalanceController {

    @Resource
    private NxtCommissionService nxtCommissionService;

    @RequestMapping(value = "/api/user/commission/balance", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {


        NxtCommission nxtCommissionCondition = new NxtCommission();
        nxtCommissionCondition.setUserId(userId);
        nxtCommissionCondition.setIsTransfer(0);

        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAll(nxtCommissionCondition);

        Long balanceAllowTransfer = 0L;
        Long balanceIsTransfering = 0L;
        Long balanceWaitDealCompleate = 0L;
        Long balanceWaitPay = 0L;

        for (NxtCommission item : nxtCommissionList) {
            if (item.getIsPaid().equals(1)){
                //已经支付
                //未退货退款数量
                Long quantityNotRefund = item.getQuantityDeal() - item.getQuantityRefund();
                if (item.getCommissionTransferInId() != null && item.getCommissionTransferInId() > 0){
                    //结转中收益
                    balanceIsTransfering += item.getCommissionAmount() * quantityNotRefund;
                }
                else {
                    if (item.getDatelineEnd() != null && item.getDatelineEnd() > 0){
                        //可结转收益
                        balanceIsTransfering += item.getCommissionAmount() * quantityNotRefund;
                    }
                    else {
                        //等待交易完成
                        balanceWaitDealCompleate += item.getCommissionAmount() * quantityNotRefund;
                    }
                }
            }
            else {
                //未支付
                balanceWaitPay += item.getCommissionAmount() * item.getQuantityDeal();
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("balanceAllowTransfer",balanceAllowTransfer/100F);
        data.put("balanceIsTransfering",balanceIsTransfering/100F);
        data.put("balanceWaitDealCompleate",balanceWaitDealCompleate/100F);
        data.put("balanceWaitPay",balanceWaitPay/100F);

        return new NxtStructApiResult(data);

    }

}
