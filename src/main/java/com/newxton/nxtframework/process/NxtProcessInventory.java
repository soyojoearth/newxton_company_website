package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructSettingEcConfig;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProductSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 减库存
 */
@Component
public class NxtProcessInventory {

    private Logger logger = LoggerFactory.getLogger(NxtProcessInventory.class);

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductSkuValuePriceEtcService nxtProductSkuValuePriceEtcService;

    public void reduceProductInventoryQuantity(NxtOrderForm nxtOrderForm,NxtStructSettingEcConfig settingEcConfig){
        //1 下单减库存 2 付款减库存
        if (settingEcConfig.getInventoryUpdateType().equals(1)){
            if (nxtOrderForm.getStatusPaid().equals(0)){//是否已经支付（0：未支付 1:已支付 -1:支付失败）
                //减库存
                this.doProcess(nxtOrderForm);
            }
        }
        if (settingEcConfig.getInventoryUpdateType().equals(2)){
            if (nxtOrderForm.getStatusPaid().equals(1)){//是否已经支付（0：未支付 1:已支付 -1:支付失败）
                //减库存
                this.doProcess(nxtOrderForm);
            }
        }
    }

    private void doProcess(NxtOrderForm orderForm){

        List<Long> orderFormIdList = new ArrayList<>();
        orderFormIdList.add(orderForm.getId());
        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);

        List<Long> productIdList = new ArrayList<>();
        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {
            productIdList.add(nxtOrderFormProduct.getProductId());
        }

        List<NxtProduct> nxtProductList = nxtProductService.selectByIdSet(0,Integer.MAX_VALUE,productIdList);

        //先准备好sku列表数据
        List<NxtProductSku> nxtProductSkuList = nxtProductSkuService.selectByProductIdSet(productIdList);
        List<Long> skuIdList = new ArrayList<>();
        for (NxtProductSku nxtProductSku : nxtProductSkuList) {
            skuIdList.add(nxtProductSku.getId());
        }
        List<NxtProductSkuValue> nxtProductSkuValueList = nxtProductSkuValueService.selectBySkuIdSet(0,Integer.MAX_VALUE,skuIdList);
        List<Long> skuValueIdList = new ArrayList<>();
        for (NxtProductSkuValue nxtProductSkuValue : nxtProductSkuValueList) {
            skuValueIdList.add(nxtProductSkuValue.getId());
        }
        List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList = nxtProductSkuValuePriceEtcService.selectByValueIdSet(0,Integer.MAX_VALUE,skuValueIdList);
        //准备好了
        Map<Long,NxtProduct> mapProduct = new HashMap<>();
        for (NxtProduct nxtProduct : nxtProductList) {
            mapProduct.put(nxtProduct.getId(),nxtProduct);
        }

        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {
            NxtProduct nxtProduct = mapProduct.getOrDefault(nxtOrderFormProduct.getProductId(),null);
            if (nxtProduct == null){
                continue;
            }
            if (nxtProduct.getWithSku() != null && nxtProduct.getWithSku().equals(1)) {
                //减sku库存
                List<NxtStructShoppingCartProductSku> nxtStructShoppingCartProductSkuList = null;
                try {
                    nxtStructShoppingCartProductSkuList = new Gson().fromJson(nxtOrderFormProduct.getProductSku(), new TypeToken<List<NxtStructShoppingCartProductSku>>(){}.getType());
                    if (nxtStructShoppingCartProductSkuList != null) {
                        this.reduceProductInventoryQuantityBySku(nxtOrderFormProduct,nxtStructShoppingCartProductSkuList, nxtProductSkuList, nxtProductSkuValueList, nxtProductSkuValuePriceEtcList);
                    }
                }
                catch (Exception e){
                    //减库存失败，没关系
                    logger.info("sku减库存失败，订单id："+orderForm.getId());
                }
            }
            //总库存也是要同时减的
            if (nxtProduct.getInventoryQuantity() != null && nxtProduct.getInventoryQuantity() > 0) {
                nxtProduct.setInventoryQuantity(nxtProduct.getInventoryQuantity() - nxtOrderFormProduct.getQuantity());
                nxtProductService.update(nxtProduct);
            }
        }

    }


    /**
     * 减sku的库存
     * @param skuList
     * @param nxtProductSkuList
     * @param nxtProductSkuValueList
     * @param nxtProductSkuValuePriceEtcList
     */
    public void reduceProductInventoryQuantityBySku(NxtOrderFormProduct nxtOrderFormProduct, List<NxtStructShoppingCartProductSku> skuList, List<NxtProductSku> nxtProductSkuList, List<NxtProductSkuValue> nxtProductSkuValueList, List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList) throws NxtException {

        Long skuValueId1 = null;
        Long skuValueId2 = null;

        for (NxtStructShoppingCartProductSku sku :
                skuList) {
            for (NxtProductSku productSku : nxtProductSkuList) {
                if (sku.getSkuKeyName().equals(productSku.getSkuKeyName())){
                    for (NxtProductSkuValue skuValue : nxtProductSkuValueList){
                        if (productSku.getId().equals(skuValue.getSkuId()) && skuValue.getSkuValueName().equals(sku.getSkuValueName())){
                            if (skuValueId1 == null) {
                                skuValueId1 = skuValue.getId();
                            }
                            else if (skuValueId2 == null){
                                skuValueId2 = skuValue.getId();
                            }
                        }
                    }
                }
            }
        }

        if (skuValueId1 == null && skuValueId2 == null){
            throw new NxtException("sku减库存出错");
        }

        for (NxtProductSkuValuePriceEtc priceEtc : nxtProductSkuValuePriceEtcList){
            //双sku
            if (
                    (
                            skuValueId1 != null && priceEtc.getSkuValueId1().equals(skuValueId1) &&
                                    skuValueId2 != null && priceEtc.getSkuValueId2().equals(skuValueId2)
                    )
                            ||
                            (
                                    skuValueId2 != null && priceEtc.getSkuValueId1().equals(skuValueId2) &&
                                            skuValueId1 != null && priceEtc.getSkuValueId2().equals(skuValueId1)
                            )
            ){
                if (priceEtc.getSkuValueInventoryQuantity() != null && priceEtc.getSkuValueInventoryQuantity() > 0) {
                    priceEtc.setSkuValueInventoryQuantity(priceEtc.getSkuValueInventoryQuantity()-nxtOrderFormProduct.getQuantity());
                    nxtProductSkuValuePriceEtcService.update(priceEtc);
                    return;
                }
            }
        }

        for (NxtProductSkuValuePriceEtc priceEtc : nxtProductSkuValuePriceEtcList){
            //单sku
            if (
                    (
                            skuValueId1 != null && priceEtc.getSkuValueId1().equals(skuValueId1) ||
                                    skuValueId2 != null && priceEtc.getSkuValueId2().equals(skuValueId2)
                    )
                            ||
                            (
                                    skuValueId2 != null && priceEtc.getSkuValueId1().equals(skuValueId2) ||
                                            skuValueId1 != null && priceEtc.getSkuValueId2().equals(skuValueId1)
                            )
            ){
                if (priceEtc.getSkuValueInventoryQuantity() != null && priceEtc.getSkuValueInventoryQuantity() > 0) {
                    priceEtc.setSkuValueInventoryQuantity(priceEtc.getSkuValueInventoryQuantity()-nxtOrderFormProduct.getQuantity());
                    nxtProductSkuValuePriceEtcService.update(priceEtc);
                    return;
                }
            }
        }

    }

}
