package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * Api接口数据结构：产品详情
 *
 */
public class NxtStructProduct {

    public Long id;
    public Long categoryId;
    public Long brandId;
    public String productName;
    public String productSubtitle;
    public String productTags;
    public Float productRatings;
    public String externalUrl;
    public Long dealQuantityMin;
    public Long dealQuantityMax;
    public Boolean freeShipping;
    public Long deliveryConfigId;
    public String itemNo;
    public Boolean withSku;
    public Float price;
    public Float priceDiscount;
    public Long inventoryQuantity;
    public String productDescription;
    public Long datelineUpdated;
    public String datelineUpdatedReadable;
    public Long datelineCreate;
    public String datelineCreateReadable;
    public Boolean isRecommend;
    public Boolean isHot;
    public Boolean isNew;
    public Boolean isSelling;
    public Boolean isTrash;
    public List<NxtStructProductSku> skuList = new ArrayList<>();
    public List<NxtStructProductSkuValuePriceEtc> skuValuePriceEtcList = new ArrayList();
    public List<NxtStructProductPicture> pictureList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductTags() {
        return productTags;
    }

    public void setProductTags(String productTags) {
        this.productTags = productTags;
    }

    public Float getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(Float productRatings) {
        this.productRatings = productRatings;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Long getDealQuantityMin() {
        return dealQuantityMin;
    }

    public void setDealQuantityMin(Long dealQuantityMin) {
        this.dealQuantityMin = dealQuantityMin;
    }

    public Long getDealQuantityMax() {
        return dealQuantityMax;
    }

    public void setDealQuantityMax(Long dealQuantityMax) {
        this.dealQuantityMax = dealQuantityMax;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public Long getDeliveryConfigId() {
        return deliveryConfigId;
    }

    public void setDeliveryConfigId(Long deliveryConfigId) {
        this.deliveryConfigId = deliveryConfigId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public Boolean getWithSku() {
        return withSku;
    }

    public void setWithSku(Boolean withSku) {
        this.withSku = withSku;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Float priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Long getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getDatelineUpdated() {
        return datelineUpdated;
    }

    public void setDatelineUpdated(Long datelineUpdated) {
        this.datelineUpdated = datelineUpdated;
    }

    public String getDatelineUpdatedReadable() {
        return datelineUpdatedReadable;
    }

    public void setDatelineUpdatedReadable(String datelineUpdatedReadable) {
        this.datelineUpdatedReadable = datelineUpdatedReadable;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public String getDatelineCreateReadable() {
        return datelineCreateReadable;
    }

    public void setDatelineCreateReadable(String datelineCreateReadable) {
        this.datelineCreateReadable = datelineCreateReadable;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getSelling() {
        return isSelling;
    }

    public void setSelling(Boolean selling) {
        isSelling = selling;
    }

    public Boolean getTrash() {
        return isTrash;
    }

    public void setTrash(Boolean trash) {
        isTrash = trash;
    }

    public List<NxtStructProductSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<NxtStructProductSku> skuList) {
        this.skuList = skuList;
    }

    public List<NxtStructProductSkuValuePriceEtc> getSkuValuePriceEtcList() {
        return skuValuePriceEtcList;
    }

    public void setSkuValuePriceEtcList(List<NxtStructProductSkuValuePriceEtc> skuValuePriceEtcList) {
        this.skuValuePriceEtcList = skuValuePriceEtcList;
    }

    public List<NxtStructProductPicture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<NxtStructProductPicture> pictureList) {
        this.pictureList = pictureList;
    }
}
