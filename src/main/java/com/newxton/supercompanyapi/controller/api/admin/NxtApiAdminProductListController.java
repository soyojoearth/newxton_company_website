package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtProduct;
import com.newxton.supercompanyapi.entity.NxtProductCategory;
import com.newxton.supercompanyapi.entity.NxtProductPicture;
import com.newxton.supercompanyapi.entity.NxtUploadfile;
import com.newxton.supercompanyapi.service.NxtProductCategoryService;
import com.newxton.supercompanyapi.service.NxtProductPictureService;
import com.newxton.supercompanyapi.service.NxtProductService;
import com.newxton.supercompanyapi.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminProductListController {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/product/list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "is_recommend", required = false) Integer isRecommend,
            @RequestParam(value = "search_keyword", required = false) String searchKeyword
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先取出所有分类做备用*/
        List<NxtProductCategory> categoryList = nxtProductCategoryService.queryAll(new NxtProductCategory());
        Map<Long,String> categoryNameMap = new HashMap<>();
        for (NxtProductCategory category: categoryList) {
            categoryNameMap.put(category.getId(),category.getCategoryName());
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        Map<String,Object> mapCondition = new HashMap<>();

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            mapCondition.put("productName","%"+searchKeyword.trim()+"%");
        }
        if (isRecommend != null){
            mapCondition.put("isRecommend",isRecommend);
        }
        if (categoryId != null){
            mapCondition.put("categoryId",categoryId);
        }

        mapCondition.put("offset",offset);
        mapCondition.put("limit",limit);

        List<NxtProduct> list = nxtProductService.searchQueryAllByMap(mapCondition);

        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            NxtProduct content = list.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("id",content.getId());
            item.put("categoryId",content.getCategoryId());
            if (categoryNameMap.containsKey(content.getCategoryId())){
                item.put("categoryName",categoryNameMap.get(content.getCategoryId()));
            }
            else {
                item.put("categoryName","---");
            }
            item.put("pic_url","");
            item.put("productName",content.getProductName());
            item.put("datelineUpdated",content.getDatelineUpdated());
            item.put("datelineUpdatedReadable",sdf.format(new Date(content.getDatelineUpdated())));
            item.put("datelineCreate",content.getDatelineCreate());
            item.put("datelineCreateReadable",sdf.format(new Date(content.getDatelineCreate())));
            item.put("isRecommend",content.getIsRecommend());
            listResult.add(item);

            /*查询产品图片*/
            NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
            nxtProductPictureCondition.setProductId(content.getId());
            List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
            if (picList.size() > 0){
                NxtProductPicture nxtProductPictureFirst = picList.get(0);
                NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(nxtProductPictureFirst.getUploadfileId());
                if (nxtUploadfile != null) {
                    item.put("pic_url", nxtUploadfile.getUrlpath());
                }
            }

        }

        result.put("list",listResult);

        Long totalCount = nxtProductService.countSearchQueryAllByMap(mapCondition);
        Long pageCount =  (long)Math.ceil((float)totalCount / (float)limit);
        result.put("page_count",pageCount);

        return result;

    }

}
