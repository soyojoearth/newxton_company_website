package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormDelivery;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
import com.newxton.nxtframework.service.NxtOrderFormDeliveryService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormUpdateDeliveryController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormDeliveryService nxtOrderFormDeliveryService;

    @Resource
    private NxtDeliveryCompanyService nxtDeliveryCompanyService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/order_form/update_delivery", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Long deliveryCompanyId = jsonParam.getLong("deliveryCompanyId");
        String deliverySerialNum = jsonParam.getString("deliverySerialNum");

        if (id == null){
            return new NxtStructApiResult(53,"缺少参数：id").toMap();
        }
        if (deliveryCompanyId == null){
            return new NxtStructApiResult(53,"请选择快递公司").toMap();
        }
        if (deliverySerialNum == null){
            return new NxtStructApiResult(53,"请填写运单号码").toMap();
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);
        if (nxtOrderForm == null){
            return new NxtStructApiResult(53,"找不到对应订单").toMap();
        }

        if (!nxtOrderForm.getStatusPaid().equals(1)){
            return new NxtStructApiResult(53,"订单还没有支付").toMap();
        }

        NxtDeliveryCompany nxtDeliveryCompany = nxtDeliveryCompanyService.queryById(deliveryCompanyId);
        if (nxtDeliveryCompany == null){
            return new NxtStructApiResult(53,"找不到对应快递公司").toMap();
        }

        NxtOrderFormDelivery nxtOrderFormDelivery = nxtOrderFormDeliveryService.queryShippingByOrderFormId(nxtOrderForm.getId());
        if (nxtOrderFormDelivery == null){
            nxtOrderFormDelivery = new NxtOrderFormDelivery();
            nxtOrderFormDelivery.setOrderFormId(nxtOrderForm.getId());
        }
        nxtOrderFormDelivery.setDeliveryCompanyId(nxtDeliveryCompany.getId());
        nxtOrderFormDelivery.setDeliveryCompanyName(nxtDeliveryCompany.getName());
        nxtOrderFormDelivery.setDeliverySerialNum(deliverySerialNum);

        if (nxtOrderFormDelivery.getId() == null){
            nxtOrderFormDeliveryService.insert(nxtOrderFormDelivery);
        }
        else {
            nxtOrderFormDeliveryService.update(nxtOrderFormDelivery);
        }

        nxtOrderForm.setStatusDelivery(1);
        nxtOrderFormService.update(nxtOrderForm);

        return new NxtStructApiResult().toMap();

    }

}
