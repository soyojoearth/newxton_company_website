package com.newxton.nxtframework.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/3
 * @address Shenzhen, China
 */
@Component
public class NxtModelProduct {

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

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    public Map<String, Object> getProductAllDetail(NxtProduct nxtProduct) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> allDetail = new HashMap<>();
        allDetail.put("id", nxtProduct.getId());
        allDetail.put("categoryId", nxtProduct.getCategoryId());
        allDetail.put("brandId", nxtProduct.getBrandId());
        allDetail.put("productName", nxtProduct.getProductName());
        allDetail.put("productSubtitle", nxtProduct.getProductSubtitle());
        allDetail.put("dealQuantityMin", nxtProduct.getDealQuantityMin());
        allDetail.put("dealQuantityMax", nxtProduct.getDealQuantityMax());
        allDetail.put("freeShipping", nxtProduct.getFreeShipping() != null && nxtProduct.getFreeShipping() > 0);
        allDetail.put("deliveryConfigId", nxtProduct.getDeliveryConfigId());
        allDetail.put("itemNo", nxtProduct.getItemNo());
        allDetail.put("withSku", nxtProduct.getWithSku() != null && nxtProduct.getWithSku() > 0);

        if (nxtProduct.getPrice() != null) {
            allDetail.put("price", nxtProduct.getPrice() / 100F);
        } else {
            allDetail.put("price", 0);
        }

        allDetail.put("priceDiscount", nxtProduct.getPriceDiscount());
        allDetail.put("inventoryQuantity", nxtProduct.getInventoryQuantity());
        allDetail.put("productDescription", nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(nxtProduct.getProductDescription()));
        allDetail.put("datelineUpdated", nxtProduct.getDatelineUpdated());
        allDetail.put("datelineUpdatedReadable", sdf.format(new Date(nxtProduct.getDatelineUpdated())));
        allDetail.put("datelineCreate", nxtProduct.getDatelineCreate());
        allDetail.put("datelineCreateReadable", sdf.format(new Date(nxtProduct.getDatelineCreate())));
        allDetail.put("isRecommend", nxtProduct.getIsRecommend() != null && nxtProduct.getIsRecommend() > 0);
        allDetail.put("isHot", nxtProduct.getIsHot() != null && nxtProduct.getIsHot() > 0);
        allDetail.put("isNew", nxtProduct.getIsNew() != null && nxtProduct.getIsNew() > 0);
        allDetail.put("isSelling", nxtProduct.getIsSelling() != null && nxtProduct.getIsSelling() > 0);
        allDetail.put("isTrash", nxtProduct.getIsTrash() != null && nxtProduct.getIsTrash() > 0);


        /*查询产品图片*/
        List<Map<String, Object>> pictureList = new ArrayList<>();
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(nxtProduct.getId());
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        List<Long> picUploadfileIdList = new ArrayList<>();
        for (NxtProductPicture nxtProductPicture : picList) {
            picUploadfileIdList.add(nxtProductPicture.getUploadfileId());
        }
        List<NxtUploadfile> uploadfileList = nxtUploadfileService.selectByIdSet(0, Integer.MAX_VALUE, picUploadfileIdList);
        for (NxtUploadfile nxtUploadfile : uploadfileList) {
            Map<String, Object> picItem = new HashMap<>();
            picItem.put("id", nxtUploadfile.getId());
            picItem.put("url", nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
            pictureList.add(picItem);
        }
        allDetail.put("pictureList", pictureList);

        //查询 productSkuList
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(nxtProduct.getId());
        List<NxtProductSku> listSku = nxtProductSkuService.queryAll(nxtProductSkuCondition);
        List<Map<String, Object>> productSkuList = new ArrayList<>();
        List<Long> skuIdList = new ArrayList<>();

        for (NxtProductSku productSku :
                listSku) {
            skuIdList.add(productSku.getId());
            Map<String, Object> itemSkuMap = new HashMap<>();
            itemSkuMap.put("skuId", productSku.getId());
            itemSkuMap.put("skuKeyName", productSku.getSkuKeyName());
            itemSkuMap.put("skuValueList", new ArrayList<>());
            productSkuList.add(itemSkuMap);
        }
        List<Long> skuValueIdList = new ArrayList<>();
        Map<Long,String> skuValueIdToNameMap = new HashMap<>();
        List<NxtProductSkuValue> nxtProductSkuValueList = nxtProductSkuValueService.selectBySkuIdSet(0, Integer.MAX_VALUE, skuIdList);
        for (NxtProductSkuValue skuValue :
                nxtProductSkuValueList) {
            skuValueIdList.add(skuValue.getId());
            skuValueIdToNameMap.put(skuValue.getId(),skuValue.getSkuValueName());
            for (Map<String, Object> itemSkuMap :
                    productSkuList) {
                if (Long.valueOf(itemSkuMap.get("skuId").toString()).equals(skuValue.getSkuId())) {
                    List<NxtProductSkuValue> skuValueList = (List<NxtProductSkuValue>) itemSkuMap.get("skuValueList");
                    skuValueList.add(skuValue);
                }
            }
        }
        allDetail.put("skuList", productSkuList);

        //获取sku的价格、库存、折扣
        List<Map<String, Object>> skuValuePriceEtcList = new ArrayList<>();
        List<NxtProductSkuValuePriceEtc> nxtProductSkuValuePriceEtcList = nxtProductSkuValuePriceEtcService.selectByValueIdSet(0, Integer.MAX_VALUE, skuValueIdList);

        for (NxtProductSkuValuePriceEtc skuValueEtcItem:nxtProductSkuValuePriceEtcList) {
            Map<String,Object> skuValueEtcItemMap = new HashMap<>();
            //skuValueEtcItemMap.put("id",skuValueEtcItem.getId());
            //skuValueEtcItemMap.put("skuValueId1",skuValueEtcItem.getSkuValueId1());
            skuValueEtcItemMap.put("skuValueName1",skuValueIdToNameMap.get(skuValueEtcItem.getSkuValueId1()));
            //skuValueEtcItemMap.put("skuValueId2",skuValueEtcItem.getSkuValueId2());
            if (skuValueEtcItem.getSkuValueId2() > 0) {
                skuValueEtcItemMap.put("skuValueName2", skuValueIdToNameMap.get(skuValueEtcItem.getSkuValueId2()));
            }
            else {
                skuValueEtcItemMap.put("skuValueName2",null);
            }
            skuValueEtcItemMap.put("skuValueInventoryQuantity",skuValueEtcItem.getSkuValueInventoryQuantity());
            skuValueEtcItemMap.put("skuValuePrice",skuValueEtcItem.getSkuValuePrice()/100F);
            skuValueEtcItemMap.put("skuValuePriceDiscount",skuValueEtcItem.getSkuValuePriceDiscount());
            skuValuePriceEtcList.add(skuValueEtcItemMap);
        }
        allDetail.put("skuValuePriceEtcList", skuValuePriceEtcList);


        return allDetail;

    }

    @Transactional
    public Map<String, Object> saveProductAllDetail(JSONObject jsonParam){

        Long id = jsonParam.getLong("id");
        Long categoryId = jsonParam.getLong("categoryId");
        Long brandId = jsonParam.getLong("brandId");
        String productName = jsonParam.getString("productName");
        String productSubtitle = jsonParam.getString("productSubtitle");
        Long dealQuantityMin = jsonParam.getLong("dealQuantityMin");
        Long dealQuantityMax = jsonParam.getLong("dealQuantityMax");
        Boolean freeShipping = jsonParam.getBoolean("freeShipping");
        Long deliveryConfigId = jsonParam.getLong("deliveryConfigId");
        String itemNo = jsonParam.getString("itemNo");
        Boolean withSku = jsonParam.getBoolean("withSku");
        Float price = jsonParam.getFloat("price");
        Long priceDiscount= jsonParam.getLong("priceDiscount");
        Long inventoryQuantity = jsonParam.getLong("inventoryQuantity");
        String productDescription = jsonParam.getString("productDescription");
        Boolean isRecommend = jsonParam.getBoolean("isRecommend");
        Boolean isHot = jsonParam.getBoolean("isHot");
        Boolean isNew = jsonParam.getBoolean("isNew");
        Boolean isSelling = jsonParam.getBoolean("isSelling");
        Boolean isTrash = jsonParam.getBoolean("isTrash");
        JSONArray skuList = jsonParam.getJSONArray("skuList");
        JSONArray skuValuePriceEtcList = jsonParam.getJSONArray("skuValuePriceEtcList");
        JSONArray pictureList = jsonParam.getJSONArray("pictureList");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null || productName == null || productDescription == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }


        /*检查分类*/
        if (categoryId > 0) {
            NxtProductCategory category = nxtProductCategoryService.queryById(categoryId);
            if (category == null) {
                result.put("status", 48);
                result.put("message", "该类别不存在");
                return result;
            }
        }

        NxtProduct product;

        if (id == null){
            product = nxtProductService.queryById(id);
        }
        else {
            product = new NxtProduct();
        }

        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        //把第三方图片抓取过来，存放到自己这里
        productDescription = nxtUploadImageComponent.checkHtmlAndSavePic(productDescription);

        /*内容*/
        product.setCategoryId(categoryId);
        product.setBrandId(brandId);
        product.setProductName(productName);
        product.setProductSubtitle(productSubtitle);
        product.setDealQuantityMin(dealQuantityMin);
        product.setDealQuantityMax(dealQuantityMax);
        product.setFreeShipping(freeShipping ? 1 : 0);
        product.setDeliveryConfigId(deliveryConfigId);
        product.setItemNo(itemNo);
        product.setWithSku(withSku ? 1 : 0);

        if (nxtUtilComponent.isNumeric(price.toString().trim())) {
            Long priceLong = (long) (Float.parseFloat(price.toString().trim()) * 100);
            product.setPrice(priceLong);
        }

        product.setPriceDiscount(priceDiscount);
        product.setInventoryQuantity(inventoryQuantity);
        product.setProductDescription(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForSave(productDescription));
        product.setDatelineCreate(System.currentTimeMillis());
        product.setDatelineUpdated(System.currentTimeMillis());
        product.setIsRecommend(isRecommend ? 1 : 0);
        product.setIsHot(isHot ? 1 : 0);
        product.setIsNew(isNew ? 1 : 0);
        product.setIsSelling(isSelling ? 1 : 0);

        if (id == null){
            nxtProductService.insert(product);
        }
        else {
            nxtProductService.update(product);
        }

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
        if (pictureList != null){
            for (int i = 0; i < pictureList.size(); i++) {
                JSONObject item = pictureList.getJSONObject(i);
                Long picId = item.getLong("id");
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
        }



        //更新产品sku
        if(skuList != null){

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
            for (int i = 0; i < skuList.size(); i++) {
                JSONObject skuItem = skuList.getJSONObject(i);
                NxtProductSku nxtProductSku = new NxtProductSku();
                nxtProductSku.setProductId(product.getId());
                nxtProductSku.setSkuKeyName(skuItem.getString("skuKeyName"));
                NxtProductSku nxtProductSkuCreated = nxtProductSkuService.insert(nxtProductSku);
                List<Map<String,Object>> skuValueList = (List<Map<String,Object>>)skuItem.get("skuValueList");
                for (Map<String,Object> skuValue :
                        skuValueList) {
                    NxtProductSkuValue nxtProductSkuValue = new NxtProductSkuValue();
                    nxtProductSkuValue.setSkuId(nxtProductSkuCreated.getId());
                    nxtProductSkuValue.setSkuValueName(skuValue.get("skuValueName").toString());
                    nxtProductSkuValueService.insert(nxtProductSkuValue);
                    mapValueNameToId.put(nxtProductSkuValue.getSkuValueName(),nxtProductSkuValue.getId());
                }
            }

            //更新产品sku对应的价格、库存、折扣
            for (Object skuValuePrictEtc: skuValuePriceEtcList) {
                Map<String,Object> etcItem = (Map<String,Object>)skuValuePrictEtc;
                NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc = new NxtProductSkuValuePriceEtc();
                nxtProductSkuValuePriceEtc.setSkuValueId1(mapValueNameToId.get(etcItem.get("skuValueName1")));
                if (etcItem.get("skuValueName2") != null) {
                    nxtProductSkuValuePriceEtc.setSkuValueId2(mapValueNameToId.get(etcItem.get("skuValueName2")));
                }
                else {
                    nxtProductSkuValuePriceEtc.setSkuValueId2(0L);
                }
                nxtProductSkuValuePriceEtc.setSkuValueInventoryQuantity(Long.valueOf(etcItem.get("skuValueInventoryQuantity").toString()));
                nxtProductSkuValuePriceEtc.setSkuValuePrice((long) (Float.parseFloat(etcItem.get("skuValuePrice").toString().trim()) * 100));
                nxtProductSkuValuePriceEtc.setSkuValuePriceDiscount((long) (Float.parseFloat(etcItem.get("skuValuePriceDiscount").toString().trim()) * 100));
                nxtProductSkuValuePriceEtcService.insert(nxtProductSkuValuePriceEtc);
            }

        }

        result.put("detail",getProductAllDetail(product));

        return result;

    }

}
