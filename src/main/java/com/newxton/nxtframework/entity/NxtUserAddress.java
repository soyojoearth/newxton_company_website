package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUserAddress)实体类
 *
 * @author makejava
 * @since 2020-12-01 14:53:02
 */
public class NxtUserAddress implements Serializable {
    private static final long serialVersionUID = -47559102538199585L;
    /**
    * 收货地址
    */
    private Long id;
    
    private Long userId;
    
    private Long regionCountry;
    
    private Long regionProvince;
    
    private Long regionCity;
    
    private String deliveryAddress;
    
    private String deliveryPerson;
    
    private String deliveryPhone;
    
    private String deliveryPostcode;
    
    private Integer isDefault;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRegionCountry() {
        return regionCountry;
    }

    public void setRegionCountry(Long regionCountry) {
        this.regionCountry = regionCountry;
    }

    public Long getRegionProvince() {
        return regionProvince;
    }

    public void setRegionProvince(Long regionProvince) {
        this.regionProvince = regionProvince;
    }

    public Long getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(Long regionCity) {
        this.regionCity = regionCity;
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

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

}