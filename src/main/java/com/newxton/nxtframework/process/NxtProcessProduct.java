package com.newxton.nxtframework.process;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessProduct {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductSkuValuePriceEtcService nxtProductSkuValuePriceEtcService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    public NxtStructProduct getProductAllDetail(NxtProduct nxtProduct) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        NxtStructProduct nxtStructProduct = new NxtStructProduct();

        nxtStructProduct.setId(nxtProduct.getId());
        nxtStructProduct.setCategoryId(nxtProduct.getCategoryId());
        nxtStructProduct.setBrandId(nxtProduct.getBrandId());
        nxtStructProduct.setProductName(nxtProduct.getProductName());
        nxtStructProduct.setProductSubtitle(nxtProduct.getProductSubtitle());
        nxtStructProduct.setDealQuantityMin(nxtProduct.getDealQuantityMin());
        nxtStructProduct.setDealQuantityMax(nxtProduct.getDealQuantityMax());
        nxtStructProduct.setFreeShipping(nxtProduct.getFreeShipping() != null && nxtProduct.getFreeShipping() > 0);
        nxtStructProduct.setDeliveryConfigId(nxtProduct.getDeliveryConfigId());
        nxtStructProduct.setItemNo(nxtProduct.getItemNo());
        nxtStructProduct.setWithSku(nxtProduct.getWithSku() != null && nxtProduct.getWithSku() > 0);
        if (nxtProduct.getPrice() != null) {
            nxtStructProduct.setPrice(nxtProduct.getPrice() / 100F);
        }
        nxtStructProduct.setPriceDiscount(nxtProduct.getPriceDiscount()/100F);
        nxtStructProduct.setInventoryQuantity(nxtProduct.getInventoryQuantity());
        nxtStructProduct.setProductDescription(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(nxtProduct.getProductDescription()));

        nxtStructProduct.setDatelineCreate(nxtProduct.getDatelineCreate());
        nxtStructProduct.setDatelineUpdated(nxtProduct.getDatelineUpdated());
        nxtStructProduct.setDatelineCreateReadable(sdf.format(new Date(nxtProduct.getDatelineCreate())));
        nxtStructProduct.setDatelineUpdatedReadable(sdf.format(new Date(nxtProduct.getDatelineUpdated())));

        nxtStructProduct.setRecommend(nxtProduct.getIsRecommend() != null && nxtProduct.getIsRecommend() > 0);
        nxtStructProduct.setHot(nxtProduct.getIsHot() != null && nxtProduct.getIsHot() > 0);
        nxtStructProduct.setNew(nxtProduct.getIsNew() != null && nxtProduct.getIsNew() > 0);
        nxtStructProduct.setSelling(nxtProduct.getIsSelling() != null && nxtProduct.getIsSelling() > 0);
        nxtStructProduct.setTrash(nxtProduct.getIsTrash() != null && nxtProduct.getIsTrash() > 0);

        //图片列表
        nxtStructProduct.setPictureList(getProductPictureList(nxtStructProduct));

        //sku列表
        nxtStructProduct.setSkuList(getProductSkuList(nxtStructProduct));

        //sku价格、库存等交叉信息列表
        nxtStructProduct.setSkuValuePriceEtcList(getProductSkuValuePriceEtcList(nxtStructProduct.getSkuList()));

        return nxtStructProduct;

    }

    /**
     * 获取产品图片列表Struct详情
     * @param nxtStructProduct
     * @return
     */
    public List<NxtStructProductPicture> getProductPictureList(NxtStructProduct nxtStructProduct){

        List<NxtStructProductPicture> nxtStructProductPictureList = new ArrayList<>();

        /*查询产品图片*/
        List<Map<String, Object>> pictureList = new ArrayList<>();
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(nxtStructProduct.getId());
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        List<Long> picUploadfileIdList = new ArrayList<>();
        for (NxtProductPicture nxtProductPicture : picList) {
            picUploadfileIdList.add(nxtProductPicture.getUploadfileId());
        }
        List<NxtUploadfile> uploadfileList = nxtUploadfileService.selectByIdSet(0, Integer.MAX_VALUE, picUploadfileIdList);
        for (NxtUploadfile nxtUploadfile : uploadfileList) {
            NxtStructProductPicture nxtStructProductPicture = new NxtStructProductPicture();
            nxtStructProductPicture.setId(nxtUploadfile.getId());
            nxtStructProductPicture.setUrl(nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
            nxtStructProductPictureList.add(nxtStructProductPicture);
        }

        return nxtStructProductPictureList;

    }

    /**
     * 获取产品sku列表
     * @param nxtStructProduct
     * @return
     */
    public List<NxtStructProductSku> getProductSkuList(NxtStructProduct nxtStructProduct){

        List<NxtStructProductSku> nxtStructProductSkuList = new ArrayList<>();

        //查询 productSkuList
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(nxtStructProduct.getId());
        List<NxtProductSku> listSku = nxtProductSkuService.queryAll(nxtProductSkuCondition);
        List<Long> skuIdList = new ArrayList<>();

        Map<Long,NxtStructProductSku> skuIdToSkuMap = new HashMap<>();

        for (NxtProductSku productSku : listSku) {
            skuIdList.add(productSku.getId());

            NxtStructProductSku nxtStructProductSku = new NxtStructProductSku();
            nxtStructProductSku.setSkuId(productSku.getId());
            nxtStructProductSku.setSkuKeyName(productSku.getSkuKeyName());
            nxtStructProductSkuList.add(nxtStructProductSku);

            skuIdToSkuMap.put(productSku.getId(),nxtStructProductSku);

        }

        List<Long> skuValueIdList = new ArrayList<>();
        Map<Long,String> skuValueIdToNameMap = new HashMap<>();
        List<NxtProductSkuValue> nxtProductSkuValueList = nxtProductSkuValueService.selectBySkuIdSet(0, Integer.MAX_VALUE, skuIdList);
        for (NxtProductSkuValue skuValue :
                nxtProductSkuValueList) {

            skuValueIdList.add(skuValue.getId());
            skuValueIdToNameMap.put(skuValue.getId(),skuValue.getSkuValueName());

            NxtStructProductSkuValue nxtStructProductSkuValue = new NxtStructProductSkuValue();

            nxtStructProductSkuValue.setId(skuValue.getId());
            nxtStructProductSkuValue.setSkuId(skuValue.getSkuId());
            nxtStructProductSkuValue.setSkuValueName(skuValue.getSkuValueName());

            List<NxtStructProductSkuValue> skuValueList = skuIdToSkuMap.get(skuValue.getSkuId()).getSkuValueList();

            skuValueList.add(nxtStructProductSkuValue);

        }

        return nxtStructProductSkuList;

    }

    /**
     * 获取产品sku的价格、库存列表
     * @param nxtStructProductSkuList
     * @return
     */
    public List<NxtStructProductSkuValuePriceEtc> getProductSkuValuePriceEtcList(List<NxtStructProductSku> nxtStructProductSkuList){

        List<Long> skuValueIdList = new ArrayList<>();

        Map<Long,String> skuValueIdToNameMap = new HashMap<>();

        for (NxtStructProductSku skuItem :
                nxtStructProductSkuList) {
            List<NxtStructProductSkuValue> nxtStructProductSkuValueList = skuItem.getSkuValueList();
            for (NxtStructProductSkuValue nxtStructProductSkuValue :
                    nxtStructProductSkuValueList) {
                skuValueIdList.add(nxtStructProductSkuValue.getId());
                skuValueIdToNameMap.put(nxtStructProductSkuValue.getId(),nxtStructProductSkuValue.getSkuValueName());
            }
        }

        //获取sku的价格、库存、折扣
        List<NxtStructProductSkuValuePriceEtc> nxtStructProductSkuValuePriceEtcList = new ArrayList<>();

        List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList = nxtProductSkuValuePriceEtcService.selectByValueIdSet(0, Integer.MAX_VALUE, skuValueIdList);

        for (NxtProductSkuValuePriceEtc skuValueEtcItem:nxtProductSkuValuePriceEtcList) {

            NxtStructProductSkuValuePriceEtc nxtStructProductSkuValuePriceEtc = new NxtStructProductSkuValuePriceEtc();
            nxtStructProductSkuValuePriceEtc.setSkuValueName1(skuValueIdToNameMap.get(skuValueEtcItem.getSkuValueId1()));

            if (skuValueEtcItem.getSkuValueId2() > 0) {
                nxtStructProductSkuValuePriceEtc.setSkuValueName2(skuValueIdToNameMap.get(skuValueEtcItem.getSkuValueId2()));
            }

            nxtStructProductSkuValuePriceEtc.setSkuValueInventoryQuantity(skuValueEtcItem.getSkuValueInventoryQuantity());
            nxtStructProductSkuValuePriceEtc.setSkuValuePrice(skuValueEtcItem.getSkuValuePrice()/100F);
            nxtStructProductSkuValuePriceEtc.setSkuValuePriceDiscount(skuValueEtcItem.getSkuValuePriceDiscount()/100F);

            nxtStructProductSkuValuePriceEtcList.add(nxtStructProductSkuValuePriceEtc);

        }

        return nxtStructProductSkuValuePriceEtcList;

    }

    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> saveProductAllDetail(NxtStructProduct nxtStructProduct){

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtProduct product;

        if (nxtStructProduct.getId() == null){
            product = new NxtProduct();
        }
        else {
            product = nxtProductService.queryById(nxtStructProduct.getId());
        }

        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        //把第三方图片抓取过来，存放到自己这里
        String productDescription = nxtUploadImageComponent.checkHtmlAndSavePic(nxtStructProduct.getProductDescription());

        /*内容*/
        product.setCategoryId(nxtStructProduct.getCategoryId());
        product.setBrandId(nxtStructProduct.getBrandId());
        product.setProductName(nxtStructProduct.getProductName());
        product.setProductSubtitle(nxtStructProduct.getProductSubtitle());
        product.setDealQuantityMin(nxtStructProduct.getDealQuantityMin());
        product.setDealQuantityMax(nxtStructProduct.getDealQuantityMax());
        product.setFreeShipping(nxtStructProduct.getFreeShipping() ? 1 : 0);
        product.setDeliveryConfigId(nxtStructProduct.getDeliveryConfigId());
        product.setItemNo(nxtStructProduct.getItemNo());
        product.setWithSku(nxtStructProduct.getWithSku() ? 1 : 0);

        product.setPrice((long)Math.round(nxtStructProduct.getPrice()*100));

        product.setPriceDiscount((long)Math.round(nxtStructProduct.getPriceDiscount()*100));
        product.setInventoryQuantity(nxtStructProduct.getInventoryQuantity());
        product.setProductDescription(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForSave(productDescription));
        product.setDatelineCreate(System.currentTimeMillis());
        product.setDatelineUpdated(System.currentTimeMillis());
        product.setIsRecommend(nxtStructProduct.getRecommend() ? 1 : 0);
        product.setIsHot(nxtStructProduct.getHot() ? 1 : 0);
        product.setIsNew(nxtStructProduct.getNew() ? 1 : 0);
        product.setIsSelling(nxtStructProduct.getSelling() ? 1 : 0);

        product.setIsTrash(0);

        if (nxtStructProduct.getId() == null){
            nxtProductService.insert(product);
            product.setSortId(product.getId());
        }

        nxtProductService.update(product);

        List<NxtStructProductSku> skuList = nxtStructProduct.getSkuList();
        List<NxtStructProductSkuValuePriceEtc> skuValuePriceEtcList = nxtStructProduct.getSkuValuePriceEtcList();
        List<NxtStructProductPicture> pictureList = nxtStructProduct.getPictureList();

        //更新产品图片id（先删除全部，再重新插入）
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(product.getId());
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        if (picList != null){
            for (int i = 0; i < picList.size(); i++) {
                NxtProductPicture nxtProductPicture = picList.get(i);
                nxtProductPictureService.deleteById(nxtProductPicture.getId());
            }
        }

        //添加产品图片id
        for (NxtStructProductPicture nxtStructProductPicture :
                pictureList) {
            Long picId = nxtStructProductPicture.getId();
            NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(picId);
            if (nxtUploadfile != null){
                if (nxtUploadfile.getCategoryId().equals(0L)){//检查是不是图片类型
                    NxtProductPicture nxtProductPicture = new NxtProductPicture();
                    nxtProductPicture.setProductId(product.getId());
                    nxtProductPicture.setUploadfileId(nxtUploadfile.getId());
                    //插入关联图片
                    NxtProductPicture nxtProductPictureCreated = nxtProductPictureService.insert(nxtProductPicture);
                    if (nxtProductPictureCreated.getId() != null){
                        //更新排序ID
                        nxtProductPictureCreated.setSortId(nxtProductPictureCreated.getId());
                        nxtProductPictureService.update(nxtProductPictureCreated);
                    }
                }
            }
        }


        //更新产品sku

        //先清除原先的sku
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(product.getId());
        List<NxtProductSku> dbSkuList = nxtProductSkuService.queryAll(nxtProductSkuCondition);
        for (NxtProductSku skuItem :
                dbSkuList) {
            NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
            nxtProductSkuValueCondition.setSkuId(skuItem.getId());
            List<NxtProductSkuValue> dbSkuVauleList = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
            List<Long> skuValueIdList = new ArrayList<>();
            for (NxtProductSkuValue skuValueItem :
                    dbSkuVauleList) {
                skuValueIdList.add(skuValueItem.getId());
                nxtProductSkuValueService.deleteById(skuValueItem.getId());
            }
            nxtProductSkuService.deleteById(skuItem.getId());
            if (skuValueIdList.size() > 0) {
                nxtProductSkuValuePriceEtcService.deleteByValueIdSet(skuValueIdList);
            }
        }

        //再插入接收到的sku
        Map<String,Long> mapValueNameToId = new HashMap<>();
        for (NxtStructProductSku nxtStructProductSku :
                skuList) {
            NxtProductSku nxtProductSku = new NxtProductSku();
            nxtProductSku.setProductId(product.getId());
            nxtProductSku.setSkuKeyName(nxtStructProductSku.getSkuKeyName());
            NxtProductSku nxtProductSkuCreated = nxtProductSkuService.insert(nxtProductSku);
            List<NxtStructProductSkuValue> skuValueList = nxtStructProductSku.getSkuValueList();
            for (NxtStructProductSkuValue nxtStructProductSkuValue :
                    skuValueList) {
                NxtProductSkuValue nxtProductSkuValue = new NxtProductSkuValue();
                nxtProductSkuValue.setSkuId(nxtProductSkuCreated.getId());
                nxtProductSkuValue.setSkuValueName(nxtStructProductSkuValue.getSkuValueName());
                nxtProductSkuValueService.insert(nxtProductSkuValue);
                mapValueNameToId.put(nxtProductSkuValue.getSkuValueName(),nxtProductSkuValue.getId());
            }
        }

        //更新产品sku对应的价格、库存、折扣
        for (NxtStructProductSkuValuePriceEtc skuValuePrictEtc: skuValuePriceEtcList) {
            NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc = new NxtProductSkuValuePriceEtc();
            nxtProductSkuValuePriceEtc.setSkuValueId1(mapValueNameToId.get(skuValuePrictEtc.getSkuValueName1()));
            if (skuValuePrictEtc.getSkuValueName2() != null) {
                nxtProductSkuValuePriceEtc.setSkuValueId2(mapValueNameToId.get(skuValuePrictEtc.getSkuValueName2()));
            }
            else {
                nxtProductSkuValuePriceEtc.setSkuValueId2(0L);
            }
            nxtProductSkuValuePriceEtc.setSkuValueInventoryQuantity(skuValuePrictEtc.getSkuValueInventoryQuantity());
            nxtProductSkuValuePriceEtc.setSkuValuePrice((long) Math.round(skuValuePrictEtc.getSkuValuePrice() * 100));
            nxtProductSkuValuePriceEtc.setSkuValuePriceDiscount((long) Math.round(skuValuePrictEtc.getSkuValuePriceDiscount() * 100));
            nxtProductSkuValuePriceEtcService.insert(nxtProductSkuValuePriceEtc);
        }

        result.put("detail",getProductAllDetail(product));

        return result;

    }

}
