package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormDelivery;
import com.newxton.nxtframework.struct.NxtStructOrderFormProduct;
import com.newxton.nxtframework.struct.NxtStructOrderFormProductSku;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
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
    private NxtUserService nxtUserService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtOrderFormDeliveryService nxtOrderFormDeliveryService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    /**
     * 后台查询订单统计
     * @param isPaid
     * @param isDelivery
     * @param dealPlatform
     * @param datelineBegin
     * @param datelineEnd
     * @return
     * @throws NxtException
     */
    public Long adminOrderFormCount(Boolean isPaid,Boolean isDelivery,Integer dealPlatform,String datelineBegin,String datelineEnd) throws NxtException {
        Long datelineBeginLong = null;
        Long datelineEndLong = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            if (datelineBegin != null && !datelineBegin.isEmpty()) {
                date = dateFormat.parse(datelineBegin);
                datelineBeginLong = date.getTime();
            }
            if (datelineEnd != null && !datelineEnd.isEmpty()) {
                date = dateFormat.parse(datelineEnd);
                datelineEndLong = date.getTime() + 86400000L;
            }
        }
        catch (Exception e) {
            throw new NxtException("日期转化错误");
        }

        //查询订单统计
        return nxtOrderFormService.adminOrderFormCount(isPaid,isDelivery,dealPlatform,datelineBeginLong,datelineEndLong);

    }

    /**
     * 后台查询订单列表
     * @param offset
     * @param limit
     * @param isPaid
     * @param isDelivery
     * @param dealPlatform
     * @param datelineBegin 例如：2020-11-19
     * @param datelineEnd 例如：2020-11-20
     * @return
     * @throws NxtException
     */
    public List<NxtStructOrderForm> adminOrderFormList(Long offset,Long limit,Boolean isPaid,Boolean isDelivery,
                                    Integer dealPlatform,String datelineBegin,String datelineEnd) throws NxtException {

        Long datelineBeginLong = null;
        Long datelineEndLong = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            if (datelineBegin != null && !datelineBegin.isEmpty()) {
                date = dateFormat.parse(datelineBegin);
                datelineBeginLong = date.getTime();
            }
            if (datelineEnd != null && !datelineEnd.isEmpty()) {
                date = dateFormat.parse(datelineEnd);
                datelineEndLong = date.getTime() + 86400000L;
            }
        }
        catch (Exception e) {
            throw new NxtException("日期转化错误");
        }

        //查询订单
        List<NxtOrderForm> orderFormList = nxtOrderFormService.adminOrderFormList(offset,limit,isPaid,isDelivery,dealPlatform,datelineBeginLong,datelineEndLong);

        List<NxtStructOrderForm> nxtStructOrderFormList = this.assemblyStructOrderFormList(orderFormList);

        return nxtStructOrderFormList;

    }

    /**
     * 个人中心--用户查询订单列表
     * @param userId
     * @param offset
     * @param limit
     * @param isPaid
     * @param isDelivery
     * @param isReviews
     * @return
     * @throws NxtException
     */
    public List<NxtStructOrderForm> userOrderFormList(Long userId,Long offset,Long limit,Boolean isPaid,Boolean isDelivery,Boolean isReviews) throws NxtException {

        //查询订单
        List<NxtOrderForm> orderFormList = nxtOrderFormService.queryAllByUserIdAndLimit(offset,limit,userId,isPaid,isDelivery,isReviews);

        List<NxtStructOrderForm> nxtStructOrderFormList = this.assemblyStructOrderFormList(orderFormList);

        return nxtStructOrderFormList;

    }

    /**
     * 根据初步的orderFromList装配详细的结构化订单列表
     * @param orderFormList
     * @return
     * @throws NxtException
     */
    public List<NxtStructOrderForm> assemblyStructOrderFormList(List<NxtOrderForm> orderFormList) throws NxtException{

        List<NxtStructOrderForm> nxtStructOrderFormList = new ArrayList<>();

        List<NxtStructOrderFormProduct> nxtStructOrderFormProductList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = new Gson();

        Map<Long,NxtStructOrderForm> mapIdToNxtStructOrderForm = new HashMap<>();

//        //查询订单
//        List<NxtOrderForm> orderFormList = nxtOrderFormService.queryAllByUserIdAndLimit(offset,limit,userId,isPaid,isDelivery,isReviews);

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
            if (orderForm.getManualDeliveryCostDiscount() != null){
                nxtStructOrderForm.setManualDeliveryCostDiscount(orderForm.getManualDeliveryCostDiscount()/100F);
            }
            nxtStructOrderForm.setStatusText(this.getStatusText(orderForm));
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
            if (orderForm.getDatelineDelivery() != null){
                nxtStructOrderForm.setDatelineDelivery(orderForm.getDatelineDelivery());
                nxtStructOrderForm.setDatelineDeliveryReadable(sdf.format(new Date(orderForm.getDatelineDelivery())));
            }
            if (orderForm.getDatelinePaid() != null){
                nxtStructOrderForm.setDatelinePaid(orderForm.getDatelinePaid());
                nxtStructOrderForm.setDatelinePaidReadable(sdf.format(new Date(orderForm.getDatelinePaid())));
            }
            if (orderForm.getDatelineReceived() != null){
                nxtStructOrderForm.setDatelineReceived(orderForm.getDatelineReceived());
                nxtStructOrderForm.setDatelineReceivedReadable(sdf.format(new Date(orderForm.getDatelineReceived())));
            }
            //已经确认收货：Done=true
            if (orderForm.getDatelineReceived() != null){
                nxtStructOrderForm.setDone(true);
            }
            else {
                nxtStructOrderForm.setDone(false);
            }
            nxtStructOrderFormList.add(nxtStructOrderForm);
            mapIdToNxtStructOrderForm.put(nxtStructOrderForm.getId(),nxtStructOrderForm);
        }

        List<Long> productIdList = new ArrayList<>();

        //查询订单相关的物品
        List<Long> orderFormIdList = new ArrayList<>();
        orderFormIdList.addAll(mapIdToOrderForm.keySet());

        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);

        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {

            productIdList.add(nxtOrderFormProduct.getProductId());

            NxtStructOrderFormProduct nxtStructOrderFormProduct = new NxtStructOrderFormProduct();

            nxtStructOrderFormProduct.setId(nxtOrderFormProduct.getId());
            nxtStructOrderFormProduct.setProductId(nxtOrderFormProduct.getProductId());
            nxtStructOrderFormProduct.setOrderFormId(nxtOrderFormProduct.getOrderFormId());
            nxtStructOrderFormProduct.setQuantity(nxtOrderFormProduct.getQuantity());
            nxtStructOrderFormProduct.setProductName(nxtOrderFormProduct.getProductName());
            if (nxtOrderFormProduct.getUnitWeight() != null) {
                nxtStructOrderFormProduct.setUnitWeight(nxtOrderFormProduct.getUnitWeight() / 1000F);
            }
            if (nxtOrderFormProduct.getUnitVolume() != null) {
                nxtStructOrderFormProduct.setUnitVolume(nxtOrderFormProduct.getUnitVolume() / 1000000F);
            }
            nxtStructOrderFormProduct.setProductPrice(nxtOrderFormProduct.getProductPrice()/100F);
            nxtStructOrderFormProduct.setProductPriceDiscount(nxtOrderFormProduct.getProductPriceDiscount()/100F);
            if (nxtOrderFormProduct.getManualPriceDiscount() != null){
                nxtStructOrderFormProduct.setManualPriceDiscount(nxtOrderFormProduct.getManualPriceDiscount()/100F);
            }
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

            //备用列表
            nxtStructOrderFormProductList.add(nxtStructOrderFormProduct);

        }


        //批量查主图
        Map<Long,Long> mapUploadFileIdToProductId = new HashMap<>();
        List<NxtProductPicture> productPictureList =  nxtProductPictureService.selectByProductIdSet(0,Integer.MAX_VALUE,productIdList);
        for (NxtProductPicture item : productPictureList) {
            mapUploadFileIdToProductId.put(item.getUploadfileId(),item.getProductId());
        }
        List<Long> uploadFileId = new ArrayList<>();
        uploadFileId.addAll(mapUploadFileIdToProductId.keySet());
        List<NxtUploadfile> uploadfileList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,uploadFileId);

        Map<Long,String> mapProductIdToPicUrl = new HashMap<>();
        for (NxtUploadfile nxtUploadfile : uploadfileList) {
            Long productId = mapUploadFileIdToProductId.get(nxtUploadfile.getId());
            mapProductIdToPicUrl.put(productId,nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
        }

        for (NxtStructOrderFormProduct nxtStructOrderFormProduct : nxtStructOrderFormProductList){
            nxtStructOrderFormProduct.setPicUrl(mapProductIdToPicUrl.getOrDefault(nxtStructOrderFormProduct.getProductId(),"/common/images/empty.png"));
        }

        return nxtStructOrderFormList;

    }

    public NxtStructOrderForm orderFormDetail(Long id) throws NxtException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = new Gson();

        Map<Long,NxtStructOrderForm> mapIdToNxtStructOrderForm = new HashMap<>();

        NxtOrderForm orderForm = nxtOrderFormService.queryById(id);

        if (orderForm == null){
            return null;
        }

        NxtUser user = nxtUserService.queryById(orderForm.getUserId());

        NxtStructOrderForm nxtStructOrderForm = new NxtStructOrderForm();
        nxtStructOrderForm.setId(orderForm.getId());
        nxtStructOrderForm.setUserId(orderForm.getUserId());
        nxtStructOrderForm.setUsername(user.getUsername());
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
        if (orderForm.getManualDeliveryCostDiscount() != null){
            nxtStructOrderForm.setManualDeliveryCostDiscount(orderForm.getManualDeliveryCostDiscount()/100F);
        }
        nxtStructOrderForm.setStatusText(this.getStatusText(orderForm));
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
        if (orderForm.getDatelineDelivery() != null){
            nxtStructOrderForm.setDatelineDelivery(orderForm.getDatelineDelivery());
            nxtStructOrderForm.setDatelineDeliveryReadable(sdf.format(new Date(orderForm.getDatelineDelivery())));
        }
        if (orderForm.getDatelinePaid() != null){
            nxtStructOrderForm.setDatelinePaid(orderForm.getDatelinePaid());
            nxtStructOrderForm.setDatelinePaidReadable(sdf.format(new Date(orderForm.getDatelinePaid())));
        }
        if (orderForm.getDatelineReceived() != null){
            nxtStructOrderForm.setDatelineReceived(orderForm.getDatelineReceived());
            nxtStructOrderForm.setDatelineReceivedReadable(sdf.format(new Date(orderForm.getDatelineReceived())));
        }
        //发货后超过60天未退货，或者已经确认收货：Done=true
        if (orderForm.getDatelineReceived() != null){
            nxtStructOrderForm.setDone(true);
        }
        else {
            if (!(orderForm.getStatusRefund() > 0) && orderForm.getDatelineDelivery() != null &&
                    (System.currentTimeMillis() - orderForm.getDatelineDelivery() > 5184000000L)
            ){//60天
                nxtStructOrderForm.setDone(true);
            }
            else {
                nxtStructOrderForm.setDone(false);
            }
        }

        //查询订单相关的物品
        List<Long> orderFormIdList = new ArrayList<>();
        orderFormIdList.add(id);

        Map<Long,Long> orderFormProductIdToProductId = new HashMap<>();

        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);

        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {

            orderFormProductIdToProductId.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductId());

            NxtStructOrderFormProduct nxtStructOrderFormProduct = new NxtStructOrderFormProduct();

            nxtStructOrderFormProduct.setId(nxtOrderFormProduct.getId());
            nxtStructOrderFormProduct.setOrderFormId(nxtOrderFormProduct.getOrderFormId());
            nxtStructOrderFormProduct.setProductId(nxtOrderFormProduct.getProductId());
            nxtStructOrderFormProduct.setQuantity(nxtOrderFormProduct.getQuantity());
            nxtStructOrderFormProduct.setProductName(nxtOrderFormProduct.getProductName());
            if (nxtOrderFormProduct.getUnitWeight() != null) {
                nxtStructOrderFormProduct.setUnitWeight(nxtOrderFormProduct.getUnitWeight() / 1000F);
            }
            if (nxtOrderFormProduct.getUnitVolume() != null) {
                nxtStructOrderFormProduct.setUnitVolume(nxtOrderFormProduct.getUnitVolume() / 1000000F);
            }
            nxtStructOrderFormProduct.setProductPrice(nxtOrderFormProduct.getProductPrice()/100F);
            nxtStructOrderFormProduct.setProductPriceDiscount(nxtOrderFormProduct.getProductPriceDiscount()/100F);
            if (nxtOrderFormProduct.getManualPriceDiscount() != null){
                nxtStructOrderFormProduct.setManualPriceDiscount(nxtOrderFormProduct.getManualPriceDiscount()/100F);
            }
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
            nxtStructOrderForm.getOrderFormProductList().add(nxtStructOrderFormProduct);

        }

        //物流配送，查询发货信息
        NxtOrderFormDelivery nxtOrderFormDelivery = nxtOrderFormDeliveryService.queryShippingByOrderFormId(id);

        if (nxtOrderFormDelivery != null) {
            NxtStructOrderFormDelivery nxtStructOrderFormDelivery = new NxtStructOrderFormDelivery();
            nxtStructOrderFormDelivery.setId(nxtOrderFormDelivery.getId());
            nxtStructOrderFormDelivery.setOrderFormId(nxtOrderFormDelivery.getOrderFormId());
            nxtStructOrderFormDelivery.setDeliveryCompanyId(nxtOrderFormDelivery.getDeliveryCompanyId());
            nxtStructOrderFormDelivery.setDeliveryCompanyName(nxtOrderFormDelivery.getDeliveryCompanyName());
            nxtStructOrderFormDelivery.setDeliverySerialNum(nxtOrderFormDelivery.getDeliverySerialNum());

            nxtStructOrderForm.setOrderFormDelivery(nxtStructOrderFormDelivery);
        }

        //批量查主图
        List<Long> productIdList = new ArrayList<>();
        productIdList.addAll(orderFormProductIdToProductId.values());
        Map<Long,Long> mapUploadFileIdToProductId = new HashMap<>();
        List<NxtProductPicture> productPictureList =  nxtProductPictureService.selectByProductIdSet(0,Integer.MAX_VALUE,productIdList);
        for (NxtProductPicture item : productPictureList) {
            mapUploadFileIdToProductId.put(item.getUploadfileId(),item.getProductId());
        }
        List<Long> uploadFileId = new ArrayList<>();
        uploadFileId.addAll(mapUploadFileIdToProductId.keySet());
        List<NxtUploadfile> uploadfileList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,uploadFileId);

        Map<Long,String> mapProductIdToPicUrl = new HashMap<>();
        for (NxtUploadfile nxtUploadfile : uploadfileList) {
            Long productId = mapUploadFileIdToProductId.get(nxtUploadfile.getId());
            mapProductIdToPicUrl.put(productId,nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
        }

        for (NxtStructOrderFormProduct nxtStructOrderFormProduct : nxtStructOrderForm.getOrderFormProductList()){
            nxtStructOrderFormProduct.setPicUrl(mapProductIdToPicUrl.getOrDefault(nxtStructOrderFormProduct.getProductId(),"/common/images/empty.png"));
        }

        return nxtStructOrderForm;

    }

    public String getStatusText(NxtOrderForm nxtOrderForm){

        if (nxtOrderForm.getStatusRefund() > 0){
            return "已申请售后";
        }
        if (nxtOrderForm.getStatusReviews() > 0){
            return "已评价";
        }
        if (nxtOrderForm.getDatelineReceived() != null){
            return "已确认收货";
        }
        if (nxtOrderForm.getStatusDelivery() > 0){
            return "已发货";
        }
        if (nxtOrderForm.getStatusPaid() > 0){
            return "已付款";
        }
        else {
            return "订单付款";
        }
    }

}
