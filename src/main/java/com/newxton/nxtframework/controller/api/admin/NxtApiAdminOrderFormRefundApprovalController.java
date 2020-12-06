package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormRefund;
import com.newxton.nxtframework.entity.NxtOrderFormRefundProduct;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormRefundProductService;
import com.newxton.nxtframework.service.NxtOrderFormRefundService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminOrderFormRefundApprovalPost;
import com.newxton.nxtframework.struct.admin.NxtStructAdminOrderFormRefundApprovalPostItemAmount;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/6
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormRefundApprovalController {

    @Resource
    private NxtOrderFormRefundService nxtOrderFormRefundService;

    @Resource
    private NxtOrderFormRefundProductService nxtOrderFormRefundProductService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @RequestMapping(value = "/api/admin/order_form_refund/approval", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();
        NxtStructAdminOrderFormRefundApprovalPost nxtStructAdminOrderFormRefundApprovalPost;
        try {
            nxtStructAdminOrderFormRefundApprovalPost = gson.fromJson(json, NxtStructAdminOrderFormRefundApprovalPost.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对").toMap();
        }

        Long id = nxtStructAdminOrderFormRefundApprovalPost.getId();
        Integer status = nxtStructAdminOrderFormRefundApprovalPost.getStatus();
        String remark = nxtStructAdminOrderFormRefundApprovalPost.getRemark();
        List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> refundAmountList = nxtStructAdminOrderFormRefundApprovalPost.getRefundAmountList();
        Boolean refundDeliveryCost = nxtStructAdminOrderFormRefundApprovalPost.getRefundDeliveryCost();

        if (id == null) {
            return new NxtStructApiResult(53, "缺少参数：id").toMap();
        }
        if (status == null) {
            return new NxtStructApiResult(53, "缺少参数：status").toMap();
        }
        if (remark == null) {
            return new NxtStructApiResult(53, "缺少参数：remark").toMap();
        }

        Set<Integer> statusSet = Stream.of(-1, 2, 3, 4).collect(toSet());
        if (!statusSet.contains(status)) {
            return new NxtStructApiResult(53, "status错误").toMap();
        }

        NxtOrderFormRefund nxtOrderFormRefund = nxtOrderFormRefundService.queryById(id);

        if (nxtOrderFormRefund == null) {
            return new NxtStructApiResult(53, "找不到该售后单").toMap();
        }

        //状态（-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:直接退款 5:用户已寄出物品）
        //审核结果：(-1:拒绝退款 2:同意退货，等待用户发货 3:收到货退款 4:直接退款)
        //不可随意设置状态

        //-1:拒绝退款
        if (status.equals(-1)) {
            Set<Integer> statusRejection = Stream.of(1, 2).collect(toSet());
            if (statusRejection.contains(nxtOrderFormRefund.getStatus())) {
                return new NxtStructApiResult(51, "当前状态下不可设置[拒绝退款]").toMap();
            }
        }
        //2:同意退货，等待用户发货
        if (status.equals(2)) {
            Set<Integer> statusRejection = Stream.of(1, 2, 3, 4, 5).collect(toSet());
            if (statusRejection.contains(nxtOrderFormRefund.getStatus())) {
                return new NxtStructApiResult(51, "当前状态下不可设置[等待用户发货]").toMap();
            }
        }
        //3:收到货退款
        if (status.equals(3)) {
            Set<Integer> statusRejection = Stream.of(1).collect(toSet());
            if (statusRejection.contains(nxtOrderFormRefund.getStatus())) {
                return new NxtStructApiResult(51, "当前状态下不可设置[收到货退款]").toMap();
            }
        }
        //4:直接退款
        if (status.equals(4)) {
            Set<Integer> statusRejection = Stream.of(1).collect(toSet());
            if (statusRejection.contains(nxtOrderFormRefund.getStatus())) {
                return new NxtStructApiResult(51, "当前状态下不可设置[直接退款]").toMap();
            }
        }

        Set<Integer> statusRefundSet = Stream.of(3,4).collect(toSet());

        if (statusRefundSet.contains(status)){
            try {
                //执行退款操作
                this.processRefund(nxtOrderFormRefund,refundAmountList,refundDeliveryCost);
                return new NxtStructApiResult().toMap();
            }
            catch (NxtException e){
                return new NxtStructApiResult(e.getNxtExecptionMessage()).toMap();
            }

        }
        else {
            nxtOrderFormRefund.setStatus(status);
            nxtOrderFormRefundService.update(nxtOrderFormRefund);
            return new NxtStructApiResult().toMap();
        }

    }

    /**
     * 执行退款操作
     * @param nxtOrderFormRefund
     * @param refundAmountList
     */
    @Transactional(rollbackFor = { Exception.class, NxtException.class })
    public void processRefund(NxtOrderFormRefund nxtOrderFormRefund, List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> refundAmountList, Boolean refundDeliveryCost) throws NxtException {


        NxtTransaction nxtTransactionCondition = new NxtTransaction();
        nxtTransactionCondition.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransactionCondition.setOuterId(nxtOrderFormRefund.getId());
        List<NxtTransaction> nxtTransactionList = nxtTransactionService.queryAll(nxtTransactionCondition);

        if (nxtTransactionList.size() > 0){
            throw new NxtException("不可重复退款");
        }

        //自定义最终退款金额
        Map<Long,Long> mapRefundAmount = new HashMap<>();
        if (refundAmountList != null){
            for (NxtStructAdminOrderFormRefundApprovalPostItemAmount item : refundAmountList) {
                mapRefundAmount.put(item.getOrderFromRefundProductId(),(long)(item.getOrderFromRefundProductAmount()*100));
            }
        }

        //查询物品
        List<NxtOrderFormRefundProduct> nxtOrderFormRefundProductList = nxtOrderFormRefundProductService.selectAllByOrderFormRefundIdSet(Stream.of(nxtOrderFormRefund.getId()).collect(toList()));

        Long amountRefundTotal = 0L;
        for (NxtOrderFormRefundProduct item : nxtOrderFormRefundProductList) {
            if (mapRefundAmount.containsKey(item.getId())){
                //修改成最终退款金额
                if (mapRefundAmount.get(item.getId()) > item.getAmountRefund()){
                    throw new NxtException("有物品退款额过多，请修改");
                }
                item.setAmountRefund(mapRefundAmount.get(item.getId()));
                nxtOrderFormRefundProductService.update(item);
            }
            amountRefundTotal += item.getAmountRefund();
        }

        //退款到余额(记到账本)
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(nxtOrderFormRefund.getUserId());
        nxtTransaction.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setAmount(amountRefundTotal);
        nxtTransaction.setDateline(System.currentTimeMillis());
        nxtTransaction.setOuterId(nxtOrderFormRefund.getId());
        nxtTransactionService.insert(nxtTransaction);

        //退运费
        if (refundDeliveryCost != null && refundDeliveryCost){
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtOrderFormRefund.getOrderFormId());
            Long cost = nxtOrderForm.getDeliveryCost();
//            cost += nxtOrderForm.getManualDeliveryCostDiscount();
            if (nxtOrderForm.getManualDeliveryCostDiscount() != null){
                cost += nxtOrderForm.getManualDeliveryCostDiscount();
            }
            if (cost > 0) {
                NxtTransaction nxtTransactionDeliveryCost = new NxtTransaction();
                nxtTransactionDeliveryCost.setUserId(nxtOrderFormRefund.getUserId());
                nxtTransactionDeliveryCost.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
                nxtTransactionDeliveryCost.setAmount(cost);
                nxtTransactionDeliveryCost.setDateline(System.currentTimeMillis());
                nxtTransactionDeliveryCost.setOuterId(nxtOrderFormRefund.getId());
                nxtTransactionService.insert(nxtTransactionDeliveryCost);
            }
        }

        //设置售后完成
        nxtOrderFormRefund.setStatus(1);
        nxtOrderFormRefundService.update(nxtOrderFormRefund);

    }

}
