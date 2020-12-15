package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormRefund;
import com.newxton.nxtframework.entity.NxtOrderFormRefundProduct;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.service.NxtOrderFormRefundProductService;
import com.newxton.nxtframework.service.NxtOrderFormRefundService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminOrderFormRefundApprovalPost;
import com.newxton.nxtframework.struct.admin.NxtStructAdminOrderFormRefundApprovalPostItemAmount;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
 * @copyright NxtFramework
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

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

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
                nxtProcessOrderFormRefund.execRefund(nxtOrderFormRefund,refundAmountList,refundDeliveryCost);
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



}
