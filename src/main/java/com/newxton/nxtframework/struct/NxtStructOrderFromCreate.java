package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFromCreate {

    /**
     * 收件人全名
     */
    public String deliveryPerson;
    /**
     * 国家
     */
    public Long deliveryCountry;
    /**
     * 省份
     */
    public Long deliveryProvince;
    /**
     * 城市
     */
    public Long deliveryCity;
    /**
     * 城市内地址
     */
    public String deliveryAddress;

    /**
     * 收件人联系电话
     */
    public String deliveryPhone;
    /**
     * 邮编
     */
    public String deliveryPostcode;
    /**
     * 用户订单备注
     */
    public String deliveryRemark;
    /**
     * 物流方式（运费模版编号）
     */
    public Long deliveryConfigId;
    /**
     * 成交平台（0:web 1:ios 2:android 3:wx ）
     */
    public Integer dealPlatform;

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Long getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(Long deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public Long getDeliveryProvince() {
        return deliveryProvince;
    }

    public void setDeliveryProvince(Long deliveryProvince) {
        this.deliveryProvince = deliveryProvince;
    }

    public Long getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(Long deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public Long getDeliveryConfigId() {
        return deliveryConfigId;
    }

    public void setDeliveryConfigId(Long deliveryConfigId) {
        this.deliveryConfigId = deliveryConfigId;
    }

    public Integer getDealPlatform() {
        return dealPlatform;
    }

    public void setDealPlatform(Integer dealPlatform) {
        this.dealPlatform = dealPlatform;
    }

}
