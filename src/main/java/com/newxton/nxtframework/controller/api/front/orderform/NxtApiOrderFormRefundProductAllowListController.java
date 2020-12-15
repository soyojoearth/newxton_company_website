package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefundProductAllowItem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/25
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 售后申请--查看订单可退货物品详情
 */
@RestController
public class NxtApiOrderFormRefundProductAllowListController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;


    @RequestMapping("/api/order_form_refund/product_allow/list")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提供订单id");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);

        if (nxtOrderForm == null){
            return new NxtStructApiResult(34,"找不到该订单");
        }

        if (!nxtOrderForm.getUserId().equals(userId)){
            return new NxtStructApiResult(34,"该订单不属于你");
        }

        if (nxtOrderForm.getStatusPaid() < 1){
            return new NxtStructApiResult(34,"该订单尚未支付");
        }

        try {
            NxtStructOrderForm orderFormDetail = nxtProcessOrderForm.orderFormDetail(nxtOrderForm.getId());
            List<NxtStructOrderFormRefundProductAllowItem> nxtStructOrderFormRefundProductAllowItemList = nxtProcessOrderFormRefund.productAllowList(nxtOrderForm);
            Map<String,Object> data = new HashMap<>();
            data.put("orderFormDetail",orderFormDetail);
            data.put("list",nxtStructOrderFormRefundProductAllowItemList);
            return new NxtStructApiResult(data);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
