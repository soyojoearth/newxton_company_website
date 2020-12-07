package com.newxton.nxtframework.process;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructShoppingCart;
import com.newxton.nxtframework.struct.NxtStructShoppingCartPOST;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProduct;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProductSku;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 */
@Component
public class NxtProcessShoppingCart {

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

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
            nxtStructShoppingCartProduct.setSelected(nxtShoppingCartProduct.getSelected() > 0);

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
        Map<Long,Float> mapProductPrice = new HashMap<>();

        //批量 查产品名称 & 价格
        List<NxtProduct> productList = nxtProductService.selectByIdSet(0,Integer.MAX_VALUE,productIdList);
        for (NxtProduct nxtProduct : productList) {
            mapProductName.put(nxtProduct.getId(),nxtProduct.getProductName());
            mapProductPrice.put(nxtProduct.getId(),(nxtProduct.getPrice() - nxtProduct.getPriceDiscount())/100F);
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
            Long productId = nxtStructShoppingCartProduct.getProductId();
            String productName = mapProductName.get(productId);
            if (productName != null) {
                nxtStructShoppingCartProduct.setProductName(productName);
            }
            String picUrl = mapProductPic.get(productId);
            if (picUrl != null) {
                nxtStructShoppingCartProduct.setPicUrl(picUrl);
            }
            Float productPrice = mapProductPrice.get(productId);
            if (productPrice != null) {
                nxtStructShoppingCartProduct.setProductPrice(productPrice);
            }
        }


        return nxtStructShoppingCart;

    }

    /**
     * 检查、合并匿名购物车到已登录用户
     */
    public void mergeGuestShoppingCartToUser(String guestToken,Long userId) throws NxtException {

        //查询匿名用户购物车
        NxtShoppingCart nxtShoppingCartGuest = nxtShoppingCartService.queryByToken(guestToken);
        if (nxtShoppingCartGuest == null){
            return;
        }
        if (nxtShoppingCartGuest.getUserId() != null){
            return;
        }

        Gson gson = new Gson();

        //查询已登录用户购物车
        NxtShoppingCart nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);

        if (nxtShoppingCart == null){
            //直接把匿名购物车给这个登录用户
            nxtShoppingCartGuest.setUserId(userId);
            nxtShoppingCartService.update(nxtShoppingCartGuest);
            return;
        }

        //将匿名购物车里的东西，都合并到已登录购物车中
        List<NxtShoppingCartProduct> nxtShoppingCartProductListGuest = nxtShoppingCartProductService.queryAllProductByShoppingCartId(nxtShoppingCartGuest.getId());
        List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllProductByShoppingCartId(nxtShoppingCart.getId());

        //合并之前先把已登录购物车全部取消勾选
        for (NxtShoppingCartProduct item : nxtShoppingCartProductList) {
            if (item.getSelected().equals(1)) {
                item.setSelected(0);
                nxtShoppingCartProductService.update(item);
            }
        }

        //开始合并，合并后只勾选匿名购物车已勾选的物品
        for (NxtShoppingCartProduct itemGuest : nxtShoppingCartProductListGuest) {

            NxtStructShoppingCartProduct cartProductGuest = new NxtStructShoppingCartProduct();
            cartProductGuest.setProductId(itemGuest.getProductId());
            try {
                List<NxtStructShoppingCartProductSku> skuList = gson.fromJson(itemGuest.getSku(),new TypeToken<List<NxtStructShoppingCartProductSku>>(){}.getType());
                cartProductGuest.setSku(skuList);
            }
            catch (Exception e){
                throw new NxtException("merge匿名购物车suk查询解析错误");
            }

            //在已登录购物车里面找找有没有物品和该匿名购物车物品一样
            NxtShoppingCartProduct sameProduct = null;
            for (NxtShoppingCartProduct item : nxtShoppingCartProductList) {
                NxtStructShoppingCartProduct cartProduct = new NxtStructShoppingCartProduct();
                cartProduct.setProductId(item.getProductId());
                try {
                    List<NxtStructShoppingCartProductSku> skuList = gson.fromJson(item.getSku(),new TypeToken<List<NxtStructShoppingCartProductSku>>(){}.getType());
                    cartProduct.setSku(skuList);
                }
                catch (Exception e){
                    throw new NxtException("merge匿名购物车suk查询解析错误");
                }
                if (cartProduct.isSameProductWithSku(cartProductGuest)){
                    sameProduct = item;
                }
            }

            if (sameProduct != null){
                //判断数量是不是需要更新
                if (sameProduct.getQuantity() < itemGuest.getQuantity()){
                    sameProduct.setQuantity(itemGuest.getQuantity());
                }
                sameProduct.setSelected(itemGuest.getSelected());
                nxtShoppingCartProductService.update(sameProduct);
            }
            else {
                //添加产品到已登录购物车
                NxtShoppingCartProduct newNxtShoppingCartProduct = new NxtShoppingCartProduct();
                newNxtShoppingCartProduct.setDateline(System.currentTimeMillis());
                newNxtShoppingCartProduct.setShoppingCartId(nxtShoppingCart.getId());//已登录购物车id
                newNxtShoppingCartProduct.setProductId(itemGuest.getProductId());
                newNxtShoppingCartProduct.setQuantity(itemGuest.getQuantity());
                newNxtShoppingCartProduct.setSku(itemGuest.getSku());
                newNxtShoppingCartProduct.setSelected(itemGuest.getSelected());
                nxtShoppingCartProductService.insert(newNxtShoppingCartProduct);
            }
        }

    }

}
