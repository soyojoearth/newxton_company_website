package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUserAddress)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:49
 */
public class NxtUserAddress implements Serializable {
    private static final long serialVersionUID = -43041326929736589L;
    /**
     * 收货地址
     */
    private Long id;

    private Long userId;

    private Long regionLevel0;

    private Long regionLevel1;

    private Long regionLevel2;

    private Long regionLevel3;

    private Long regionLevel4;

    private String regionAddress;

    private String deliveryPerson;

    private String deliveryPhone;

    private String deliveryPostcode;


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

    public Long getRegionLevel0() {
        return regionLevel0;
    }

    public void setRegionLevel0(Long regionLevel0) {
        this.regionLevel0 = regionLevel0;
    }

    public Long getRegionLevel1() {
        return regionLevel1;
    }

    public void setRegionLevel1(Long regionLevel1) {
        this.regionLevel1 = regionLevel1;
    }

    public Long getRegionLevel2() {
        return regionLevel2;
    }

    public void setRegionLevel2(Long regionLevel2) {
        this.regionLevel2 = regionLevel2;
    }

    public Long getRegionLevel3() {
        return regionLevel3;
    }

    public void setRegionLevel3(Long regionLevel3) {
        this.regionLevel3 = regionLevel3;
    }

    public Long getRegionLevel4() {
        return regionLevel4;
    }

    public void setRegionLevel4(Long regionLevel4) {
        this.regionLevel4 = regionLevel4;
    }

    public String getRegionAddress() {
        return regionAddress;
    }

    public void setRegionAddress(String regionAddress) {
        this.regionAddress = regionAddress;
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

}