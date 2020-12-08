package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.struct.admin.NxtStructAdminOrderFormRefundApprovalPostItemAmount;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
@Component
public class NxtProcessOrderFormRefund {

    @Resource
    private NxtTransactionService nxtTransactionService;


    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormRefundDeliveryService nxtOrderFormRefundDeliveryService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtOrderFormRefundService nxtOrderFormRefundService;

    @Resource
    private NxtOrderFormRefundProductService nxtOrderFormRefundProductService;

    @Resource
    private NxtOrderFormRefundPictureService nxtOrderFormRefundPictureService;

    @Resource
    private NxtOrderFormRefundLogService nxtOrderFormRefundLogService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    /**
     * 用户创建订单退款申请
     * @param userId
     * @param nxtStructOrderFormRefundCreate
     * @throws NxtException
     */
    @Transactional(rollbackFor=Exception.class)
    public void create(Long userId, NxtStructOrderFormRefundCreate nxtStructOrderFormRefundCreate) throws NxtException {

        if (nxtStructOrderFormRefundCreate.getId() == null){
            throw new NxtException("缺少订单id");
        }
        if (nxtStructOrderFormRefundCreate.getOrderFormProductList().size() == 0){
            throw new NxtException("缺少选中物品");
        }
        if (nxtStructOrderFormRefundCreate.getReasonType() == null){
            throw new NxtException("请选中退货理由");
        }
        if (nxtStructOrderFormRefundCreate.getReasionDescription() == null){
            throw new NxtException("请填写退货原因");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtStructOrderFormRefundCreate.getId());

        if (nxtOrderForm == null){
            throw new NxtException("找不到该订单");
        }

        if (!nxtOrderForm.getUserId().equals(userId)){
            throw new NxtException("该订单不属于你");
        }

        if (nxtOrderForm.getStatusPaid() < 1){
            throw new NxtException("该订单没有支付，售后申请无效");
        }



        //先检查退货物品
        List<NxtStructOrderFormRefundProduct> orderFormProductList = nxtStructOrderFormRefundCreate.getOrderFormProductList();
        for (NxtStructOrderFormRefundProduct nxtStructOrderFormRefundProduct :
                orderFormProductList) {
            //检查orderFormProductId归属正确后记录物品
            NxtOrderFormProduct nxtOrderFormProduct = nxtOrderFormProductService.queryById(nxtStructOrderFormRefundProduct.getOrderFormProductId());
            if (!(nxtOrderFormProduct != null && nxtOrderFormProduct.getOrderFormId().equals(nxtOrderForm.getId()))){
                throw new NxtException("有物品不属于该订单");
            }
            //检查申请退货数量，是否超过剩余可退货数量
            Long quantityMax = nxtOrderFormProduct.getQuantity() - nxtOrderFormProduct.getQuantityRefund();
            Long quantityWillRefund = nxtStructOrderFormRefundProduct.getQuantity();
            if (quantityWillRefund > quantityMax){
                throw new NxtException("有物品超过剩余可退货数量");
            }
        }

        //订单标记退货退款
        nxtOrderForm.setStatusRefund(1);
        nxtOrderFormService.update(nxtOrderForm);

        //创建退货记录
        NxtOrderFormRefund nxtOrderFormRefund = new NxtOrderFormRefund();
        nxtOrderFormRefund.setUserId(userId);
        nxtOrderFormRefund.setOrderFormId(nxtOrderForm.getId());
        nxtOrderFormRefund.setReasonType(nxtStructOrderFormRefundCreate.getReasonType());
        nxtOrderFormRefund.setReasionDescription(nxtStructOrderFormRefundCreate.getReasionDescription());
        nxtOrderFormRefund.setStatus(0);//状态（-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:收到货有问题，请修改金额）
        nxtOrderFormRefund.setDatelineCreate(System.currentTimeMillis());
        nxtOrderFormRefundService.insert(nxtOrderFormRefund);

        //插入退货物品
        for (NxtStructOrderFormRefundProduct nxtStructOrderFormRefundProduct :
                orderFormProductList) {
            //检查orderFormProductId归属正确后记录物品
            NxtOrderFormProduct nxtOrderFormProduct = nxtOrderFormProductService.queryById(nxtStructOrderFormRefundProduct.getOrderFormProductId());
            if (nxtOrderFormProduct != null && nxtOrderFormProduct.getOrderFormId().equals(nxtOrderForm.getId())){
                NxtOrderFormRefundProduct nxtOrderFormRefundProduct = new NxtOrderFormRefundProduct();
                nxtOrderFormRefundProduct.setOrderFormProductId(nxtOrderFormProduct.getId());
                nxtOrderFormRefundProduct.setOrderFormRefundId(nxtOrderFormRefund.getId());
                //检查申请退货数量，是否超过剩余可退货数量
                Long quantityMax = nxtOrderFormProduct.getQuantity() - nxtOrderFormProduct.getQuantityRefund();
                Long quantityWillRefund = nxtStructOrderFormRefundProduct.getQuantity();
                if (quantityWillRefund > quantityMax){
                    quantityWillRefund = quantityMax;
                }
                nxtOrderFormRefundProduct.setQuantity(quantityWillRefund);
                nxtOrderFormRefundProduct.setPriceDeal(nxtOrderFormProduct.getProductPriceDeal());
                //退货总金额：数量x成交单价
                Long amountRefund = quantityWillRefund * nxtOrderFormProduct.getProductPriceDeal();
                nxtOrderFormRefundProduct.setAmountRefund(amountRefund);
                nxtOrderFormRefundProductService.insert(nxtOrderFormRefundProduct);

                //更新已申请退货数量
                nxtOrderFormProduct.setQuantityRefund(quantityWillRefund+nxtOrderFormProduct.getQuantityRefund());
                nxtOrderFormProductService.update(nxtOrderFormProduct);
            }
        }

        //图片
        List<Long> imageIdList = nxtStructOrderFormRefundCreate.getImageIdList();
        for (Long uploadFileid :
                imageIdList) {
            NxtOrderFormRefundPicture nxtOrderFormRefundPicture = new NxtOrderFormRefundPicture();
            nxtOrderFormRefundPicture.setOrderFormRefundId(nxtOrderFormRefund.getId());
            nxtOrderFormRefundPicture.setUploadfileId(uploadFileid);
            nxtOrderFormRefundPictureService.insert(nxtOrderFormRefundPicture);
        }

        //日志
        NxtOrderFormRefundLog nxtOrderFormRefundLog = new NxtOrderFormRefundLog();
        nxtOrderFormRefundLog.setOrderFormRefundId(nxtOrderFormRefund.getId());
        nxtOrderFormRefundLog.setUserId(userId);
        nxtOrderFormRefundLog.setIsAdmin(0);
        nxtOrderFormRefundLog.setDateline(System.currentTimeMillis());
        nxtOrderFormRefundLog.setLogName("订单申请退货");
        nxtOrderFormRefundLog.setLogRemark(nxtOrderFormRefund.getReasionDescription());
        nxtOrderFormRefundLog.setStatusPaid(nxtOrderForm.getStatusPaid());
        nxtOrderFormRefundLog.setStatusDelivery(nxtOrderForm.getStatusDelivery());
        nxtOrderFormRefundLogService.insert(nxtOrderFormRefundLog);

    }

    /**
     * 订单中可退款物品列表
     * @param nxtOrderForm
     */
    public List<NxtStructOrderFormRefundProductAllowItem> productAllowList(NxtOrderForm nxtOrderForm) throws NxtException{

        if (nxtOrderForm == null){
            throw new NxtException("请提供订单");
        }

        Gson gson = new Gson();

        List<NxtStructOrderFormRefundProductAllowItem> nxtStructOrderFormRefundProductAllowItemList = new ArrayList<>();

        Map<Long,Long> orderFormProductIdToProductId = new HashMap();
        //查询订单物品
        List<Long> orderFormIdList = new ArrayList<>();
        orderFormIdList.add(nxtOrderForm.getId());
        List<NxtOrderFormProduct> orderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);
        for (NxtOrderFormProduct nxtOrderFormProduct :
                orderFormProductList) {

            orderFormProductIdToProductId.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductId());

            NxtStructOrderFormRefundProductAllowItem nxtStructOrderFormRefundProductAllowItem = new NxtStructOrderFormRefundProductAllowItem();
            nxtStructOrderFormRefundProductAllowItem.setOrderFormProductId(nxtOrderFormProduct.getId());
            //检查可退货数量
            Long quantityAllowRefund = nxtOrderFormProduct.getQuantity() - nxtOrderFormProduct.getQuantityRefund();
            nxtStructOrderFormRefundProductAllowItem.setQuantityAllowRefund(quantityAllowRefund);
            nxtStructOrderFormRefundProductAllowItem.setQuantityAll(nxtOrderFormProduct.getQuantity());
            nxtStructOrderFormRefundProductAllowItem.setQuantityIsRefund(nxtOrderFormProduct.getQuantityRefund());
            nxtStructOrderFormRefundProductAllowItem.setAllowRefund(quantityAllowRefund > 0);

            nxtStructOrderFormRefundProductAllowItem.setProductName(nxtOrderFormProduct.getProductName());
            nxtStructOrderFormRefundProductAllowItem.setProductPriceDeal(nxtOrderFormProduct.getProductPriceDeal()/100F);

            try {
                if (nxtOrderFormProduct.getProductSku() != null) {
                    List<NxtStructOrderFormProductSku> skuList = gson.fromJson(nxtOrderFormProduct.getProductSku(), new TypeToken<List<NxtStructOrderFormProductSku>>() {
                    }.getType());
                    nxtStructOrderFormRefundProductAllowItem.setSku(skuList);
                }
            }
            catch (Exception e){
                throw new NxtException("订单物品suk解析错误");
            }

            nxtStructOrderFormRefundProductAllowItemList.add(nxtStructOrderFormRefundProductAllowItem);

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

        for (NxtStructOrderFormRefundProductAllowItem nxtStructOrderFormRefundProductAllowItem : nxtStructOrderFormRefundProductAllowItemList){
            Long productId = orderFormProductIdToProductId.get(nxtStructOrderFormRefundProductAllowItem.getOrderFormProductId());
            nxtStructOrderFormRefundProductAllowItem.setPicUrl(mapProductIdToPicUrl.getOrDefault(productId,"/common/images/empty.png"));
        }

        return nxtStructOrderFormRefundProductAllowItemList;

    }

    /**
     * 后台查询售后订单数量
     * @param offset
     * @param limit
     * @param status
     * @param userId
     * @param orderFormId
     * @return
     * @throws NxtException
     */
    public Long adminQueryCount(Long offset, Long limit, Integer status, Long userId, Long orderFormId) throws NxtException {

        return nxtOrderFormRefundService.adminQueryCount(offset,limit,status,userId,orderFormId);

    }

    /**
     * 后台查询售后订单列表
     * @param offset
     * @param limit
     * @param status
     * @param userId
     * @param orderFormId
     * @return
     * @throws NxtException
     */
    public List<NxtStructOrderFormRefund> adminQueryList(Long offset, Long limit, Integer status, Long userId, Long orderFormId) throws NxtException {

        List<NxtOrderFormRefund> nxtOrderFormRefundList = nxtOrderFormRefundService.adminQueryList(offset,limit,status,userId,orderFormId);

        return this.assemblyStructOrderFormRefundList(nxtOrderFormRefundList);

    }

    /**
     * 用户售后单列表
     * @param userId
     * @param offset
     * @param limit
     * @param isDone
     * @param isShippedOrWaitShipping
     * @param isApplied
     * @return
     */
    public List<NxtStructOrderFormRefund> userOrderFormRefundList(Long userId, Long offset, Long limit,Boolean isDone, Boolean isShippedOrWaitShipping, Boolean isApplied) throws NxtException {

        List<NxtOrderFormRefund> nxtOrderFormRefundList = nxtOrderFormRefundService.queryAllByUserIdAndLimit(offset, limit, userId, isDone, isShippedOrWaitShipping, isApplied);

        return this.assemblyStructOrderFormRefundList(nxtOrderFormRefundList);

    }

    /**
     * 根据初步的nxtOrderFormRefundList装配详细的结构化售后订单列表
     * @param nxtOrderFormRefundList
     * @return
     */
    public List<NxtStructOrderFormRefund> assemblyStructOrderFormRefundList(List<NxtOrderFormRefund> nxtOrderFormRefundList) throws NxtException{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = new Gson();

        List<NxtStructOrderFormRefund> nxtStructOrderFormRefundList = new ArrayList<>();

        Map<Long,NxtStructOrderFormRefund> mapIdToNxtStructOrderFormRefund = new HashMap<>();

        Set<Long> productIdSet = new HashSet<>();
        List<Long> orderFormIdList = new ArrayList<>();


        for (NxtOrderFormRefund nxtOrderFormRefund : nxtOrderFormRefundList) {

            NxtStructOrderFormRefund nxtStructOrderFormRefund = new NxtStructOrderFormRefund();
            nxtStructOrderFormRefund.setId(nxtOrderFormRefund.getId());
            nxtStructOrderFormRefund.setUserId(nxtOrderFormRefund.getUserId());
            nxtStructOrderFormRefund.setOrderFormId(nxtOrderFormRefund.getOrderFormId());
            nxtStructOrderFormRefund.setReasonType(nxtOrderFormRefund.getReasonType());

            if (nxtOrderFormRefund.getReasonType().equals(0)) {
                nxtStructOrderFormRefund.setReasonTypeText("无理由");
            }
            else if (nxtOrderFormRefund.getReasonType().equals(1)) {
                nxtStructOrderFormRefund.setReasonTypeText("质量问题");
            }
            nxtStructOrderFormRefund.setStatus(nxtOrderFormRefund.getStatus());
            nxtStructOrderFormRefund.setStatusText(this.getStatusText(nxtOrderFormRefund.getStatus()));
            nxtStructOrderFormRefund.setReasionDescription(nxtOrderFormRefund.getReasionDescription());

            nxtStructOrderFormRefund.setDatelineCreate(nxtOrderFormRefund.getDatelineCreate());
            nxtStructOrderFormRefund.setDatelineCreateReadable(sdf.format(new Date(nxtOrderFormRefund.getDatelineCreate())));

            if (nxtOrderFormRefund.getDatelineEnd() != null){
                nxtStructOrderFormRefund.setDatelineEnd(nxtOrderFormRefund.getDatelineEnd());
                nxtStructOrderFormRefund.setDatelineEndReadable(sdf.format(new Date(nxtOrderFormRefund.getDatelineEnd())));
            }

            //列表
            nxtStructOrderFormRefundList.add(nxtStructOrderFormRefund);

            //备用map
            mapIdToNxtStructOrderFormRefund.put(nxtOrderFormRefund.getId(),nxtStructOrderFormRefund);

            orderFormIdList.add(nxtOrderFormRefund.getOrderFormId());

        }


        //取订单
        Map<Long,NxtOrderForm> mapOrderFormIdToNxtOrderForm = new HashMap<>();
        List<NxtOrderForm> nxtOrderFormList = nxtOrderFormService.selectByIdSet(orderFormIdList);
        for (NxtOrderForm nxtOrderForm :
                nxtOrderFormList) {
            mapOrderFormIdToNxtOrderForm.put(nxtOrderForm.getId(),nxtOrderForm);
        }

        //批量取sku 、productName
        Map<Long,Long> orderFormProductIdToProductId = new HashMap<>();
        Map<Long,String> mapOrderFormProductIdToProductName = new HashMap<>();
        Map<Long,List<NxtStructOrderFormProductSku>> mapOrderFormProductSku = new HashMap<>();
        List<NxtOrderFormProduct> orderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);
        for (NxtOrderFormProduct nxtOrderFormProduct : orderFormProductList) {
            mapOrderFormProductIdToProductName.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductName());
            try {
                if (nxtOrderFormProduct.getProductSku() != null) {
                    List<NxtStructOrderFormProductSku> skuList = gson.fromJson(nxtOrderFormProduct.getProductSku(), new TypeToken<List<NxtStructOrderFormProductSku>>() {
                    }.getType());
                    mapOrderFormProductSku.put(nxtOrderFormProduct.getId(),skuList);
                }
            }
            catch (Exception e){
                throw new NxtException("订单物品suk解析错误");
            }
            orderFormProductIdToProductId.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductId());
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


        //取退货物品列表
        Map<Long,Long> orderFormRefundProductIdToOrderFormProductId = new HashMap();
        List<Long> orderFormRefundIdList = new ArrayList<>();
        orderFormRefundIdList.addAll(mapIdToNxtStructOrderFormRefund.keySet());

        //各退款单的退款总额
        Map<Long,Long> mapRefundTotal = new HashMap<>();

        List<NxtOrderFormRefundProduct> orderFormRefundProductList = nxtOrderFormRefundProductService.selectAllByOrderFormRefundIdSet(orderFormRefundIdList);
        for (NxtOrderFormRefundProduct nxtOrderFormRefundProduct :
                orderFormRefundProductList) {

            orderFormRefundProductIdToOrderFormProductId.put(nxtOrderFormRefundProduct.getId(),nxtOrderFormRefundProduct.getOrderFormProductId());

            NxtStructOrderFormRefundProduct nxtStructOrderFormRefundProduct = new NxtStructOrderFormRefundProduct();
            nxtStructOrderFormRefundProduct.setId(nxtOrderFormRefundProduct.getId());
            nxtStructOrderFormRefundProduct.setOrderFormProductId(nxtOrderFormRefundProduct.getId());
            nxtStructOrderFormRefundProduct.setQuantity(nxtOrderFormRefundProduct.getQuantity());
            nxtStructOrderFormRefundProduct.setProductPriceDeal(nxtOrderFormRefundProduct.getPriceDeal()/100F);
            nxtStructOrderFormRefundProduct.setAmountRefund(nxtOrderFormRefundProduct.getAmountRefund()/100F);

            //productName
            if (mapOrderFormProductIdToProductName.containsKey(nxtOrderFormRefundProduct.getOrderFormProductId())){
                nxtStructOrderFormRefundProduct.setProductName(mapOrderFormProductIdToProductName.get(nxtOrderFormRefundProduct.getOrderFormProductId()));
            }

            //sku
            if (mapOrderFormProductSku.containsKey(nxtOrderFormRefundProduct.getOrderFormProductId())){
                nxtStructOrderFormRefundProduct.setSku(mapOrderFormProductSku.get(nxtOrderFormRefundProduct.getOrderFormProductId()));
            }

            //主图
            nxtStructOrderFormRefundProduct.setPicUrl("/common/images/empty.png");//默认主图
            Long productId = orderFormProductIdToProductId.get(nxtOrderFormRefundProduct.getOrderFormProductId());
            if (productId != null && mapProductIdToPicUrl.containsKey(productId)){
                nxtStructOrderFormRefundProduct.setPicUrl(mapProductIdToPicUrl.get(productId));
            }

            //把物品塞到对应的退款单
            mapIdToNxtStructOrderFormRefund.get(nxtOrderFormRefundProduct.getOrderFormRefundId()).getOrderFormRefundProductList().add(nxtStructOrderFormRefundProduct);

            //各退款单的退款总额
            if (!mapRefundTotal.containsKey(nxtOrderFormRefundProduct.getOrderFormRefundId())){
                mapRefundTotal.put(nxtOrderFormRefundProduct.getOrderFormRefundId(),0L);
            }
            mapRefundTotal.put(nxtOrderFormRefundProduct.getOrderFormRefundId(), mapRefundTotal.get(nxtOrderFormRefundProduct.getOrderFormRefundId())+nxtOrderFormRefundProduct.getAmountRefund());
        }

        //附加订单内容
        for (NxtStructOrderFormRefund item : nxtStructOrderFormRefundList) {
            if (mapOrderFormIdToNxtOrderForm.containsKey(item.getOrderFormId())) {
                NxtOrderForm nxtOrderForm = mapOrderFormIdToNxtOrderForm.get(item.getOrderFormId());
                item.setOrderFormSerialNum(nxtOrderForm.getSerialNum());
                item.setDeliveryPerson(nxtOrderForm.getDeliveryPerson());
                item.setDeliveryPhone(nxtOrderForm.getDeliveryPhone());
                item.setAmountFinally(nxtOrderForm.getAmountFinally()/100F);
                if (mapRefundTotal.containsKey(item.getId())) {
                    item.setAmountRefundTotal(mapRefundTotal.get(item.getId())/100F);
                }
            }
        }

        return nxtStructOrderFormRefundList;
    }

    public String getStatusText(Integer status){
        //-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:收到货有问题，请修改金额
        if (status.equals(-1)){
            return "拒绝退款";
        }
        else if (status.equals(0)){
            return "已申请";
        }
        else if (status.equals(1)){
            return "完成";
        }
        else if (status.equals(2)){
            return "等用户发货";
        }
        else if (status.equals(3)){
            return "收到货退款";
        }
        else if (status.equals(4)){
            return "收到货有问题，请修改金额";
        }
        else if (status.equals(5)){
            return "用户已经寄出物品";
        }
        else {
            return null;
        }
    }

    /**
     * 某个售后单详情
     * @param id
     * @return
     */
    public NxtStructOrderFormRefund allDetail(Long id) throws NxtException {


        NxtOrderFormRefund nxtOrderFormRefund = nxtOrderFormRefundService.queryById(id);
        if (nxtOrderFormRefund == null){
            return null;
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = new Gson();

        Set<Long> productIdSet = new HashSet<>();
        List<Long> orderFormIdList = new ArrayList<>();


        NxtStructOrderFormRefund nxtStructOrderFormRefund = new NxtStructOrderFormRefund();
        nxtStructOrderFormRefund.setId(nxtOrderFormRefund.getId());
        nxtStructOrderFormRefund.setUserId(nxtOrderFormRefund.getUserId());
        nxtStructOrderFormRefund.setOrderFormId(nxtOrderFormRefund.getOrderFormId());
        nxtStructOrderFormRefund.setReasonType(nxtOrderFormRefund.getReasonType());

        if (nxtOrderFormRefund.getReasonType().equals(0)) {
            nxtStructOrderFormRefund.setReasonTypeText("无理由");
        }
        else if (nxtOrderFormRefund.getReasonType().equals(1)) {
            nxtStructOrderFormRefund.setReasonTypeText("质量问题");
        }
        nxtStructOrderFormRefund.setStatus(nxtOrderFormRefund.getStatus());
        nxtStructOrderFormRefund.setStatusText(this.getStatusText(nxtOrderFormRefund.getStatus()));
        nxtStructOrderFormRefund.setReasionDescription(nxtOrderFormRefund.getReasionDescription());

        nxtStructOrderFormRefund.setDatelineCreate(nxtOrderFormRefund.getDatelineCreate());
        nxtStructOrderFormRefund.setDatelineCreateReadable(sdf.format(new Date(nxtOrderFormRefund.getDatelineCreate())));

        if (nxtOrderFormRefund.getDatelineEnd() != null){
            nxtStructOrderFormRefund.setDatelineEnd(nxtOrderFormRefund.getDatelineEnd());
            nxtStructOrderFormRefund.setDatelineEndReadable(sdf.format(new Date(nxtOrderFormRefund.getDatelineEnd())));
        }


        orderFormIdList.add(nxtOrderFormRefund.getOrderFormId());

        //取订单内容
        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtOrderFormRefund.getOrderFormId());
        if (nxtOrderForm != null){
            nxtStructOrderFormRefund.setOrderFormSerialNum(nxtOrderForm.getSerialNum());
            nxtStructOrderFormRefund.setDeliveryPerson(nxtOrderForm.getDeliveryPerson());
            nxtStructOrderFormRefund.setDeliveryPhone(nxtOrderForm.getDeliveryPhone());
            nxtStructOrderFormRefund.setAmountFinally(nxtOrderForm.getAmountFinally()/100F);
        }

        //批量取sku 、productName
        Map<Long,Long> orderFormProductIdToProductId = new HashMap<>();
        Map<Long,String> mapOrderFormProductIdToProductName = new HashMap<>();
        Map<Long,List<NxtStructOrderFormProductSku>> mapOrderFormProductSku = new HashMap<>();
        List<NxtOrderFormProduct> orderFormProductList = nxtOrderFormProductService.selectAllByOrderFormIdSet(orderFormIdList);
        for (NxtOrderFormProduct nxtOrderFormProduct : orderFormProductList) {
            mapOrderFormProductIdToProductName.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductName());
            try {
                if (nxtOrderFormProduct.getProductSku() != null) {
                    List<NxtStructOrderFormProductSku> skuList = gson.fromJson(nxtOrderFormProduct.getProductSku(), new TypeToken<List<NxtStructOrderFormProductSku>>() {
                    }.getType());
                    mapOrderFormProductSku.put(nxtOrderFormProduct.getId(),skuList);
                }
            }
            catch (Exception e){
                throw new NxtException("订单物品suk解析错误");
            }
            orderFormProductIdToProductId.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductId());
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


        //取退货物品列表
        Map<Long,Long> orderFormRefundProductIdToOrderFormProductId = new HashMap();
        List<Long> orderFormRefundIdList = new ArrayList<>();
        orderFormRefundIdList.add(nxtOrderFormRefund.getId());

        List<NxtOrderFormRefundProduct> orderFormRefundProductList = nxtOrderFormRefundProductService.selectAllByOrderFormRefundIdSet(orderFormRefundIdList);
        for (NxtOrderFormRefundProduct nxtOrderFormRefundProduct :
                orderFormRefundProductList) {

            orderFormRefundProductIdToOrderFormProductId.put(nxtOrderFormRefundProduct.getId(),nxtOrderFormRefundProduct.getOrderFormProductId());

            NxtStructOrderFormRefundProduct nxtStructOrderFormRefundProduct = new NxtStructOrderFormRefundProduct();
            nxtStructOrderFormRefundProduct.setId(nxtOrderFormRefundProduct.getId());
            nxtStructOrderFormRefundProduct.setOrderFormProductId(nxtOrderFormRefundProduct.getId());
            nxtStructOrderFormRefundProduct.setQuantity(nxtOrderFormRefundProduct.getQuantity());
            nxtStructOrderFormRefundProduct.setProductPriceDeal(nxtOrderFormRefundProduct.getPriceDeal()/100F);
            nxtStructOrderFormRefundProduct.setAmountRefund(nxtOrderFormRefundProduct.getAmountRefund()/100F);

            //productName
            if (mapOrderFormProductIdToProductName.containsKey(nxtOrderFormRefundProduct.getOrderFormProductId())){
                nxtStructOrderFormRefundProduct.setProductName(mapOrderFormProductIdToProductName.get(nxtOrderFormRefundProduct.getOrderFormProductId()));
            }

            //sku
            if (mapOrderFormProductSku.containsKey(nxtOrderFormRefundProduct.getOrderFormProductId())){
                nxtStructOrderFormRefundProduct.setSku(mapOrderFormProductSku.get(nxtOrderFormRefundProduct.getOrderFormProductId()));
            }

            //主图
            nxtStructOrderFormRefundProduct.setPicUrl("/common/images/empty.png");//默认主图
            Long productId = orderFormProductIdToProductId.get(nxtOrderFormRefundProduct.getOrderFormProductId());
            if (productId != null && mapProductIdToPicUrl.containsKey(productId)){
                nxtStructOrderFormRefundProduct.setPicUrl(mapProductIdToPicUrl.get(productId));
            }

            //把物品塞到对应的退款单
            nxtStructOrderFormRefund.getOrderFormRefundProductList().add(nxtStructOrderFormRefundProduct);

        }

        //查申请售后时提交的图片
        NxtOrderFormRefundPicture nxtOrderFormRefundPictureCondition = new NxtOrderFormRefundPicture();
        nxtOrderFormRefundPictureCondition.setOrderFormRefundId(nxtOrderFormRefund.getId());
        List<NxtOrderFormRefundPicture> nxtOrderFormRefundPictureList = nxtOrderFormRefundPictureService.queryAll(nxtOrderFormRefundPictureCondition);
        List<Long> orderFormRefundPicUploadFileIdList = new ArrayList<>();
        for (NxtOrderFormRefundPicture item : nxtOrderFormRefundPictureList) {
            orderFormRefundPicUploadFileIdList.add(item.getUploadfileId());
        }
        List<NxtUploadfile> nxtUploadfileList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,orderFormRefundPicUploadFileIdList);

        List<String> reasonImageList = new ArrayList<>();
        for (NxtUploadfile nxtUploadFile : nxtUploadfileList) {
            reasonImageList.add(nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadFile.getUrlpath()));
        }

        nxtStructOrderFormRefund.setReasonImageList(reasonImageList);

        //查询退货快递
        if (nxtOrderFormRefund.getStatus().equals(5)){//状态（-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:直接退款 5:用户已寄出物品）
            NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery = nxtOrderFormRefundDeliveryService.queryByOrderFormRefundId(nxtOrderFormRefund.getId());
            if (nxtOrderFormRefundDelivery != null){
                nxtStructOrderFormRefund.setDeliveryCompanyName(nxtOrderFormRefundDelivery.getDeliveryCompanyName());
                nxtStructOrderFormRefund.setDeliverySerialNum(nxtOrderFormRefundDelivery.getDeliverySerialNum());
            }
        }

        return nxtStructOrderFormRefund;

    }

    /**
     * 执行退款操作
     * @param nxtOrderFormRefund
     * @param refundAmountList
     */
    @Transactional(rollbackFor=Exception.class)
    public void execRefund(NxtOrderFormRefund nxtOrderFormRefund, List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> refundAmountList, Boolean refundDeliveryCost) throws NxtException {

        NxtTransaction nxtTransactionCondition = new NxtTransaction();
        nxtTransactionCondition.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransactionCondition.setOuterId(nxtOrderFormRefund.getId());
        List<NxtTransaction> nxtTransactionList = nxtTransactionService.queryAll(nxtTransactionCondition);

        if (nxtTransactionList.size() > 0){
            throw new NxtException("不可重复退款");
        }

        //自定义最终退款金额
        Map<Long,Long> mapRefundAmount = new HashMap<>();
        if (refundAmountList != null){
            for (NxtStructAdminOrderFormRefundApprovalPostItemAmount item : refundAmountList) {
                mapRefundAmount.put(item.getOrderFromRefundProductId(),(long)(item.getOrderFromRefundProductAmount()*100));
            }
        }

        //查询物品
        List<NxtOrderFormRefundProduct> nxtOrderFormRefundProductList = nxtOrderFormRefundProductService.selectAllByOrderFormRefundIdSet(Stream.of(nxtOrderFormRefund.getId()).collect(toList()));

        Long amountRefundTotal = 0L;
        for (NxtOrderFormRefundProduct item : nxtOrderFormRefundProductList) {
            if (mapRefundAmount.containsKey(item.getId())){
                //修改成最终退款金额
                if (mapRefundAmount.get(item.getId()) > item.getAmountRefund()){
                    throw new NxtException("有物品退款额过多，请修改");
                }
                item.setAmountRefund(mapRefundAmount.get(item.getId()));
                nxtOrderFormRefundProductService.update(item);
            }
            amountRefundTotal += item.getAmountRefund();
        }

        //退款到余额(记到账本)
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(nxtOrderFormRefund.getUserId());
        nxtTransaction.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setAmount(amountRefundTotal);
        nxtTransaction.setDateline(System.currentTimeMillis());
        nxtTransaction.setOuterId(nxtOrderFormRefund.getId());
        nxtTransactionService.insert(nxtTransaction);

        //退运费
        if (refundDeliveryCost != null && refundDeliveryCost){
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(nxtOrderFormRefund.getOrderFormId());
            Long cost = nxtOrderForm.getDeliveryCost();
//            cost += nxtOrderForm.getManualDeliveryCostDiscount();
            if (nxtOrderForm.getManualDeliveryCostDiscount() != null){
                cost += nxtOrderForm.getManualDeliveryCostDiscount();
            }
            if (cost > 0) {
                NxtTransaction nxtTransactionDeliveryCost = new NxtTransaction();
                nxtTransactionDeliveryCost.setUserId(nxtOrderFormRefund.getUserId());
                nxtTransactionDeliveryCost.setType(3);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
                nxtTransactionDeliveryCost.setAmount(cost);
                nxtTransactionDeliveryCost.setDateline(System.currentTimeMillis());
                nxtTransactionDeliveryCost.setOuterId(nxtOrderFormRefund.getId());
                nxtTransactionService.insert(nxtTransactionDeliveryCost);
            }
        }

        //设置售后完成
        nxtOrderFormRefund.setStatus(1);
        nxtOrderFormRefundService.update(nxtOrderFormRefund);

    }

}
