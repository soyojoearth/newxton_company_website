package com.newxton.nxtframework.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructShoppingCart;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProduct;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProductSku;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 */
@Component
public class NxtProcessShoppingCart {

    @Resource
    private NxtShoppingCartProductService nxtShoppingCartProductService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    public NxtStructShoppingCart allDetail(NxtShoppingCart nxtShoppingCart) throws NxtException{

        Gson gson = new Gson();



        // 查询当前用户当前购物车产品信息
        NxtStructShoppingCart nxtStructShoppingCart = new NxtStructShoppingCart();

        nxtStructShoppingCart.setId(nxtShoppingCart.getId());
        nxtStructShoppingCart.setUserId(nxtShoppingCart.getUserId());
        nxtStructShoppingCart.setGuestToken(nxtShoppingCart.getToken());

        List<Long> productIdList = new ArrayList<>();

        //查询购物车所有产品
        List<NxtShoppingCartProduct> shoppingCartProductList = nxtShoppingCartProductService.queryAllProductByShoppingCartId(nxtShoppingCart.getId());

        for (NxtShoppingCartProduct nxtShoppingCartProduct : shoppingCartProductList) {

            productIdList.add(nxtShoppingCartProduct.getProductId());

            NxtStructShoppingCartProduct nxtStructShoppingCartProduct = new NxtStructShoppingCartProduct();
            nxtStructShoppingCartProduct.setId(nxtShoppingCartProduct.getId());
            nxtStructShoppingCartProduct.setQuantity(nxtShoppingCartProduct.getQuantity());
            nxtStructShoppingCartProduct.setProductId(nxtShoppingCartProduct.getProductId());

            try {
                List<NxtStructShoppingCartProductSku> skuList = gson.fromJson(nxtShoppingCartProduct.getSku(),new TypeToken<List<NxtStructShoppingCartProductSku>>(){}.getType());
                nxtStructShoppingCartProduct.setSku(skuList);
            }
            catch (Exception e){
                throw new NxtException("购物车suk查询解析错误");
            }

            nxtStructShoppingCartProduct.setProductName("---");//默认名称
            nxtStructShoppingCartProduct.setPicUrl("/common/images/empty.png");//默认图片

            nxtStructShoppingCart.getItemList().add(nxtStructShoppingCartProduct);

        }

        Map<Long,String> mapProductPic = new HashMap<>();
        Map<Long,String> mapProductName = new HashMap<>();

        //批量 查产品名称
        List<NxtProduct> productList = nxtProductService.selectByIdSet(0,Integer.MAX_VALUE,productIdList);
        for (NxtProduct nxtProduct : productList) {
            mapProductName.put(nxtProduct.getId(),nxtProduct.getProductName());
        }

        //批量查主图
        Map<Long,Long> mapUploadFileIdToProductId = new HashMap<>();
        Map<Long,Long> mapProductIdToUploadFileId = new HashMap<>();

        List<NxtProductPicture> nxtProductPictureList = nxtProductPictureService.selectByProductIdSet(0,Integer.MAX_VALUE,productIdList);

        for (NxtProductPicture nxtProductPicture : nxtProductPictureList) {
            //只要第一张主图
            if (!mapProductIdToUploadFileId.containsKey(nxtProductPicture.getProductId())) {
                mapProductIdToUploadFileId.put(nxtProductPicture.getProductId(), nxtProductPicture.getUploadfileId());
                mapUploadFileIdToProductId.put(nxtProductPicture.getUploadfileId(),nxtProductPicture.getProductId());
            }
        }

        List<Long> uploadFileIdList = new ArrayList<>();
        uploadFileIdList.addAll(mapUploadFileIdToProductId.keySet());

        if (uploadFileIdList.size() > 0){
            List<NxtUploadfile> uploadfilesList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,uploadFileIdList);
            for (NxtUploadfile nxtUploadfile : uploadfilesList) {
                Long productId = mapUploadFileIdToProductId.get(nxtUploadfile.getId());
                mapProductPic.put(productId,nxtUploadfile.getUrlpath());
            }
        }


        List<NxtStructShoppingCartProduct> nxtStructShoppingCartProductList = nxtStructShoppingCart.getItemList();

        for (NxtStructShoppingCartProduct nxtStructShoppingCartProduct : nxtStructShoppingCartProductList) {
            String productName = mapProductName.get(nxtStructShoppingCartProduct.getId());
            if (productName != null) {
                nxtStructShoppingCartProduct.setProductName(productName);
            }
            String picUrl = mapProductPic.get(nxtStructShoppingCartProduct.getId());
            if (picUrl != null) {
                nxtStructShoppingCartProduct.setPicUrl(picUrl);
            }
        }


        return nxtStructShoppingCart;

    }

}
