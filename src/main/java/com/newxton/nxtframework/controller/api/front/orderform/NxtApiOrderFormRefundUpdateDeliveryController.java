package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtOrderFormDelivery;
import com.newxton.nxtframework.entity.NxtOrderFormRefund;
import com.newxton.nxtframework.entity.NxtOrderFormRefundDelivery;
import com.newxton.nxtframework.service.NxtOrderFormRefundDeliveryService;
import com.newxton.nxtframework.service.NxtOrderFormRefundService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/8
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormRefundUpdateDeliveryController {

    @Resource
    private NxtOrderFormRefundService nxtOrderFormRefundService;

    @Resource
    private NxtOrderFormRefundDeliveryService nxtOrderFormRefundDeliveryService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping("/api/order_form_refund/update_delivery")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");
        String deliveryCompanyName = jsonParam.getString("deliveryCompanyName");
        String deliverySerialNum = jsonParam.getString("deliverySerialNum");

        if (id == null){
            return new NxtStructApiResult(53,"缺少参数：id");
        }
        if (deliveryCompanyName == null || deliveryCompanyName.trim().isEmpty()){
            return new NxtStructApiResult(53,"请提交快递名字");
        }
        if (deliverySerialNum == null || deliverySerialNum.trim().isEmpty()){
            return new NxtStructApiResult(53,"请提交快递单号");
        }

        NxtOrderFormRefund nxtOrderFormRefund = nxtOrderFormRefundService.queryById(id);

        if (nxtOrderFormRefund == null){
            return new NxtStructApiResult(53,"找不到该售后单");
        }

        if (!nxtOrderFormRefund.getUserId().equals(userId)){
            return new NxtStructApiResult(53,"该售后单不属于你");
        }

        //状态（-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:直接退款 5:用户已寄出物品）
        if (!(nxtOrderFormRefund.getStatus().equals(2) || nxtOrderFormRefund.getStatus().equals(5))){
            return new NxtStructApiResult(53,"当前状态不可更新物流");
        }

        NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery = nxtOrderFormRefundDeliveryService.queryByOrderFormRefundId(id);

        if (nxtOrderFormRefundDelivery == null) {
            nxtOrderFormRefundDelivery = new NxtOrderFormRefundDelivery();
            nxtOrderFormRefundDelivery.setOrderFormRefundId(nxtOrderFormRefund.getId());
        }
        nxtOrderFormRefundDelivery.setDeliveryCompanyName(deliveryCompanyName);
        nxtOrderFormRefundDelivery.setDeliverySerialNum(deliverySerialNum);

        if (nxtOrderFormRefundDelivery.getId() == null) {
            nxtOrderFormRefundDeliveryService.insert(nxtOrderFormRefundDelivery);
        }
        else {
            nxtOrderFormRefundDeliveryService.update(nxtOrderFormRefundDelivery);
        }

        nxtOrderFormRefund.setStatus(5);
        nxtOrderFormRefundService.update(nxtOrderFormRefund);


        return new NxtStructApiResult();

    }

}
