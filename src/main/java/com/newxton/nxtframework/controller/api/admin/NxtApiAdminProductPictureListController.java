package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.controller.base.NxtBaseUploadImageController;
import com.newxton.nxtframework.entity.NxtProductPicture;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtProductPictureService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductPictureListController extends NxtBaseUploadImageController {

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/product/picture_list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "product_id", required = false) Long productId
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (productId == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        List<Map<String,Object>> listResult = new ArrayList<>();

        /*查询产品图片*/
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(productId);
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        for (NxtProductPicture productPicture :
                picList) {
            NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(productPicture.getUploadfileId());
            if (nxtUploadfile != null) {
                Map<String,Object> item = new HashMap<>();
                item.put("id",productPicture.getUploadfileId());
                item.put("url", this.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
                listResult.add(item);
            }
        }


        result.put("list",listResult);

        return result;

    }

}
