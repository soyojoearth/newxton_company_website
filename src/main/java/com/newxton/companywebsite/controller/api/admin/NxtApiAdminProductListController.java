package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtProduct;
import com.newxton.companywebsite.entity.NxtProductPicture;
import com.newxton.companywebsite.entity.NxtUploadfile;
import com.newxton.companywebsite.service.NxtProductPictureService;
import com.newxton.companywebsite.service.NxtProductService;
import com.newxton.companywebsite.service.NxtUploadfileService;
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
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/product/list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(value = "page_number", required = false) Integer pageNumber
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        List<NxtProduct> list = nxtProductService.selectAllByLimit(offset,limit,categoryId);
        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            NxtProduct content = list.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("id",content.getId());
            item.put("categoryId",content.getCategoryId());
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

        return result;

    }

}
