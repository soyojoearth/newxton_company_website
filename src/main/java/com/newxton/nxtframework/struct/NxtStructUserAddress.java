package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructUserAddress {

    private Long id;
    /**
     * 国家id
     */
    private Long regionCountry;
    /**
     * 国家名字
     */
    private String regionCountryName;
    /**
     * 省份id
     */
    private Long regionProvince;
    /**
     * 省份名字
     */
    private String regionProvinceName;
    /**
     * 城市id
     */
    private Long regionCity;
    /**
     * 城市名字
     */
    private String regionCityName;
    /**
     * 详细地址
     */
    private String deliveryAddress;
    /**
     * 收货人
     */
    private String deliveryPerson;
    /**
     * 收货人电话
     */
    private String deliveryPhone;
    /**
     * 邮编
     */
    private String deliveryPostcode;
    /**
     * 是否默认收货地址
     */
    private Boolean isDefault = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionCountry() {
        return regionCountry;
    }

    public void setRegionCountry(Long regionCountry) {
        this.regionCountry = regionCountry;
    }

    public String getRegionCountryName() {
        return regionCountryName;
    }

    public void setRegionCountryName(String regionCountryName) {
        this.regionCountryName = regionCountryName;
    }

    public Long getRegionProvince() {
        return regionProvince;
    }

    public void setRegionProvince(Long regionProvince) {
        this.regionProvince = regionProvince;
    }

    public String getRegionProvinceName() {
        return regionProvinceName;
    }

    public void setRegionProvinceName(String regionProvinceName) {
        this.regionProvinceName = regionProvinceName;
    }

    public Long getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(Long regionCity) {
        this.regionCity = regionCity;
    }

    public String getRegionCityName() {
        return regionCityName;
    }

    public void setRegionCityName(String regionCityName) {
        this.regionCityName = regionCityName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
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

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
