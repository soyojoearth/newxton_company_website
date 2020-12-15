package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * 后台手动调整订单金额
 *
 */
@RestController
public class NxtApiAdminOrderFormUpdatePriceController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    @RequestMapping(value = "/api/admin/order_form/update_price", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Float manualAmountDiscount = jsonParam.getFloat("manualAmountDiscount");
        Float manualDeliveryCostDiscount = jsonParam.getFloat("manualDeliveryCostDiscount");

        if (id == null){
            return new NxtStructApiResult(53,"缺少参数：id").toMap();
        }

        try {
            this.processDo(id,manualAmountDiscount,manualDeliveryCostDiscount);
            return new NxtStructApiResult().toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(53,e.getNxtExecptionMessage()).toMap();
        }

    }

    /**
     * 调整金额运费（正数加、负数减）
     *
     * 收到总调整金额（正数加、负数减），然后将总调整金额分摊到nxt_order_form_product表中的N个物品中的manual_price_discount字段（正数加、负数减）
     * 且重新计算nxt_order_form_product表中的product_price_deal字段（等于原价减去所有折扣和调整）
     * 把调整后的N个物品总价更新到nxt_order_form表的amount_finally字段（最终付款总金额）
     * 调整的运费更新到 nxt_order_form表 的 manual_delivery_cost_discount 字段
     * 还需要查找nxt_commission分销表中对应order_form_product_id的条目，计算调整后佣金，然后更新commission_amount字段。
     * 经过以上调整后，退款时不会多退，分销佣金不会多给。
     *
     * @param orderFormId
     * @param manualAmountDiscount
     * @param manualDeliveryCostDiscount
     * @throws NxtException
     */
    @Transactional(rollbackFor=Exception.class)
    public void processDo(Long orderFormId, Float manualAmountDiscount, Float manualDeliveryCostDiscount) throws NxtException {

        if (manualAmountDiscount == null || manualDeliveryCostDiscount == null){
            throw  new NxtException("请提供金额调整、运费调整");
        }

        Long manualAmountDiscountLong = (long)(manualAmountDiscount * 100L);
        Long manualDeliveryCostDiscountLong = (long)(manualDeliveryCostDiscount * 100L);

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(orderFormId);
        if (nxtOrderForm == null){
            throw  new NxtException("找不到对应订单");
        }
        if (nxtOrderForm.getStatusPaid().equals(1)){
            //已经支付成功
            throw  new NxtException("订单已经支付，不可再调整金额");
        }

        Map<Long,Long> mapOrderFormProductIdToPricePrice = new HashMap<>();

        //修改orderFormProduct表物品折扣、总金额
        NxtOrderFormProduct nxtOrderFormProductCondition = new NxtOrderFormProduct();
        nxtOrderFormProductCondition.setOrderFormId(orderFormId);
        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.queryAll(nxtOrderFormProductCondition);
        Long priceTotal = 0L;
        for (NxtOrderFormProduct item : nxtOrderFormProductList) {
            priceTotal += item.getProductPrice();
        }
        Long amountFinally = 0L;
        for (NxtOrderFormProduct item : nxtOrderFormProductList) {
            Float percent = (float)item.getProductPrice() / (float)priceTotal;
            Long manualPriceDiscount = (long)(manualAmountDiscountLong * percent);
            //产品经过所有折扣后，最终成交价
            Long priceDeal = (long)Math.round(
                    item.getProductPrice() *
                            (item.getProductPriceDiscount() / 100F) *
                            (item.getLevelDiscount() / 100F) +
                            manualPriceDiscount
            );
            item.setManualPriceDiscount(manualPriceDiscount);//（正数价格上升、负数价格下降）
            item.setProductPriceDeal(priceDeal);
            amountFinally += priceDeal;
            nxtOrderFormProductService.update(item);
            mapOrderFormProductIdToPricePrice.put(item.getId(),priceDeal);
        }

        //修改orderForm表运费、总金额
        if (nxtOrderForm.getDeliveryCost() + manualDeliveryCostDiscountLong >= 0) {
            nxtOrderForm.setManualDeliveryCostDiscount(manualDeliveryCostDiscountLong);
        }
        nxtOrderForm.setAmountFinally(amountFinally);
        nxtOrderForm.setAmountDiscount(amountFinally-nxtOrderForm.getAmountInitial());//（正数价格上升、负数价格下降）
        nxtOrderFormService.update(nxtOrderForm);

        //修改佣金表对应记录金额
        NxtCommission nxtCommissionCondition = new NxtCommission();
        nxtCommissionCondition.setOrderFormId(nxtOrderForm.getId());
        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAll(nxtCommissionCondition);
        for (NxtCommission item : nxtCommissionList) {
            Long priceDeal = mapOrderFormProductIdToPricePrice.getOrDefault(item.getOrderFormProductId(),0L);
            Long commissionAmount = (long)Math.round(priceDeal * item.getCommissionRate() / 100F);
            item.setCommissionAmount(commissionAmount);
            nxtCommissionService.update(item);
        }

    }

}
