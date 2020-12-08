package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.transaction.annotation.Transactional;
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
public class NxtApiAdminProductDeleteController {

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @Resource
    private NxtProductSkuValuePriceEtcService nxtProductSkuValuePriceEtcService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/product/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询，再删除*/
        NxtProduct product = nxtProductService.queryById(id);
        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        //查询订单
        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.queryAllByProductIdLimit(0L,1L,product.getId());
        if (nxtOrderFormProductList.size() > 0){
            return new NxtStructApiResult(53,"该产品已产生订单，请勿删除").toMap();
        }

        //先清除原先的sku
        NxtProductSku nxtProductSkuCondition = new NxtProductSku();
        nxtProductSkuCondition.setProductId(product.getId());
        List<NxtProductSku> dbSkuList = nxtProductSkuService.queryAll(nxtProductSkuCondition);
        for (NxtProductSku skuItem :
                dbSkuList) {
            NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
            nxtProductSkuValueCondition.setSkuId(skuItem.getId());
            List<NxtProductSkuValue> dbSkuVauleList = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
            List<Long> skuValueIdList = new ArrayList<>();
            for (NxtProductSkuValue skuValueItem :
                    dbSkuVauleList) {
                skuValueIdList.add(skuValueItem.getId());
                nxtProductSkuValueService.deleteById(skuValueItem.getId());
            }
            nxtProductSkuService.deleteById(skuItem.getId());
            nxtProductSkuValuePriceEtcService.deleteByValueIdSet(skuValueIdList);
        }

        //产品图片 删除全部
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(product.getId());
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        if (picList != null){
            for (int i = 0; i < picList.size(); i++) {
                NxtProductPicture nxtProductPicture = picList.get(i);
                nxtProductPictureService.deleteById(nxtProductPicture.getId());
            }
        }

        nxtProductService.deleteById(product.getId());

        return result;

    }

}
