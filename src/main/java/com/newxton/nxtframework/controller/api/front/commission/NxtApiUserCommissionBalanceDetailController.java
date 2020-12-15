package com.newxton.nxtframework.controller.api.front.commission;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtCommissionTransferInService;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserCommissionBalanceDetailController {

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtCommissionTransferInService nxtCommissionTransferInService;

    @RequestMapping(value = "/api/user/commission/balance_detail", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {

        //可结转收益
        Long balanceAllowTransfer = 0L;
        //查询所有已完成交易、且未结转的有效佣金记录
        List<NxtCommission> nxtCommissionListAllowTransfer = nxtCommissionService.queryAllAllowTransferByUserId(userId);
        for (NxtCommission nxtCommission : nxtCommissionListAllowTransfer) {
            //未退货退款数量
            Long quantityNotRefund = nxtCommission.getQuantityDeal() - nxtCommission.getQuantityRefund();
            //可结转收益
            balanceAllowTransfer += nxtCommission.getCommissionAmount() * quantityNotRefund;
        }

        //等待交易完成收益
        Long balanceWaitDealCompleate = 0L;
        //查询所有已付款、等待交易完成的有效佣金记录
        List<NxtCommission> nxtCommissionListWaitDealCompleate = nxtCommissionService.queryAllAllowTransferByUserId(userId);
        for (NxtCommission nxtCommission : nxtCommissionListWaitDealCompleate) {
            //未退货退款数量
            Long quantityNotRefund = nxtCommission.getQuantityDeal() - nxtCommission.getQuantityRefund();
            //等待交易完成收益
            balanceWaitDealCompleate += nxtCommission.getCommissionAmount() * quantityNotRefund;
        }

        //结转中的收益（已提交结转申请，等待审核）
        Long balanceIsTransfering = nxtCommissionTransferInService.querySumIsTransferingByUserId(userId);
        if (balanceIsTransfering == null){
            balanceIsTransfering = 0L;
        }

        //锁定收益（已提交结转申请，审核被拒绝）
        Long balanceIsRejected = nxtCommissionTransferInService.querySumIsTransferRejectedByUserId(userId);
        if (balanceIsRejected == null){
            balanceIsRejected = 0L;
        }

        Map<String,Object> data = new HashMap<>();
        data.put("balanceAll",(balanceAllowTransfer+balanceWaitDealCompleate)/100F);
        data.put("balanceAllowTransfer",balanceAllowTransfer/100F);
//        data.put("balanceWaitDealCompleate",balanceWaitDealCompleate/100F);
        data.put("balanceIsTransfering",balanceIsTransfering/100F);
        data.put("balanceIsRejected",balanceIsRejected/100F);

        return new NxtStructApiResult(data);

    }

}
