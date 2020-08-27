package com.newxton.companywebsite.controller.api.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.companywebsite.entity.*;
import com.newxton.companywebsite.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminProductUpdateController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @RequestMapping(value = "/api/admin/product/update", method = RequestMethod.POST)
    public Map<String, Object> index(
                                    @RequestParam(value = "id", required=false) Long id,
                                    @RequestParam(value = "category_id", required=false) Long categoryId,
                                     @RequestParam(value = "product_name", required=false) String productName,
                                     @RequestParam(value = "product_description", required=false) String productDescription,
                                     @RequestParam(value = "product_picture_list", required=false) String productPictureList,
                                     @RequestParam(value = "is_recommend", required=false) Integer isRecommend,
                                    @RequestParam(value = "product_sku", required=false) String productSku,
                                    @RequestParam(value = "price", required=false) Float price,
                                    @RequestParam(value = "price_negotiation", required=false) Integer priceNegotiation,
                                    @RequestParam(value = "price_remark", required=false) String priceRemark,
                                    @RequestParam(value = "product_subtitle", required=false) String productSubtitle
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null || productName == null || productDescription == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        if (categoryId == null){
            categoryId = 0L;
        }

        if (isRecommend == null){
            isRecommend = 0;
        }
        if (!isRecommend.equals(0)){
            isRecommend = 1;
        }

        productName = productName.trim();

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

        /*更新内容*/
        product.setCategoryId(categoryId);
        product.setProductName(productName);
        product.setProductDescription(productDescription.replace(this.qiniuDomain,"http://newxton-image-domain"));
        product.setDatelineCreate(System.currentTimeMillis());
        product.setDatelineUpdated(product.getDatelineCreate());
        product.setIsRecommend(isRecommend);

        if (price != null){
            Long priceLong = (long)(price * 100);
            product.setPrice(priceLong);
        }
        if (priceNegotiation != null){
            product.setPriceNegotiation(priceNegotiation);
        }
        if (priceRemark != null){
            product.setPriceRemark(priceRemark);
        }
        if (productSubtitle != null){
            product.setProductSubtitle(productSubtitle);
        }

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
        if (productPictureList != null && !productPictureList.trim().isEmpty()){
            Gson gson = new Gson();
            List<Long> productPictureIdList = gson.fromJson(productPictureList,new TypeToken<List<Long>>(){}.getType());
            for (int i = 0; i < productPictureIdList.size(); i++) {
                Long picId = productPictureIdList.get(i);
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



        //更新产品属性
        if(productSku != null && !productSku.trim().isEmpty()){

            //先清除原先的属性
            NxtProductSku nxtProductSkuCondition = new NxtProductSku();
            nxtProductSkuCondition.setProductId(product.getId());
            List<NxtProductSku> dbSkuList = nxtProductSkuService.queryAll(nxtProductSkuCondition);
            for (NxtProductSku skuItem :
                    dbSkuList) {
                NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
                nxtProductSkuValueCondition.setSkuId(skuItem.getId());
                List<NxtProductSkuValue> dbSkuVauleList = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
                for (NxtProductSkuValue skuValueItem :
                        dbSkuVauleList) {
                    nxtProductSkuValueService.deleteById(skuValueItem.getId());
                }
                nxtProductSkuService.deleteById(skuItem.getId());
            }

            //再插入接收到的属性
            Gson gson = new Gson();
            List<Map<String,Object>> productSkuList = gson.fromJson(productSku,new TypeToken<List<Map<String,Object>>>(){}.getType());
            for (Map<String, Object> skuItem :
                    productSkuList) {
                NxtProductSku nxtProductSku = new NxtProductSku();
                nxtProductSku.setProductId(product.getId());
                nxtProductSku.setSkuName(skuItem.get("name").toString());
                NxtProductSku nxtProductSkuCreated = nxtProductSkuService.insert(nxtProductSku);
                List<String> skuValueList = (List<String>)skuItem.get("sku");
                for (String skuValue :
                        skuValueList) {
                    NxtProductSkuValue nxtProductSkuValue = new NxtProductSkuValue();
                    nxtProductSkuValue.setSkuId(nxtProductSkuCreated.getId());
                    nxtProductSkuValue.setSkuValue(skuValue);
                    nxtProductSkuValueService.insert(nxtProductSkuValue);
                }
            }
        }

        return result;

    }

}
