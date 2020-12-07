package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.process.NxtProcessOrderFormCreate;
import com.newxton.nxtframework.service.*;
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
public class NxtApiAdminOrderFormUpdateAddressController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/order_form/update_address", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Long deliveryCountry = jsonParam.getLong("deliveryCountry");
        Long deliveryProvince = jsonParam.getLong("deliveryProvince");
        Long deliveryCity = jsonParam.getLong("deliveryCity");
        String deliveryAddress = jsonParam.getString("deliveryAddress");
        String deliveryPerson = jsonParam.getString("deliveryPerson");
        String deliveryPhone = jsonParam.getString("deliveryPhone");
        String deliveryPostcode = jsonParam.getString("deliveryPostcode");
        String deliveryRemark = jsonParam.getString("deliveryRemark");
        Long deliveryConfigId = jsonParam.getLong("deliveryConfigId");

        if (id == null){
            return new NxtStructApiResult(53,"缺少参数：id").toMap();
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);
        if (nxtOrderForm == null){
            return new NxtStructApiResult(53,"找不到对应订单").toMap();
        }

        if (nxtOrderForm.getStatusDelivery().equals(1)){
            return new NxtStructApiResult(53,"订单已经发货,不可修改地址").toMap();
        }

        if (deliveryCountry != null && deliveryProvince != null && deliveryCity != null){
            NxtDeliveryRegion nxtDeliveryRegionCountry = nxtDeliveryRegionService.queryById(deliveryCountry);
            NxtDeliveryRegion nxtDeliveryRegionProvince = nxtDeliveryRegionService.queryById(deliveryProvince);
            NxtDeliveryRegion nxtDeliveryRegionCity = nxtDeliveryRegionService.queryById(deliveryCity);
            if (nxtDeliveryRegionCountry != null && nxtDeliveryRegionProvince != null && nxtDeliveryRegionCity != null) {
                if (nxtDeliveryRegionCountry.getId().equals(nxtDeliveryRegionProvince.getRegionPid()) &&
                        nxtDeliveryRegionProvince.getId().equals(nxtDeliveryRegionCity.getRegionPid())){
                    nxtOrderForm.setDeliveryCountry(nxtDeliveryRegionCountry.getRegionName());
                    nxtOrderForm.setDeliveryProvince(nxtDeliveryRegionProvince.getRegionName());
                    nxtOrderForm.setDeliveryCity(nxtDeliveryRegionCity.getRegionName());
                }
                else {
                    return new NxtStructApiResult(53,"国家、省份、城市错误").toMap();
                }
            }
            else {
                //不选不改
                //return new NxtStructApiResult(53,"国家、省份、城市之一找不到").toMap();
            }
        }

        NxtDeliveryConfig nxtDeliveryConfig = nxtDeliveryConfigService.queryById(deliveryConfigId);

        if (nxtDeliveryConfig != null){
            nxtOrderForm.setDeliveryConfigName(nxtDeliveryConfig.getName());
        }

        nxtOrderForm.setDeliveryAddress(deliveryAddress);
        nxtOrderForm.setDeliveryPerson(deliveryPerson);
        nxtOrderForm.setDeliveryPhone(deliveryPhone);
        nxtOrderForm.setDeliveryPostcode(deliveryPostcode);
        nxtOrderForm.setDeliveryRemark(deliveryRemark);

        nxtOrderFormService.update(nxtOrderForm);

        return new NxtStructApiResult().toMap();

    }

}
