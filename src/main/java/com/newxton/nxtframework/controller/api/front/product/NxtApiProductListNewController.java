package com.newxton.nxtframework.controller.api.front.product;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductPicture;
import com.newxton.nxtframework.entity.NxtUploadfile;
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
public class NxtApiProductListNewController {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping("/api/product_list/new")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Integer limit = jsonParam.getInteger("limit");

        if (limit == null || limit < 1){
            limit = 4;
        }

        List<NxtProduct> list = this.nxtProductService.queryAllNew(limit);

        List<Map<String,Object>> listProduct = setProductListWithFirstPicture(list);

        return new NxtStructApiResult(listProduct);

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
                item.put("picUrl",nxtUploadImageComponent.convertImagePathToDomainImagePath(mapProductFirstPicture.get(product.getId())));
            }
            else {
                item.put("picUrl","");
            }
            resultList.add(item);
        }

        return resultList;

    }

}
