package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormProduct;
import com.newxton.nxtframework.struct.NxtStructOrderFormProductSku;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
@Component
public class NxtProcessOrderForm {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    public List<NxtStructOrderForm> userOrderFormList(Long userId,Long offset,Long limit,Boolean isPaid,Boolean isDelivery,Boolean isReviews) throws NxtException{

        List<NxtStructOrderForm> nxtStructOrderFormList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = new Gson();

        Map<Long,NxtStructOrderForm> mapIdToNxtStructOrderForm = new HashMap<>();

        //查询订单
        List<NxtOrderForm> orderFormList = nxtOrderFormService.queryAllByUserIdAndLimit(offset,limit,userId,isPaid,isDelivery,isReviews);

        Map<Long,NxtOrderForm> mapIdToOrderForm = new HashMap<>();
        for (NxtOrderForm orderForm : orderFormList) {
            mapIdToOrderForm.put(orderForm.getId(),orderForm);
            NxtStructOrderForm nxtStructOrderForm = new NxtStructOrderForm();
            nxtStructOrderForm.setId(orderForm.getId());
            nxtStructOrderForm.setUserId(orderForm.getUserId());
            nxtStructOrderForm.setDatelineCreate(orderForm.getDatelineCreate());
            nxtStructOrderForm.setDatelineCreateReadable(sdf.format(new Date(orderForm.getDatelineCreate())));
            nxtStructOrderForm.setSerialNum(orderForm.getSerialNum());
            nxtStructOrderForm.setAmountInitial(orderForm.getAmountInitial()/100F);
            nxtStructOrderForm.setAmountDiscount(orderForm.getAmountDiscount()/100F);
            nxtStructOrderForm.setAmountFinally(orderForm.getAmountFinally()/100F);
            nxtStructOrderForm.setDeliveryAddress(orderForm.getDeliveryAddress());
            nxtStructOrderForm.setDeliveryPerson(orderForm.getDeliveryPerson());
            nxtStructOrderForm.setDeliveryCountry(orderForm.getDeliveryCountry());
            nxtStructOrderForm.setDeliveryProvince(orderForm.getDeliveryProvince());
            nxtStructOrderForm.setDeliveryCity(orderForm.getDeliveryCity());
            nxtStructOrderForm.setDeliveryPhone(orderForm.getDeliveryPhone());
            nxtStructOrderForm.setDeliveryPostcode(orderForm.getDeliveryPostcode());
            nxtStructOrderForm.setDeliveryRemark(orderForm.getDeliveryRemark());
            nxtStructOrderForm.setDeliveryConfigName(orderForm.getDeliveryConfigName());
            nxtStructOrderForm.setDeliveryCost(orderForm.getDeliveryCost()/100F);
            nxtStructOrderForm.setPaid(orderForm.getStatusPaid() > 0);
            nxtStructOrderForm.setDelivery(orderForm.getStatusDelivery() > 0);
            nxtStructOrderForm.setReviews(orderForm.getStatusReviews() > 0);
            nxtStructOrderForm.setRefund(orderForm.getStatusRefund() > 0);
            if (orderForm.getDealPlatform() != null){
                if (orderForm.getDealPlatform().equals(0)){
                    nxtStructOrderForm.setDealPlatform("web");
                }
                else if (orderForm.getDealPlatform().equals(1)){
                    nxtStructOrderForm.setDealPlatform("ios");
                }
                else if (orderForm.getDealPlatform().equals(2)){
                    nxtStructOrderForm.setDealPlatform("android");
                }
                else if (orderForm.getDealPlatform().equals(3)){
                    nxtStructOrderForm.setDealPlatform("wx");
                }
                else {
                    nxtStructOrderForm.setDealPlatform("unknown");
                }
            }
            nxtStructOrderFormList.add(nxtStructOrderForm);
            mapIdToNxtStructOrderForm.put(nxtStructOrderForm.getId(),nxtStructOrderForm);
        }

        //查询订单相关的物品
        List<Long> orderFormIdList = new ArrayList<>();
        orderFormIdList.addAll(mapIdToOrderForm.keySet());

        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);

        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {

            NxtStructOrderFormProduct nxtStructOrderFormProduct = new NxtStructOrderFormProduct();

            nxtStructOrderFormProduct.setId(nxtOrderFormProduct.getId());
            nxtStructOrderFormProduct.setOrderFormId(nxtOrderFormProduct.getOrderFormId());
            nxtStructOrderFormProduct.setQuantity(nxtOrderFormProduct.getQuantity());
            nxtStructOrderFormProduct.setProductName(nxtOrderFormProduct.getProductName());
            nxtStructOrderFormProduct.setUnitWeight(nxtOrderFormProduct.getUnitWeight()/1000F);
            nxtStructOrderFormProduct.setUnitVolume(nxtOrderFormProduct.getUnitVolume()/1000000F);
            nxtStructOrderFormProduct.setProductPrice(nxtOrderFormProduct.getProductPrice()/100F);
            nxtStructOrderFormProduct.setProductPriceDiscount(nxtOrderFormProduct.getProductPriceDiscount()/100F);
            nxtStructOrderFormProduct.setLevelNum(nxtOrderFormProduct.getLevelNum());
            nxtStructOrderFormProduct.setLevelDiscount(nxtOrderFormProduct.getLevelDiscount()/100F);
            nxtStructOrderFormProduct.setProductPriceDeal(nxtOrderFormProduct.getProductPriceDeal()/100F);
            nxtStructOrderFormProduct.setQuantityRefund(nxtOrderFormProduct.getQuantityRefund());

            try {
                if (nxtOrderFormProduct.getProductSku() != null) {
                    List<NxtStructOrderFormProductSku> skuList = gson.fromJson(nxtOrderFormProduct.getProductSku(), new TypeToken<List<NxtStructOrderFormProductSku>>() {
                    }.getType());
                    nxtStructOrderFormProduct.setProductSku(skuList);
                }
            }
            catch (Exception e){
                throw new NxtException("订单物品suk解析错误");
            }

            //物品加入主订单
            NxtStructOrderForm nxtStructOrderForm = mapIdToNxtStructOrderForm.get(nxtOrderFormProduct.getOrderFormId());
            nxtStructOrderForm.getOrderFormProductList().add(nxtStructOrderFormProduct);

        }

        return nxtStructOrderFormList;

    }

}
