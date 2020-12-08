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
        if (nxtStructOrderFromCreate.deliveryConfigId == null){
            throw new NxtException("缺少配送方式");
        }
        if (nxtStructOrderFromCreate.dealPlatform == null){
            throw new NxtException("缺少参数：成交平台（0:web 1:ios 2:android 3:wx ）");
        }

        //查询购物车商品数据
        NxtShoppingCart nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);

        if (nxtShoppingCart == null){
            throw new NxtException("找不到该用户的购物车");
        }

        //查询用户
        NxtUser user = nxtUserService.queryById(userId);

        Long userLevelDiscount = 100L;
        //查询用户等级对应折扣
        NxtUserLevel nxtUserLevel = nxtUserLevelService.queryByNum(user.getLevelNum());
        if (nxtUserLevel != null){
            userLevelDiscount = nxtUserLevel.getDiscount();
        }

        //购物车内的已选中产品列表
        List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllSelectedProductByShoppingCartId(nxtShoppingCart.getId());

        if (nxtShoppingCartProductList.size() == 0){
            throw new NxtException("购物车内没有选中的产品");
        }

        NxtDeliveryConfig nxtDeliveryConfig = nxtDeliveryConfigService.queryById(nxtStructOrderFromCreate.deliveryConfigId);

        if (nxtDeliveryConfig == null){
            throw new NxtException("运费模版不存在");
        }

        //nxt_order_form入库
        NxtOrderForm nxtOrderForm = new NxtOrderForm();
        nxtOrderForm.setUserId(userId);
        nxtOrderForm.setDatelineCreate(System.currentTimeMillis());
        nxtOrderForm.setSerialNum(this.createSerialNum(nxtOrderForm.getDatelineCreate()));//订单编号

        nxtOrderForm.setDeliveryConfigName(nxtDeliveryConfig.getName());

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
                nxtStructOrderFromCreate.getDeliveryConfigId(),
                nxtStructOrderFromCreate.deliveryCountry,
                nxtStructOrderFromCreate.deliveryProvince,
                nxtStructOrderFromCreate.deliveryCity);

        nxtOrderForm.setDeliveryCost(deliveryCost);

        nxtOrderFormService.insert(nxtOrderForm);

        Long amountInitial = 0L;//不打折总价
        Long amountDiscount = 0L;//折扣金额
        Long amountFinally = 0L;//打折总价

        Long countWeight = null;
        Long countVolume = null;

        NxtStructSettingCommission nxtStructSettingCommission = nxtGlobalSettingComponent.getNxtStructSettingCommission();

        //nxt_order_form_product入库，且计算价格
        for (NxtShoppingCartProduct nxtShoppingCartProduct :
                nxtShoppingCartProductList) {

            //先查询产品
            NxtProduct nxtProduct = nxtProductService.queryById(nxtShoppingCartProduct.getProductId());

            if (nxtProduct != null){

                NxtOrderFormProduct nxtOrderFormProduct = new NxtOrderFormProduct();
                nxtOrderFormProduct.setOrderFormId(nxtOrderForm.getId());
                nxtOrderFormProduct.setProductId(nxtShoppingCartProduct.getProductId());
                nxtOrderFormProduct.setQuantity(nxtShoppingCartProduct.getQuantity());
                nxtOrderFormProduct.setProductName(nxtProduct.getProductName());

                //查询主图
                NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
                nxtProductPictureCondition.setProductId(nxtProduct.getId());
                List<NxtProductPicture> nxtProductPictureList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
                if (nxtProductPictureList.size() > 0){
                    NxtProductPicture nxtProductPicture = nxtProductPictureList.get(0);
                    nxtOrderFormProduct.setProductPicUploadfileId(nxtProductPicture.getUploadfileId());
                }

                if (nxtProduct.getUnitVolume() != null){
                    nxtOrderFormProduct.setUnitVolume(nxtProduct.getUnitVolume());
                    if (countVolume == null){
                        countVolume = 0L;
                    }
                    countVolume += nxtProduct.getUnitVolume();//累加体积
                }
                if (nxtProduct.getUnitWeight() != null){
                    nxtOrderFormProduct.setUnitWeight(nxtProduct.getUnitWeight());
                    if (countWeight == null){
                        countWeight = 0L;
                    }
                    countWeight += nxtProduct.getUnitWeight();//累加重量
                }

                if (nxtProduct.getWithSku() > 0){
                    //查询sku对应的产品价格、折扣
                    if (nxtShoppingCartProduct.getSku() != null){
                        NxtProductSkuValuePriceEtc skuValuePriceEtc = this.querySkuValuePriceEtc(nxtShoppingCartProduct);
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
                    nxtOrderFormProduct.setProductSku(nxtShoppingCartProduct.getSku());//json
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

                if (user.getInviterUserId() != null) {

                    //待瓜分的佣金占产品成交价的比率（数据库）
                    Long commissionRate = nxtProduct.getCommissionRate();

                    if (commissionRate == null){
                        commissionRate = nxtStructSettingCommission.getDefaultProductCommissionRate();
                    }

                    //三个分销佣金等级配置
                    Long commissionRateLevel1 = nxtStructSettingCommission.getCommissionRateLevel1();
                    Long commissionRateLevel2 = nxtStructSettingCommission.getCommissionRateLevel2();
                    Long commissionRateLevel3 = nxtStructSettingCommission.getCommissionRateLevel3();

                    NxtUser inviterUser1 = nxtUserService.queryById(user.getInviterUserId());

                    //佣金分配
                    if (inviterUser1 != null) {

                        //1、一级分销佣金

                        //佣金比率
                        nxtOrderFormProduct.setCommissionRate(commissionRate);
                        //更新佣金比率
                        nxtOrderFormProductService.update(nxtOrderFormProduct);

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
//        for (NxtShoppingCartProduct nxtShoppingCartProduct :
//                nxtShoppingCartProductList) {
//            nxtShoppingCartProductService.deleteById(nxtShoppingCartProduct.getId());
//        }

        return nxtOrderForm.getId();
    }

    /**
     * 查询购物车中产品sku对应的NxtProductSkuValuePriceEtc
     * @param nxtShoppingCartProduct
     * @return
     */
    private NxtProductSkuValuePriceEtc querySkuValuePriceEtc(NxtShoppingCartProduct nxtShoppingCartProduct) {

        Long skuValueId1 = null;//第1个sku值id
        Long skuValueId2 = null;//第2个sku值id

        /**
         * nxtShoppingCartProduct.getSku()的内容一般是这样的：
         * 一个json数组 [{"skuKeyName":"颜色","skuValueName":"白色"},{"skuKeyName":"尺码","skuValueName":"XL"}]
         * 然后根据sku文字，去数据库里面查询对应的价格、库存、折扣（NxtProductSkuValuePriceEtc）
         */

        Gson gson = new Gson();
        List<NxtStructShoppingCartProductSku> productSkuListSubmit = gson.fromJson(nxtShoppingCartProduct.getSku(), new TypeToken<List<NxtStructShoppingCartProductSku>>(){}.getType());

        //第1步：寻找该产品的所有sku
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(nxtShoppingCartProduct.getProductId());
        List<NxtProductSku> nxtProductSkuList = nxtProductSkuService.queryAll(nxtProductSkuCondition);
        for (NxtStructShoppingCartProductSku cartProductSku :
                productSkuListSubmit) {
            for (NxtProductSku nxtProductSku:
                    nxtProductSkuList) {
                //第2步：遍历匹配出对应的NxtProductSku
                if (nxtProductSku.getSkuKeyName().equals(cartProductSku.getSkuKeyName())){
                    NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
                    nxtProductSkuValueCondition.setSkuId(nxtProductSku.getId());
                    //第3步：查询该NxtProductSku下的所有sku值
                    List<NxtProductSkuValue> nxtProductSkuValueList = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
                    for (NxtProductSkuValue nxtProductSkuValue :
                            nxtProductSkuValueList) {
                        //第4步：遍历匹配出对应的NxtProductSkuValue
                        if (nxtProductSkuValue.getSkuValueName().equals(cartProductSku.getSkuValueName())) {
                            //第5步：保存sku value 的id
                            if (skuValueId1 == null) {
                                skuValueId1 = nxtProductSkuValue.getId();
                            } else if (skuValueId2 == null) {
                                skuValueId2 = nxtProductSkuValue.getId();
                            }
                        }
                    }
                }
            }
        }

        //最后：根据保存的2个sku value的id，查询NxtProductSkuValuePriceEtc
        if (skuValueId1 != null && skuValueId2 != null){
            NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtcCondition = new NxtProductSkuValuePriceEtc();
            nxtProductSkuValuePriceEtcCondition.setSkuValueId1(skuValueId1);
            nxtProductSkuValuePriceEtcCondition.setSkuValueId2(skuValueId2);
            List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList =nxtProductSkuValuePriceEtcService.queryAll(nxtProductSkuValuePriceEtcCondition);
            if (nxtProductSkuValuePriceEtcList.size() == 0) {
                nxtProductSkuValuePriceEtcCondition.setSkuValueId1(skuValueId2);
                nxtProductSkuValuePriceEtcCondition.setSkuValueId2(skuValueId1);
                nxtProductSkuValuePriceEtcList = nxtProductSkuValuePriceEtcService.queryAll(nxtProductSkuValuePriceEtcCondition);
            }
            //第一条就是了
            if (nxtProductSkuValuePriceEtcList.size() > 0){
                NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc = nxtProductSkuValuePriceEtcList.get(0);
                return nxtProductSkuValuePriceEtc;
            }
        }

        return null;

    }

    /**
     * 根据购物车，运费模版编号，国家、省份、城市，计算运费
     * @param nxtShoppingCart
     * @param nxtDeliveryConfigId
     * @param deliveryCountry
     * @param deliveryProvince
     * @param deliveryCity
     * @return
     */
    public Long calculateDeliveryCost(NxtShoppingCart nxtShoppingCart, Long nxtDeliveryConfigId, Long deliveryCountry, Long deliveryProvince, Long deliveryCity) throws NxtException{

        //运费模版
        NxtDeliveryConfig nxtDeliveryConfig = nxtDeliveryConfigService.queryById(nxtDeliveryConfigId);
        NxtStructDeliveryConfig nxtStructDeliveryConfig = nxtProcessDeliveryConfig.getDeliveryConfigAllDetail(nxtDeliveryConfig);

        //购物车内的已选中产品列表
        List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllSelectedProductByShoppingCartId(nxtShoppingCart.getId());

        Long countQuantity = 0L;
        Long countWeight = 0L;
        Long countVolume = 0L;
        if (nxtDeliveryConfig.getType().equals(3))//类型：（1:按重量 2:按体积 3:按件数）
        {
            for (NxtShoppingCartProduct cartProduct : nxtShoppingCartProductList) {
                countQuantity += cartProduct.getQuantity();
            }
        }
        else if (nxtDeliveryConfig.getType().equals(1))//类型：（1:按重量 2:按体积 3:按件数）
        {
            for (NxtShoppingCartProduct cartProduct : nxtShoppingCartProductList) {
                NxtProduct product = nxtProductService.queryById(cartProduct.getProductId());
                if (product.getUnitWeight() != null) {
                    countWeight += product.getUnitWeight();
                }
            }
        }
        else if (nxtDeliveryConfig.getType().equals(2))//类型：（1:按重量 2:按体积 3:按件数）
        {
            for (NxtShoppingCartProduct cartProduct : nxtShoppingCartProductList) {
                NxtProduct product = nxtProductService.queryById(cartProduct.getProductId());
                if (product.getUnitVolume() != null) {
                    countVolume += product.getUnitVolume();
                }
            }
        }

        NxtStructDeliveryConfigItem configItemRight = null;

        List<NxtStructDeliveryConfigItem> configItemList = nxtStructDeliveryConfig.getItemList();

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

        if (configItemRight == null) {
            throw new NxtException("没找对对应地区的运费模版条目");
        }

        if (nxtDeliveryConfig.getType().equals(3))//类型：（1:按重量 2:按体积 3:按件数）
        {
            if (countQuantity > configItemRight.getBillableQuantity()){
                Float cost = configItemRight.getBillablePrice() + (countQuantity - configItemRight.getBillableQuantity()) / (float)configItemRight.getAdditionQuantity() * configItemRight.getAdditionPrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }
            else {
                Float cost = configItemRight.getBillablePrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }


        }
        else if (nxtDeliveryConfig.getType().equals(1)){//类型：（1:按重量 2:按体积 3:按件数）

            if (countWeight > configItemRight.getBillableQuantity()) {//重量单位：克
                Float cost = configItemRight.getBillablePrice() + (float)Math.ceil((countWeight - configItemRight.getBillableQuantity()) / (float)configItemRight.getAdditionQuantity()) * configItemRight.getAdditionPrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }
            else {
                Float cost = configItemRight.getBillablePrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }
        }
        else  if (nxtDeliveryConfig.getType().equals(2)){//类型：（1:按重量 2:按体积 3:按件数）
            if (countVolume / 1000000F > configItemRight.getBillableQuantity()) {//体积单位：立法米（数据库存储放大了100万倍）
                Float cost = configItemRight.getBillablePrice() + (float)Math.ceil((countVolume / 1000000F - configItemRight.getBillableQuantity()) / (float)configItemRight.getAdditionQuantity()) * configItemRight.getAdditionPrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }
            else {
                Float cost = configItemRight.getBillablePrice();
                return (long)Math.round(cost * 100);//返回Long，放大100倍
            }
        }

        throw new NxtException("运费计算失败");

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
