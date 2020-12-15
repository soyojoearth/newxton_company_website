package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductCategory;
import com.newxton.nxtframework.entity.NxtProductPicture;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtProductCategoryService;
import com.newxton.nxtframework.service.NxtProductPictureService;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructSettingCommission;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductListV2Controller {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @RequestMapping(value = "/api/admin/product/list/v2", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        Long categoryId = jsonParam.getLong("categoryId");
        String searchKeyword = jsonParam.getString("searchKeyword");

        Boolean isRecommend = jsonParam.getBoolean("isRecommend");
        Boolean isNew = jsonParam.getBoolean("isNew");
        Boolean isHot = jsonParam.getBoolean("isHot");
        Boolean isSelling = jsonParam.getBoolean("isSelling");
        Boolean isTrash = jsonParam.getBoolean("isTrash");

        if (offset == null || limit==null){
            return new NxtStructApiResult(53,"缺少：offset或limit").toMap();
        }

        if (searchKeyword != null && searchKeyword.trim().isEmpty()){
            searchKeyword = null;
        }

        List<NxtProduct> nxtProductList = nxtProductService.adminQueryAllByLimit(offset,limit,categoryId,searchKeyword,isRecommend,isNew,isHot,isSelling,isTrash);


        /*先取出所有分类做备用*/
        List<NxtProductCategory> categoryList = nxtProductCategoryService.queryAll(new NxtProductCategory());
        Map<Long,String> categoryNameMap = new HashMap<>();
        for (NxtProductCategory category: categoryList) {
            categoryNameMap.put(category.getId(),category.getCategoryName());
        }

        //默认佣金比率
        NxtStructSettingCommission nxtStructSettingCommission = nxtGlobalSettingComponent.getNxtStructSettingCommission();

        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < nxtProductList.size(); i++) {
            NxtProduct content = nxtProductList.get(i);
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
            item.put("priceMarket",content.getPrice()/100F);
            item.put("priceSelling",content.getPrice()*(content.getPriceDiscount()/100F)/100F);
            item.put("itemNo",content.getItemNo());
            item.put("salsCount",content.getSalsCount());
            item.put("inventoryQuantity",content.getInventoryQuantity());
            item.put("commissionRate",content.getCommissionRate() == null ? nxtStructSettingCommission.getDefaultProductCommissionRate() : content.getCommissionRate());
            item.put("datelineUpdated",content.getDatelineUpdated());
            item.put("datelineUpdatedReadable",sdf.format(new Date(content.getDatelineUpdated())));
            item.put("datelineCreate",content.getDatelineCreate());
            item.put("datelineCreateReadable",sdf.format(new Date(content.getDatelineCreate())));
            item.put("isRecommend",content.getIsRecommend());
            item.put("isHot",content.getIsHot());
            item.put("isNew",content.getIsNew());
            item.put("isSelling",content.getIsSelling());
            item.put("isTrash",content.getIsTrash());

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

        Map<String,Object> data = new HashMap<>();

        data.put("list",listResult);

        Long count = nxtProductService.adminCountAll(categoryId,searchKeyword,isRecommend,isNew,isHot,isSelling,isTrash);
        data.put("count",count);

        return new NxtStructApiResult(data).toMap();

    }

}
