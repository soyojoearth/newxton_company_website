package com.newxton.supercompanyapi.controller.api.front;

import com.newxton.supercompanyapi.entity.*;
import com.newxton.supercompanyapi.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiProductDetailController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping("/api/product_detail")
    public Map<String,Object> index(
            @RequestParam(value = "product_id",required = false) Long productId,
            @RequestParam(value = "page",required = false) Long page
            ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        Map<String, Object> data = new HashMap<>();

        result.put("data", data);

        NxtProduct nxtProduct = nxtProductService.queryById(productId);

        if (nxtProduct == null){
            return result;
        }

        if (page == null){
            page = 1L;
        }


        //产品详情
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String description = "";
        String[] descriptionArray = nxtProduct.getProductDescription().split("<p><!-- pagebreak --></p>");
        if (page > descriptionArray.length){
            description = "";
        }
        else {
            description = descriptionArray[page.intValue()-1];
        }

//        description = description.replace("@常见问题@","<a name=\"detail_faq\"></a>");
//        description = description.replace("@办理流程@","<a name=\"detail_process\"></a>");
//        description = description.replace("@认证保障@","<a name=\"detail_safe\"></a>");

        //解析Tabs，依次加入锚点
        List<String> productDescriptionTabs = new ArrayList<>();
        Matcher m = Pattern.compile("@(.*?)@").matcher(description);
        while (m.find()) {
            productDescriptionTabs.add(m.group(1));
        }
        for (int i = 0; i < productDescriptionTabs.size(); i++) {
            description = description.replace("@"+productDescriptionTabs.get(i)+"@","<a name=\"detail_tab_"+i+"\"></a>");
        }


        Map<String, Object> item = new HashMap<>();
        item.put("id",nxtProduct.getId());
        item.put("categoryId",nxtProduct.getCategoryId());
        item.put("productName",nxtProduct.getProductName());
        item.put("productSubtitle",nxtProduct.getProductSubtitle());
        if (nxtProduct.getPrice() != null) {
            item.put("price", nxtProduct.getPrice() / 100F);
        }
        else {
            item.put("price", null);
        }
        item.put("priceNegotiation",nxtProduct.getPriceNegotiation());
        item.put("priceRemark",nxtProduct.getPriceRemark().trim());
        item.put("productSubtitle",nxtProduct.getProductSubtitle());
        item.put("productDescription",description.replace("http://newxton-image-domain",this.qiniuDomain));
        item.put("datelineUpdated",nxtProduct.getDatelineUpdated());
        item.put("datelineUpdatedReadable",sdf.format(new Date(nxtProduct.getDatelineUpdated())));
        item.put("datelineCreate",nxtProduct.getDatelineCreate());
        item.put("datelineCreateReadable",sdf.format(new Date(nxtProduct.getDatelineCreate())));
        item.put("isRecommend",nxtProduct.getDatelineCreate());

        data.put("product", item);

        //Tabs
        item.put("productDescriptionTabs",productDescriptionTabs);


        //SKU 加载
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(nxtProduct.getId());

        List<NxtProductSku> listSku = nxtProductSkuService.queryAll(nxtProductSkuCondition);

        List<Map<String,Object>> resultSkuList = new ArrayList<>();
        for (NxtProductSku productSku :
                listSku) {
            Map<String,Object> itemSkuMap = new HashMap<>();
            itemSkuMap.put("name",productSku.getSkuName());
            List<String> itemSkuValueList = new ArrayList<>();
            Long skuId = productSku.getId();
            NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
            nxtProductSkuValueCondition.setSkuId(skuId);
            List<NxtProductSkuValue> listSkuValue = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
            for (NxtProductSkuValue productSkuValue :
                    listSkuValue) {
                itemSkuValueList.add(productSkuValue.getSkuValue());
            }
            itemSkuMap.put("sku",itemSkuValueList);
            resultSkuList.add(itemSkuMap);
        }

        item.put("product_sku",resultSkuList);

        List<Map<String,Object>> listPicture = getProductPictureList(productId);
        data.put("listPicture", listPicture);
        if (listPicture.size() > 0){
            data.put("firstPicUrl", listPicture.get(0).get("picUrl"));
        }
        else {
            data.put("firstPicUrl", "");
        }

        //所属类别
        List<NxtProductCategory> categoryList = nxtProductCategoryService.queryAll(new NxtProductCategory());

        NxtProductCategory productCategory = nxtProductCategoryService.queryById(nxtProduct.getCategoryId());

        NxtProductCategory rootCategory = getRootCategory(productCategory,categoryList);

        data.put("category",productCategory);
        data.put("rootCategory",rootCategory);


        result.put("data", data);

        return result;

    }

    /**
     * 取得产品图列表
     * @param productId
     * @return
     */
    private List<Map<String,Object>> getProductPictureList(Long productId){

        List<Map<String,Object>> resultList = new ArrayList<>();

        List<Long> listProductId = new ArrayList<>();
        listProductId.add(productId);

        List<NxtProductPicture> listProductPicture = nxtProductPictureService.selectByProductIdSet(0,999,listProductId);

        List<Long> listUploadFileId = new ArrayList<>();

        for (NxtProductPicture productPicture:
                listProductPicture) {
            listUploadFileId.add(productPicture.getUploadfileId());
        }

        //产品图
        if (listProductPicture.size() > 0){
            List<NxtUploadfile> listUploadFile = nxtUploadfileService.selectByIdSet(0,999,listUploadFileId);
            for (NxtUploadfile uploadFile :
                    listUploadFile) {
                Map<String,Object> item = new HashMap<>();
                item.put("picUrl",this.qiniuDomain + uploadFile.getUrlpath());
                resultList.add(item);
            }
        }

        return resultList;

    }

    /**
     * 获取顶级root分类
     * @param productCategory
     * @param categoryList
     * @return
     */
    private NxtProductCategory getRootCategory(NxtProductCategory productCategory, List<NxtProductCategory> categoryList){
        if (productCategory.getCategoryPid().equals(0L)){
            return productCategory;
        }
        for (NxtProductCategory category :
                categoryList) {
            if (category.getId().equals(productCategory.getCategoryPid())){
                if (category.getCategoryPid().equals(0L)){
                    return category;
                }
                else {
                    return getRootCategory(category,categoryList);
                }
            }
        }
        return null;
    }

}
