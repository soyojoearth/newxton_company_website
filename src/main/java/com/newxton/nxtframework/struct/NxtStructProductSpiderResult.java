package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructProductSpiderResult {

    private String externalUrl;
    private String productName;
    private Float price;
    private List<String> pictureList = new ArrayList();
    private List<NxtStructProductSku> skuList = new ArrayList<>();
    private String productDescription;

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<NxtStructProductSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<NxtStructProductSku> skuList) {
        this.skuList = skuList;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
