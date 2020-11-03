package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductUpdateController {

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductSkuValuePriceEtcService nxtProductSkuValuePriceEtcService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/product/update", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

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

        /*查询Product*/
        NxtProduct product = nxtProductService.queryById(id);
        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        //把第三方图片抓取过来，存放到自己这里
        productDescription = nxtUploadImageComponent.checkHtmlAndSavePic(productDescription);

        /*更新内容*/
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

        if (isNumeric(price.toString().trim())) {
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

        nxtProductService.update(product);

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
                nxtProductSkuValuePriceEtcService.deleteByValueIdSet(skuValueIdList);
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

        return result;

    }

    private boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }


}
