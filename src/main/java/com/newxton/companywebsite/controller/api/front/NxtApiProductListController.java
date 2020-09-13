package com.newxton.companywebsite.controller.api.front;

import com.newxton.companywebsite.entity.*;
import com.newxton.companywebsite.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiProductListController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping("/api/product_list")
    public Map<String,Object> index(
            @RequestParam(value = "category_name",required = false) String categoryName,
            @RequestParam(value = "root_category_id",required = false) Long rootCategoryId,
            @RequestParam(value = "category_id",required = false) Long categoryId,
            @RequestParam(value = "offset",required = false) Integer offset,
            @RequestParam(value = "limit",required = false) Integer limit,
            @RequestParam(value = "require_pages",required = false) Integer requirePages,
            @RequestParam(value = "search_keyword",required = false) String search_keyword
    ) {


        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        Map<String, Object> data = new HashMap<>();

        if (limit == null || limit < 1){
            limit = 16;
        }

        if (offset == null || offset < 0){
            offset = 0;
        }


        List<Long> categoryIdList = new ArrayList<>();


        if (rootCategoryId != null && rootCategoryId > 0){//某顶级分类的全部子孙分类（含自己）
            //产品类别
            List<NxtProductCategory> productCategoryList = nxtProductCategoryService.queryAll(new NxtProductCategory());
            NxtProductCategory productCategory1 = nxtProductCategoryService.queryById(rootCategoryId);//顶级分类
            categoryIdList = findSubCategory(productCategory1, productCategoryList);//寻找全部子孙类别
            categoryIdList.add(rootCategoryId);

            NxtProductCategory productCategory = nxtProductCategoryService.queryById(rootCategoryId);
            data.put("categoryName",productCategory.getCategoryName());

        }
        else if (categoryId != null && categoryId > 0){//单独类别
            categoryIdList.add(categoryId);
            NxtProductCategory productCategory = nxtProductCategoryService.queryById(categoryId);
            data.put("categoryName",productCategory.getCategoryName());
        }
        else if (categoryName != null && !categoryName.trim().isEmpty()){
            NxtProductCategory category = nxtProductCategoryService.queryByName(categoryName);
            if (category != null){
                categoryId = category.getId();
                categoryIdList.add(categoryId);
                data.put("categoryName",category.getCategoryName());
            }
        }
        else {
            //产品类别
            List<NxtProductCategory> productCategoryList = nxtProductCategoryService.queryAll(new NxtProductCategory());
            //全部类别
            for (NxtProductCategory productCategory :
                    productCategoryList) {
                categoryIdList.add(productCategory.getId());
            }
        }

        //仅按分类筛选
        if (search_keyword == null || search_keyword.trim().isEmpty()) {

            List<NxtProduct> list = this.nxtProductService.selectByCategoryIdSet(offset,limit,categoryIdList);

            List<Map<String, Object>> productList = setProductListWithFirstPicture(list);

            data.put("productList", productList);

            if (requirePages != null && requirePages > 0) {
                //分页统计
                Long count = nxtProductService.countByCategoryIdSet(categoryIdList);
                Long pages = (long) Math.ceil((double)count / (double)limit);
                data.put("pages", pages);
            }

        }
        else {//仅按关键词筛选

            List<NxtProduct> list = this.nxtProductService.searchAllByLimit(offset,limit,"%"+search_keyword+"%");

            List<Map<String,Object>> listProduct = setProductListWithFirstPicture(list);

            data.put("productList", listProduct);

            if (requirePages != null && requirePages > 0) {
                //分页统计
                Long count = nxtProductService.searchAllCount("%" + search_keyword + "%");
                Long pages = (long) Math.ceil((double) count / (double) limit);

                data.put("pages", pages);
            }

        }

        result.put("data",data);

        return result;

    }

    /**
     * 遍历搜寻子孙类别的全部Id
     * @param categoryParent
     * @param list
     * @return
     */
    private List<Long> findSubCategory(NxtProductCategory categoryParent, List<NxtProductCategory> list){

        List<Long> resultIdList = new ArrayList<>();

        if (categoryParent == null){
            return resultIdList;
        }

        for (int i = 0; i < list.size(); i++) {
            NxtProductCategory category = list.get(i);
            if (category.getCategoryPid().equals(categoryParent.getId())){
                resultIdList.add(category.getId());
                resultIdList.addAll(findSubCategory(category,list));
            }
        }

        return resultIdList;

    }


    /**
     * 根据产品列表，加上产品图片返回
     * @param productList
     * @return
     */
    private List<Map<String,Object>> setProductListWithFirstPicture(List<NxtProduct> productList){

        List<Map<String,Object>> resultList = new ArrayList<>();

        if (productList.size() == 0){
            return resultList;
        }

        List<Long> listProductId = new ArrayList<>();

        for (NxtProduct product:
                productList) {
            listProductId.add(product.getId());
        }

        List<NxtProductPicture> listProductPicture = nxtProductPictureService.selectByProductIdSet(0,999,listProductId);

        //上传文件id -> 产品id
        Map<Long,Long> mapUploadFileId2ProductId = new HashMap<>();

        List<Long> listUploadFileId = new ArrayList<>();

        for (NxtProductPicture productPicture:
                listProductPicture) {
            listUploadFileId.add(productPicture.getUploadfileId());
            mapUploadFileId2ProductId.put(productPicture.getUploadfileId(),productPicture.getProductId());
        }

        //产品首图
        Map<Long,String> mapProductFirstPicture = new HashMap<>();

        if (listProductPicture.size() > 0){
            List<NxtUploadfile> listUploadFile = nxtUploadfileService.selectByIdSet(0,999,listUploadFileId);

            for (NxtUploadfile uploadFile :
                    listUploadFile) {
                if(mapUploadFileId2ProductId.containsKey(uploadFile.getId())){
                    Long productIdOfUploadfile = mapUploadFileId2ProductId.get(uploadFile.getId());
                    if (!mapProductFirstPicture.containsKey(productIdOfUploadfile)){
                        mapProductFirstPicture.put(productIdOfUploadfile,uploadFile.getUrlpath());
                    }
                }
            }
        }


        for (NxtProduct product:
                productList) {
            Map<String,Object> item = new HashMap<>();
            item.put("id",product.getId());
            item.put("productName",product.getProductName());
            item.put("productSubtitle",product.getProductSubtitle());
            if (mapProductFirstPicture.containsKey(product.getId())){
                item.put("picUrl",this.qiniuDomain + mapProductFirstPicture.get(product.getId()));
            }
            else {
                item.put("picUrl","");
            }
            resultList.add(item);
        }

        return resultList;

    }


}
