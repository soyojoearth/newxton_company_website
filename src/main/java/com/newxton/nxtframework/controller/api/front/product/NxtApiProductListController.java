package com.newxton.nxtframework.controller.api.front.product;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductCategory;
import com.newxton.nxtframework.entity.NxtProductPicture;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtProductCategoryService;
import com.newxton.nxtframework.service.NxtProductPictureService;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiProductListController {

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping("/api/product_list")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        String categoryName = jsonParam.getString("categoryName");
        Long rootCategoryId = jsonParam.getLong("rootCategoryId");
        Long categoryId = jsonParam.getLong("categoryId");
        Integer offset = jsonParam.getInteger("offset");
        Integer limit = jsonParam.getInteger("limit");
        Integer requirePages = jsonParam.getInteger("requirePages");
        String searchKeyword = jsonParam.getString("searchKeyword");

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
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {

            List<NxtProduct> list;

            if (categoryIdList.size() > 0) {
                list = this.nxtProductService.selectByCategoryIdSet(offset, limit, categoryIdList);
            }
            else {
                list = this.nxtProductService.queryAllByLimit(offset, limit);
            }

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

            List<NxtProduct> list = this.nxtProductService.searchAllByLimit(offset,limit,"%"+searchKeyword+"%");

            List<Map<String,Object>> listProduct = setProductListWithFirstPicture(list);

            data.put("productList", listProduct);

            if (requirePages != null && requirePages > 0) {
                //分页统计
                Long count = nxtProductService.searchAllCount("%" + searchKeyword + "%");
                Long pages = (long) Math.ceil((double) count / (double) limit);

                data.put("pages", pages);
            }

        }

        return new NxtStructApiResult(data);

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


        for (NxtProduct product : productList) {
            String[] tags = {};
            Map<String,Object> item = new HashMap<>();
            item.put("id",product.getId());
            item.put("productName",product.getProductName());
            item.put("productSubtitle",product.getProductSubtitle());
            item.put("productTags",product.getProductTags() != null ? product.getProductTags().split(",") : tags);
            item.put("productRatings", product.getProductRatings() != null ? product.getProductRatings() / 10F : null);
            item.put("externalUrl",product.getExternalUrl());
            if (mapProductFirstPicture.containsKey(product.getId())){
                item.put("picUrl",nxtUploadImageComponent.convertImagePathToDomainImagePath(mapProductFirstPicture.get(product.getId())));
                item.put("picUrlFull",nxtUploadImageComponent.convertImagePathToFullDomainImagePath(mapProductFirstPicture.get(product.getId())));
            }
            else {
                item.put("picUrl","/common/images/empty.png");
                item.put("picUrlFull",nxtUploadImageComponent.convertImagePathToFullDomainImagePath("/common/images/empty.png"));
            }
            resultList.add(item);
        }

        return resultList;

    }


}
