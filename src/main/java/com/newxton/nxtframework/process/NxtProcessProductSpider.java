package com.newxton.nxtframework.process;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.component.spider.*;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessProductSpider {

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @Resource
    private NxtProductSpiderTaobaoComponent nxtProductSpiderTaobaoComponent;

    @Resource
    private NxtProductSpiderTMallComponent nxtProductSpiderTMallComponent;

    @Resource
    private NxtProductSpiderJDComponent nxtProductSpiderJDComponent;

    @Resource
    private NxtProductSpiderAmazonComponent nxtProductSpiderAmazonComponent;

    @Resource
    private NxtProductSpider1688Component nxtProductSpider1688Component;

    /**
     * 抓取第三方平台商品，并返回NxtStructProduct对象
     * @param externalUrl
     * @return
     * @throws NxtException
     */
    public NxtStructProduct catchProductFromOther(String externalUrl) throws NxtException {

        NxtStructProductSpiderResult nxtStructProductSpiderResult = null;

        /**
         * 这里可以一个个增加不同平台的Spider组件
         */
        if (nxtProductSpiderTaobaoComponent.isMatchUrl(externalUrl)){
            nxtStructProductSpiderResult = nxtProductSpiderTaobaoComponent.catchProductFromUrl(externalUrl);
        }
        else if (nxtProductSpiderTMallComponent.isMatchUrl(externalUrl)){
            nxtStructProductSpiderResult = nxtProductSpiderTMallComponent.catchProductFromUrl(externalUrl);
        }
        else if (nxtProductSpiderJDComponent.isMatchUrl(externalUrl)){
            nxtStructProductSpiderResult = nxtProductSpiderJDComponent.catchProductFromUrl(externalUrl);
        }
        else if (nxtProductSpiderAmazonComponent.isMatchUrl(externalUrl)){
            nxtStructProductSpiderResult = nxtProductSpiderAmazonComponent.catchProductFromUrl(externalUrl);
        }
        else if (nxtProductSpider1688Component.isMatchUrl(externalUrl)){
            nxtStructProductSpiderResult = nxtProductSpider1688Component.catchProductFromUrl(externalUrl);
        }
        else {
            throw new NxtException("暂不支持这个第三方商品链接");
        }

        if (nxtStructProductSpiderResult != null &&
                nxtStructProductSpiderResult.getProductName() != null &&
                nxtStructProductSpiderResult.getPrice() != null &&
                nxtStructProductSpiderResult.getPictureList() != null &&
                nxtStructProductSpiderResult.getPictureList().size() > 0
        ){
            nxtStructProductSpiderResult.setExternalUrl(externalUrl);
            return this.assemblyNxtStructProductFromSpider(nxtStructProductSpiderResult);
        }
        else {
            throw new NxtException("Spider未抓取到正确商品数据");
        }

    }

    /**
     * 将Spider搞来的数据，组装成接口格式的产品结构，同时抓取图片
     * @param spiderResult
     * @return
     * @throws NxtException
     */
    private NxtStructProduct assemblyNxtStructProductFromSpider(NxtStructProductSpiderResult spiderResult) throws NxtException{

        NxtStructProduct nxtStructProduct = new NxtStructProduct();

        nxtStructProduct.setExternalUrl(spiderResult.getExternalUrl());
        nxtStructProduct.setProductName(spiderResult.getProductName());
        nxtStructProduct.setProductDescription(spiderResult.getProductDescription());
        nxtStructProduct.setPrice(spiderResult.getPrice());
        //批量抓取图片
        for (String imgUrl : spiderResult.getPictureList()) {
            NxtUploadfile nxtUploadfile = nxtUploadImageComponent.catchPictureAndSave(imgUrl);
            if (nxtUploadfile != null){
                NxtStructProductPicture nxtStructProductPicture = new NxtStructProductPicture();
                nxtStructProductPicture.setId(nxtUploadfile.getId());
                nxtStructProductPicture.setUrl(nxtUploadfile.getUrlpath());
                nxtStructProduct.getPictureList().add(nxtStructProductPicture);
            }
        }
        //设置sku
        if (spiderResult.getSkuList().size() > 2){
            throw new NxtException("产品规格最多2种，Spider抓的太多");
        }
        nxtStructProduct.setSkuList(spiderResult.getSkuList());
        //设置sku价格、库存、折扣
        if (nxtStructProduct.getSkuList().size() == 2) {
            nxtStructProduct.setWithSku(true);
            for (NxtStructProductSkuValue skuValue1 : nxtStructProduct.getSkuList().get(0).getSkuValueList()) {
                for (NxtStructProductSkuValue skuValue2 : nxtStructProduct.getSkuList().get(1).getSkuValueList()) {
                    NxtStructProductSkuValuePriceEtc skuValuePriceEtc = new NxtStructProductSkuValuePriceEtc();
                    skuValuePriceEtc.setSkuValueInventoryQuantity(100L);
                    skuValuePriceEtc.setSkuValuePrice(nxtStructProduct.getPrice());
                    skuValuePriceEtc.setSkuValuePriceDiscount(1F);
                    skuValuePriceEtc.setSkuValueName1(skuValue1.getSkuValueName());
                    skuValuePriceEtc.setSkuValueName2(skuValue2.getSkuValueName());
                    nxtStructProduct.getSkuValuePriceEtcList().add(skuValuePriceEtc);
                }
            }
        }
        else if (nxtStructProduct.getSkuList().size() == 1){
            nxtStructProduct.setWithSku(true);
            for (NxtStructProductSkuValue skuValue1 : nxtStructProduct.getSkuList().get(0).getSkuValueList()) {
                NxtStructProductSkuValuePriceEtc skuValuePriceEtc = new NxtStructProductSkuValuePriceEtc();
                skuValuePriceEtc.setSkuValueInventoryQuantity(100L);
                skuValuePriceEtc.setSkuValuePrice(nxtStructProduct.getPrice());
                skuValuePriceEtc.setSkuValuePriceDiscount(1F);
                skuValuePriceEtc.setSkuValueName1(skuValue1.getSkuValueName());
                skuValuePriceEtc.setSkuValueName2(null);
                nxtStructProduct.getSkuValuePriceEtcList().add(skuValuePriceEtc);
            }
        }

        return nxtStructProduct;

    }

}
