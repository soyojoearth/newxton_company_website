package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.process.NxtProcessProduct;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructProduct;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiProductDetailController  {

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

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @Resource
    private NxtProcessProduct nxtProcessProduct;

    @RequestMapping("/api/product_detail")
    public Map<String,Object> exec(@RequestBody JSONObject jsonParam) {

        Long productId = jsonParam.getLong("product_id");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtProduct nxtProduct = nxtProductService.queryById(productId);

        if (nxtProduct == null){
            result.put("detail",new NxtStructProduct());
            return result;
        }

        NxtStructProduct nxtStructProduct = nxtProcessProduct.getProductAllDetail(nxtProduct);

        result.put("detail",nxtStructProduct);

        return result;

    }

}
