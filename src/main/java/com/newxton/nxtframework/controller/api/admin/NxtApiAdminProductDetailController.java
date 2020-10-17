package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.controller.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductSku;
import com.newxton.nxtframework.entity.NxtProductSkuValue;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.service.NxtProductSkuService;
import com.newxton.nxtframework.service.NxtProductSkuValueService;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductDetailController {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/product/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtProduct content = nxtProductService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> item = new HashMap<>();
        item.put("id",content.getId());
        item.put("categoryId",content.getCategoryId());
        item.put("productName",content.getProductName());
        item.put("productSubtitle",content.getProductSubtitle());
        if (content.getPrice() != null) {
            item.put("price", content.getPrice() / 100F);
        }
        else {
            item.put("price", null);
        }
        item.put("priceNegotiation",content.getPriceNegotiation());
        item.put("priceRemark",content.getPriceRemark());
        item.put("productSubtitle",content.getProductSubtitle());
        item.put("productDescription",nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(content.getProductDescription()));
        item.put("datelineUpdated",content.getDatelineUpdated());
        item.put("datelineUpdatedReadable",sdf.format(new Date(content.getDatelineUpdated())));
        item.put("datelineCreate",content.getDatelineCreate());
        item.put("datelineCreateReadable",sdf.format(new Date(content.getDatelineCreate())));
        item.put("isRecommend",content.getDatelineCreate());

        result.put("detail",item);

        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(content.getId());

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

        return result;

    }

}
