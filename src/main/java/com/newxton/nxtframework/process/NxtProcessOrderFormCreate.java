package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * Process - 创建订单、计算运费、根据折扣计算实际成交价、根据成交价分配佣金（包括三级分销）
 */
@Component
public class NxtProcessOrderFormCreate {

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

    @Resource
    private NxtShoppingCartProductService nxtShoppingCartProductService;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductSkuValuePriceEtcService nxtProductSkuValuePriceEtcService;

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Resource
    private NxtProcessDeliveryConfig nxtProcessDeliveryConfig;

    @Resource
    private NxtProcessShoppingCart nxtProcessShoppingCart;

    @Transactional(rollbackFor=Exception.class)
    public Long exec(NxtStructOrderFromCreate nxtStructOrderFromCreate, Long userId) throws NxtException{

        if (nxtStructOrderFromCreate.deliveryCountry == null){
            throw new NxtException("缺少国家");
        }
        if (nxtStructOrderFromCreate.deliveryProvince == null){
            throw new NxtException("缺少省份");
        }
        if (nxtStructOrderFromCreate.deliveryCity == null){
            throw new NxtException("缺少城市");
        }
        if (nxtStructOrderFromCreate.deliveryAddress == null || nxtStructOrderFromCreate.deliveryAddress.isEmpty()){
            throw new NxtException("缺少详细地址");
        }
        if (nxtStructOrderFromCreate.deliveryPerson == null || nxtStructOrderFromCreate.deliveryPerson.isEmpty()){
            throw new NxtException("缺少收件人");
        }
        if (nxtStructOrderFromCreate.deliveryPhone == null || nxtStructOrderFromCreate.deliveryPhone.isEmpty()){
            throw new NxtException("缺少联系电话");
        }
        if (nxtStructOrderFromCreate.deliveryPostcode == null || nxtStructOrderFromCreate.deliveryPostcode.isEmpty()){
            throw new NxtException("缺少邮编");
        }
        if (nxtStructOrderFromCreate.dealPlatform == null){
            throw new NxtException("缺少参数：成交平台（0:web 1:ios 2:android 3:wx ）");
        }

        //查询购物车商品数据
        NxtShoppingCart nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);

        if (nxtShoppingCart == null){
            throw new NxtException("找不到该用户的购物车");
        }

        List<Long> productIdList = new ArrayList<>();
        Integer countCartProductInEffect = 0;
        NxtStructShoppingCart nxtStructShoppingCart = nxtProcessShoppingCart.allDetail(nxtShoppingCart);
        for (NxtStructShoppingCartProduct cartProduct :
                nxtStructShoppingCart.getItemList()) {
            if (cartProduct.getSelected() && !cartProduct.getInvalid()){
                countCartProductInEffect ++;
            }
            productIdList.add(cartProduct.getProductId());
        }
        if (countCartProductInEffect.equals(0)){
            throw new NxtException("购物车内没有选中有效的产品");
        }

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


        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        Long userLevelDiscount = 100L;
        //查询用户等级对应折扣
        NxtUserLevel nxtUserLevel = nxtUserLevelService.queryByNum(user.getLevelNum());
        if (nxtUserLevel != null){
            userLevelDiscount = nxtUserLevel.getDiscount();
        }


        Gson gson = new Gson();

        //nxt_order_form入库
        NxtOrderForm nxtOrderForm = new NxtOrderForm();
        nxtOrderForm.setUserId(userId);
        nxtOrderForm.setDatelineCreate(System.currentTimeMillis());
        nxtOrderForm.setSerialNum(this.createSerialNum(nxtOrderForm.getDatelineCreate()));//订单编号

        nxtOrderForm.setDeliveryRemark(nxtStructOrderFromCreate.deliveryRemark);

        //国家name
        NxtDeliveryRegion deliveryRegionCountry = nxtDeliveryRegionService.queryById(nxtStructOrderFromCreate.getDeliveryCountry());
        if (deliveryRegionCountry != null) {
            nxtOrderForm.setDeliveryCountry(deliveryRegionCountry.getRegionName());
        }
        //省份name
        NxtDeliveryRegion deliveryRegionProvince = nxtDeliveryRegionService.queryById(nxtStructOrderFromCreate.getDeliveryProvince());
        if (deliveryRegionProvince != null) {
            nxtOrderForm.setDeliveryProvince(deliveryRegionProvince.getRegionName());
        }
        //城市name
        NxtDeliveryRegion deliveryRegionCity = nxtDeliveryRegionService.queryById(nxtStructOrderFromCreate.getDeliveryCity());
        if (deliveryRegionCity != null) {
            nxtOrderForm.setDeliveryCity(deliveryRegionCity.getRegionName());
        }

        if (deliveryRegionCountry == null || deliveryRegionProvince == null || deliveryRegionCity == null){
            throw new NxtException("请选择国家、省份、城市");
        }

        nxtOrderForm.setDeliveryAddress(nxtStructOrderFromCreate.getDeliveryAddress());
        nxtOrderForm.setDeliveryPerson(nxtStructOrderFromCreate.getDeliveryPerson());
        nxtOrderForm.setDeliveryPhone(nxtStructOrderFromCreate.getDeliveryPhone());
        nxtOrderForm.setDeliveryPostcode(nxtStructOrderFromCreate.getDeliveryPostcode());

        nxtOrderForm.setStatusPaid(0);//是否已经支付（0：未支付 1:已支付 -1:支付失败）
        nxtOrderForm.setStatusDelivery(0);//发货状态（0:未发货 1:已发货）
        nxtOrderForm.setStatusReviews(0);//0:未评价 1:已评价
        nxtOrderForm.setStatusRefund(0);//退货退款（0:未退货退款，1:出现退货退款情况）（情况具体要见order_form_refund表）
        nxtOrderForm.setDealPlatform(nxtStructOrderFromCreate.getDealPlatform());//成交平台（0:web 1:ios 2:android 3:wx ）可扩展其它数字

        //计算运费
        Long deliveryCost = calculateDeliveryCost(
                nxtShoppingCart,
                nxtStructOrderFromCreate.deliveryCountry,
                nxtStructOrderFromCreate.deliveryProvince,
                nxtStructOrderFromCreate.deliveryCity);

        nxtOrderForm.setDeliveryCost(deliveryCost);

        nxtOrderFormService.insert(nxtOrderForm);

        Long amountInitial = 0L;//不打折总价
        Long amountDiscount = 0L;//折扣金额
        Long amountFinally = 0L;//打折总价

        NxtStructSettingCommission nxtStructSettingCommission = nxtGlobalSettingComponent.getNxtStructSettingCommission();

        //nxt_order_form_product入库，且计算价格
        for (NxtStructShoppingCartProduct nxtStructShoppingCartProduct :
                nxtStructShoppingCart.getItemList()) {

            if (nxtStructShoppingCartProduct.getInvalid() || !nxtStructShoppingCartProduct.getSelected()){
                continue;
            }

            //先查询产品
            NxtProduct nxtProduct = nxtProductService.queryById(nxtStructShoppingCartProduct.getProductId());

            if (nxtProduct != null){
                //累加销量 & 减库存
                nxtProduct.setSalsCount(nxtProduct.getSalsCount() == null ? 1 : nxtProduct.getSalsCount() + 1);
                if (nxtProduct.getWithSku() != null && nxtProduct.getWithSku().equals(1)){
                    //减sku库存
                    this.reduceProductInventoryQuantityBySku(nxtStructShoppingCartProduct.getSku(),nxtProductSkuList,nxtProductSkuValueList,nxtProductSkuValuePriceEtcList);
                }
                else {
                    if (nxtProduct.getInventoryQuantity() != null && nxtProduct.getInventoryQuantity() > 0){
                        nxtProduct.setInventoryQuantity(nxtProduct.getInventoryQuantity()-1);
                    }
                }
                nxtProductService.update(nxtProduct);



                NxtOrderFormProduct nxtOrderFormProduct = new NxtOrderFormProduct();
                nxtOrderFormProduct.setOrderFormId(nxtOrderForm.getId());
                nxtOrderFormProduct.setProductId(nxtStructShoppingCartProduct.getProductId());
                nxtOrderFormProduct.setQuantity(nxtStructShoppingCartProduct.getQuantity());
                nxtOrderFormProduct.setProductName(nxtProduct.getProductName());

                //查询主图
                NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
                nxtProductPictureCondition.setProductId(nxtProduct.getId());
                List<NxtProductPicture> nxtProductPictureList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
                if (nxtProductPictureList.size() > 0){
                    NxtProductPicture nxtProductPicture = nxtProductPictureList.get(0);
                    nxtOrderFormProduct.setProductPicUploadfileId(nxtProductPicture.getUploadfileId());
                }

                if (nxtProduct.getWithSku() > 0){
                    //查询sku对应的产品价格、折扣
                    if (nxtStructShoppingCartProduct.getSku().size() > 0){
                        NxtProductSkuValuePriceEtc skuValuePriceEtc = this.querySkuValuePriceEtc(nxtStructShoppingCartProduct.getSku(),nxtProductSkuList,nxtProductSkuValueList,nxtProductSkuValuePriceEtcList);
                        if (skuValuePriceEtc != null) {
                            nxtOrderFormProduct.setProductPrice(skuValuePriceEtc.getSkuValuePrice());
                            nxtOrderFormProduct.setProductPriceDiscount(skuValuePriceEtc.getSkuValuePriceDiscount());
                        }
                        else {
                            throw new NxtException("购物车物品sku与当前产品最新的sku不一致，请移除购物车物品后重新添加");
                        }
                    }
                    else {
                        throw new NxtException("购物车的物品缺少sku信息");
                    }
                    nxtOrderFormProduct.setProductSku(gson.toJson(nxtStructShoppingCartProduct.getSku()));//json
                }
                else {
                    //直接得到产品价格、折扣
                    nxtOrderFormProduct.setProductPrice(nxtProduct.getPrice());
                    nxtOrderFormProduct.setProductPriceDiscount(nxtProduct.getPriceDiscount());
                }

                nxtOrderFormProduct.setLevelNum(user.getLevelNum());
                nxtOrderFormProduct.setLevelDiscount(userLevelDiscount);

                //产品经过所有折扣后，最终成交价
                Long priceDeal = (long)Math.round(
                        nxtOrderFormProduct.getProductPrice() *
                        (nxtOrderFormProduct.getProductPriceDiscount() / 100F) *
                        (nxtOrderFormProduct.getLevelDiscount() / 100F)
                );
                nxtOrderFormProduct.setProductPriceDeal(priceDeal);

                nxtOrderFormProduct.setQuantityRefund(0L);

                nxtOrderFormProductService.insert(nxtOrderFormProduct);

                if (user.getInviterUserId() != null &&
                        nxtStructSettingCommission.getOnoff() &&
                        user.getDatelineCreate() != null &&
                        System.currentTimeMillis() - user.getDatelineCreate() < nxtStructSettingCommission.getInviterExpirationDays() * 86400000L
                ) {

                    //待瓜分的佣金占产品成交价的比率（数据库）
                    Long commissionRate = nxtProduct.getCommissionRate();

                    if (commissionRate == null){
                        commissionRate = nxtStructSettingCommission.getDefaultProductCommissionRate();
                    }

                    //佣金比率
                    nxtOrderFormProduct.setCommissionRate(commissionRate);
                    //更新佣金比率
                    nxtOrderFormProductService.update(nxtOrderFormProduct);

                    //三个分销佣金等级配置
                    Long commissionRateLevel1 = nxtStructSettingCommission.getCommissionRateLevel1();
                    Long commissionRateLevel2 = nxtStructSettingCommission.getCommissionRateLevel2();
                    Long commissionRateLevel3 = nxtStructSettingCommission.getCommissionRateLevel3();

                    NxtUser inviterUser1 = nxtUserService.queryById(user.getInviterUserId());

                    //佣金分配
                    if (inviterUser1 != null) {

                        //1、一级分销佣金

                        //计入佣金表
                        NxtCommission nxtCommission1 = new NxtCommission();
                        nxtCommission1.setUserId(inviterUser1.getId());
                        nxtCommission1.setInviterLevel(1);//一级分销
                        nxtCommission1.setOrderFormId(nxtOrderForm.getId());
                        nxtCommission1.setOrderFormProductId(nxtOrderFormProduct.getId());


                        Long userCommissionRate1 = (long)Math.round(commissionRate * (commissionRateLevel1 / 100F));
                        nxtCommission1.setCommissionRate(userCommissionRate1);

                        Long commissionAmount1 = (long)Math.round(priceDeal * userCommissionRate1 / 100F);
                        nxtCommission1.setCommissionAmount(commissionAmount1);

                        nxtCommission1.setDatelineCreate(nxtOrderForm.getDatelineCreate());

                        nxtCommission1.setQuantityRefund(0L);
                        nxtCommission1.setQuantityDeal(nxtOrderFormProduct.getQuantity());

                        nxtCommission1.setIsPaid(0);
                        nxtCommission1.setIsTransfer(0);

                        if (inviterUser1.getStatus().equals(0)) {
                            nxtCommissionService.insert(nxtCommission1);
                        }


                        //2、二级分销佣金
                        NxtUser inviterUser2 = nxtUserService.queryById(inviterUser1.getInviterUserId());
                        if (inviterUser2 != null){

                            //计入佣金表
                            NxtCommission nxtCommission2 = new NxtCommission();
                            nxtCommission2.setUserId(inviterUser2.getId());
                            nxtCommission2.setInviterLevel(2);//二级分销
                            nxtCommission2.setOrderFormId(nxtOrderForm.getId());
                            nxtCommission2.setOrderFormProductId(nxtOrderFormProduct.getId());

                            Long userCommissionRate2 = (long)Math.round(commissionRate * (commissionRateLevel2/100F));
                            nxtCommission2.setCommissionRate(userCommissionRate2);

                            Long commissionAmount2 = (long)Math.round(priceDeal * userCommissionRate2 / 100F);
                            nxtCommission2.setCommissionAmount(commissionAmount2);

                            nxtCommission2.setDatelineCreate(nxtOrderForm.getDatelineCreate());

                            nxtCommission2.setQuantityRefund(0L);
                            nxtCommission2.setQuantityDeal(nxtOrderFormProduct.getQuantity());

                            nxtCommission2.setIsPaid(0);
                            nxtCommission2.setIsTransfer(0);

                            if (inviterUser2.getStatus().equals(0)) {
                                nxtCommissionService.insert(nxtCommission2);
                            }


                            //3、三级分销佣金
                            NxtUser inviterUser3 = nxtUserService.queryById(inviterUser2.getInviterUserId());
                            if (inviterUser3 != null){

                                //计入佣金表
                                NxtCommission nxtCommission3 = new NxtCommission();
                                nxtCommission3.setUserId(inviterUser3.getId());
                                nxtCommission3.setInviterLevel(3);//二级分销
                                nxtCommission3.setOrderFormId(nxtOrderForm.getId());
                                nxtCommission3.setOrderFormProductId(nxtOrderFormProduct.getId());

                                Long userCommissionRate3 = (long)Math.round(commissionRate * (commissionRateLevel3/100F));
                                nxtCommission3.setCommissionRate(userCommissionRate3);

                                Long commissionAmount3 = (long)Math.round(priceDeal * userCommissionRate3 / 100F);
                                nxtCommission3.setCommissionAmount(commissionAmount3 * nxtOrderFormProduct.getQuantity());

                                nxtCommission3.setDatelineCreate(nxtOrderForm.getDatelineCreate());

                                nxtCommission3.setQuantityRefund(0L);
                                nxtCommission3.setQuantityDeal(nxtOrderFormProduct.getQuantity());

                                nxtCommission3.setIsPaid(0);
                                nxtCommission3.setIsTransfer(0);

                                if (inviterUser3.getStatus().equals(0)) {
                                    nxtCommissionService.insert(nxtCommission3);
                                }

                            }

                        }

                    }

                }

                amountInitial = amountInitial + nxtOrderFormProduct.getProductPrice() * nxtOrderFormProduct.getQuantity();
                amountFinally = amountFinally + priceDeal  * nxtOrderFormProduct.getQuantity();

            }

        }

        amountDiscount = amountFinally - amountInitial;//amountDiscount 负数减价、正数加价

        //再更新nxt_order_form订单价格
        nxtOrderForm.setAmountDiscount(amountDiscount);
        nxtOrderForm.setAmountFinally(amountFinally);
        nxtOrderForm.setAmountInitial(amountInitial);
        nxtOrderFormService.update(nxtOrderForm);


        //最最后，才删除购物车选中的产品
        for (NxtStructShoppingCartProduct nxtStructShoppingCartProduct : nxtStructShoppingCart.getItemList()) {
            nxtShoppingCartProductService.deleteById(nxtStructShoppingCartProduct.getId());
        }

        return nxtOrderForm.getId();
    }

    /**
     * 查找sku的价格
     * @param skuList
     * @param nxtProductSkuList
     * @param nxtProductSkuValueList
     * @param nxtProductSkuValuePriceEtcList
     * @return
     */
    private NxtProductSkuValuePriceEtc querySkuValuePriceEtc(List<NxtStructShoppingCartProductSku> skuList,List<NxtProductSku> nxtProductSkuList, List<NxtProductSkuValue> nxtProductSkuValueList, List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList){

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
            return null;
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
                return priceEtc;
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
                return priceEtc;
            }
        }

        return null;
    }

    /**
     * 减sku的库存
     * @param skuList
     * @param nxtProductSkuList
     * @param nxtProductSkuValueList
     * @param nxtProductSkuValuePriceEtcList
     */
    private void reduceProductInventoryQuantityBySku(List<NxtStructShoppingCartProductSku> skuList,List<NxtProductSku> nxtProductSkuList, List<NxtProductSkuValue> nxtProductSkuValueList, List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList) throws NxtException{

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
                    priceEtc.setSkuValueInventoryQuantity(priceEtc.getSkuValueInventoryQuantity()-1);
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
                    priceEtc.setSkuValueInventoryQuantity(priceEtc.getSkuValueInventoryQuantity()-1);
                    nxtProductSkuValuePriceEtcService.update(priceEtc);
                    return;
                }
            }
        }

    }

    /**
     * 根据购物车，国家、省份、城市，计算运费
     * @param nxtShoppingCart
     * @param deliveryCountry
     * @param deliveryProvince
     * @param deliveryCity
     * @return
     */
    public Long calculateDeliveryCost(NxtShoppingCart nxtShoppingCart, Long deliveryCountry, Long deliveryProvince, Long deliveryCity) throws NxtException{

        //拿出所有运费模版数据
        Map<Long,NxtStructDeliveryConfig> mapDeliveryConfig = new HashMap<>();
        List<NxtDeliveryConfig> nxtDeliveryConfigList = nxtDeliveryConfigService.queryAllByLimit(0,Integer.MAX_VALUE);
        for (NxtDeliveryConfig nxtDeliveryConfig : nxtDeliveryConfigList) {
            NxtStructDeliveryConfig mapItemAllDetailG = nxtProcessDeliveryConfig.getDeliveryConfigAllDetail(nxtDeliveryConfig);
            mapDeliveryConfig.put(nxtDeliveryConfig.getId(),mapItemAllDetailG);
        }

        List<NxtStructDeliveryConfigItem> listConfigItem = new ArrayList<>();

        //各运费规则下的产品数量
        Map<Long,Long> mapConfigItemIdToProductQuantity = new HashMap<>();

        //第一步：找出产品所匹配的运费规则条目 ConfigItem

        //购物车内的已选中产品列表
        List<Long> productIdList = new ArrayList<>();
        Map<Long,Long> mapProductIdToQuantity = new HashMap<>();
        List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllSelectedProductByShoppingCartId(nxtShoppingCart.getId());
        for (NxtShoppingCartProduct cartProduct : nxtShoppingCartProductList) {
            if (cartProduct.getSelected().equals(1)) {//已选中的产品
                productIdList.add(cartProduct.getProductId());
                mapProductIdToQuantity.put(cartProduct.getProductId(), cartProduct.getQuantity());
            }
        }
        //查询产品运费模版
        Map<Long,Long> mapProductIdToConfigId = new HashMap<>();
        List<NxtProduct> nxtProductList = nxtProductService.selectByIdSet(0,Integer.MAX_VALUE,productIdList);
        for (NxtProduct nxtProduct : nxtProductList) {
            mapProductIdToConfigId.put(nxtProduct.getId(),nxtProduct.getDeliveryConfigId());
        }

        //找出产品所匹配的运费规则条目 ConfigItem
        for (Long productId : productIdList) {
            NxtStructDeliveryConfig nxtDeliveryConfig = mapDeliveryConfig.getOrDefault(mapProductIdToConfigId.getOrDefault(productId,0L),null);
            if (nxtDeliveryConfig == null){
                continue;
            }
            List<NxtStructDeliveryConfigItem> configItemList = nxtDeliveryConfig.getItemList();
            //进行匹配
            NxtStructDeliveryConfigItem configItemRight = null;
            //先匹配City
            for (NxtStructDeliveryConfigItem configItem : configItemList) {
                List<NxtStructDeliveryCofnigItemRegion> regionList = configItem.getRegionList();
                for (NxtStructDeliveryCofnigItemRegion itemRegion : regionList) {
                    if (itemRegion.getRegionId().equals(deliveryCity)){
                        configItemRight = configItem;
                    }
                }
            }
            if (configItemRight == null) {
                //再匹配Province
                for (NxtStructDeliveryConfigItem configItem : configItemList) {
                    List<NxtStructDeliveryCofnigItemRegion> regionList = configItem.getRegionList();
                    for (NxtStructDeliveryCofnigItemRegion itemRegion : regionList) {
                        if (itemRegion.getRegionId().equals(deliveryProvince)) {
                            configItemRight = configItem;
                        }
                    }
                }
            }
            if (configItemRight == null) {
                //最后，匹配Country
                for (NxtStructDeliveryConfigItem configItem : configItemList) {
                    List<NxtStructDeliveryCofnigItemRegion> regionList = configItem.getRegionList();
                    for (NxtStructDeliveryCofnigItemRegion itemRegion : regionList) {
                        if (itemRegion.getRegionId().equals(deliveryCountry)) {
                            configItemRight = configItem;
                        }
                    }
                }
            }
            if (configItemRight != null) {
                listConfigItem.add(configItemRight);
                //累加各运费规则下的产品数量
                Long quantity = mapProductIdToQuantity.get(productId);
                if (quantity != null) {
                    Long quantityOld = mapConfigItemIdToProductQuantity.getOrDefault(configItemRight.getId(),0L);
                    mapConfigItemIdToProductQuantity.put(configItemRight.getId(), quantity+quantityOld);
                }
            }
        }

        Map<Long,Float> mapAdditionPrice = new HashMap<>();
        List<Float> listAdditionPrice = new ArrayList<>();

        Float maxBillablePrice = 0F;
        //确定匹配上运费最贵的那个 ConfigItem 条目，作为起步价
        for (NxtStructDeliveryConfigItem configItem : listConfigItem) {
            maxBillablePrice = Math.max(maxBillablePrice,configItem.getBillablePrice());
            if (!mapConfigItemIdToProductQuantity.containsKey(configItem.getId())){
                continue;
            }
            Long countQuantity = mapConfigItemIdToProductQuantity.get(configItem.getId());
            //仅计算续件部分的价格
            if (countQuantity > configItem.getBillableQuantity()){
                Float cost = (countQuantity - configItem.getBillableQuantity()) / (float)configItem.getAdditionQuantity() * configItem.getAdditionPrice();
                mapAdditionPrice.put(configItem.getId(),cost);
                listAdditionPrice.add(cost);
            }
        }

        Float additionPriceTotal = 0F;
        //计算各 ConfigItem 中 的 续费总价
        for (Float addPrice : listAdditionPrice) {
            additionPriceTotal += addPrice;
        }

        //1个起步价+各个续费总价=运费
        return (long)((maxBillablePrice + additionPriceTotal)*100L);

    }

    /**
     * 生成订单号码
     * @return
     */
    private String createSerialNum(Long dateline){
        String serialNum = "NXT" + dateline + this.getRandomUppercaseLetter(6);
        return serialNum;
    }

    /**
     * 取随机大写字母
     * @param length
     * @return
     */
    public String getRandomUppercaseLetter(int length) {
        String str = "ACFHKLPQSTUVWXYZ";
        Random random = new Random();
        StringBuffer buffet = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length() - 1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }

}
