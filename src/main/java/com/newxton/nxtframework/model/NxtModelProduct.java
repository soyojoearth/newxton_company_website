package com.newxton.nxtframework.model;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.springframework.stereotype.Component;

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

    public Map<String, Object> productAllDetail(NxtProduct nxtProduct) {

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
            skuValueEtcItemMap.put("id",skuValueEtcItem.getId());
            skuValueEtcItemMap.put("skuValueId1",skuValueEtcItem.getSkuValueId1());
            skuValueEtcItemMap.put("skuValueName1",skuValueIdToNameMap.get(skuValueEtcItem.getSkuValueId1()));
            skuValueEtcItemMap.put("skuValueId2",skuValueEtcItem.getSkuValueId2());
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

}
