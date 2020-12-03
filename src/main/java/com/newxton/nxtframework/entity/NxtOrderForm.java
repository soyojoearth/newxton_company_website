package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderForm)实体类
 *
 * @author makejava
 * @since 2020-12-03 10:41:45
 */
public class NxtOrderForm implements Serializable {
    private static final long serialVersionUID = -47959913067327264L;
    /**
    * 订单表
    */
    private Long id;
    /**
    * 下单用户
    */
    private Long userId;
    /**
    * 下单时间
    */
    private Long datelineCreate;
    /**
    * 订单编号
    */
    private String serialNum;
    /**
    * 最初总金额（放大100倍）
    */
    private Long amountInitial;
    /**
    * 优惠金额（放大100倍）
    */
    private Long amountDiscount;
    /**
    * 最终总金额（放大100倍）
    */
    private Long amountFinally;
    /**
    * 收货人名字
    */
    private String deliveryPerson;
    
    private String deliveryCountry;
    
    private String deliveryProvince;
    
    private String deliveryCity;
    /**
    * 收货人地址
    */
    private String deliveryAddress;
    /**
    * 收货人联系电话
    */
    private String deliveryPhone;
    /**
    * 邮编
    */
    private String deliveryPostcode;
    /**
    * 备注
    */
    private String deliveryRemark;
    /**
    * 运费模版名称
    */
    private String deliveryConfigName;
    /**
    * 运费
    */
    private Long deliveryCost;
    /**
    * 运费调整（正数加、负数减）
    */
    private Long manualDeliveryCostDiscount;
    /**
    * 单位（千克）放大1000倍
    */
    private Long countWeight;
    /**
    * 单位（立方米）放大100万倍
    */
    private Long countVolume;
    /**
    * 是否已经支付（0：未支付 1:已支付 -1:支付失败）
    */
    private Integer statusPaid;
    /**
    * 发货状态（0:未发货 1:已发货）
    */
    private Integer statusDelivery;
    /**
    * 0:未评价 1:已评价
    */
    private Integer statusReviews;
    /**
    * 退货退款（0:未退货退款，1:出现退货退款情况）（情况具体要见order_form_refund表）
    */
    private Integer statusRefund;
    /**
    * 成交平台（0:web 1:ios 2:android 3:wx ）可扩展其它数字
    */
    private Integer dealPlatform;
    
    private Long datelineDelivery;
    
    private Long datelinePaid;
    
    private Long datelineReceived;
    /**
    * 商家内部备注
    */
    private String sellerRemark;


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

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Long getAmountInitial() {
        return amountInitial;
    }

    public void setAmountInitial(Long amountInitial) {
        this.amountInitial = amountInitial;
    }

    public Long getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Long amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Long getAmountFinally() {
        return amountFinally;
    }

    public void setAmountFinally(Long amountFinally) {
        this.amountFinally = amountFinally;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryProvince() {
        return deliveryProvince;
    }

    public void setDeliveryProvince(String deliveryProvince) {
        this.deliveryProvince = deliveryProvince;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
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

    public String getDeliveryConfigName() {
        return deliveryConfigName;
    }

    public void setDeliveryConfigName(String deliveryConfigName) {
        this.deliveryConfigName = deliveryConfigName;
    }

    public Long getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Long deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Long getManualDeliveryCostDiscount() {
        return manualDeliveryCostDiscount;
    }

    public void setManualDeliveryCostDiscount(Long manualDeliveryCostDiscount) {
        this.manualDeliveryCostDiscount = manualDeliveryCostDiscount;
    }

    public Long getCountWeight() {
        return countWeight;
    }

    public void setCountWeight(Long countWeight) {
        this.countWeight = countWeight;
    }

    public Long getCountVolume() {
        return countVolume;
    }

    public void setCountVolume(Long countVolume) {
        this.countVolume = countVolume;
    }

    public Integer getStatusPaid() {
        return statusPaid;
    }

    public void setStatusPaid(Integer statusPaid) {
        this.statusPaid = statusPaid;
    }

    public Integer getStatusDelivery() {
        return statusDelivery;
    }

    public void setStatusDelivery(Integer statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public Integer getStatusReviews() {
        return statusReviews;
    }

    public void setStatusReviews(Integer statusReviews) {
        this.statusReviews = statusReviews;
    }

    public Integer getStatusRefund() {
        return statusRefund;
    }

    public void setStatusRefund(Integer statusRefund) {
        this.statusRefund = statusRefund;
    }

    public Integer getDealPlatform() {
        return dealPlatform;
    }

    public void setDealPlatform(Integer dealPlatform) {
        this.dealPlatform = dealPlatform;
    }

    public Long getDatelineDelivery() {
        return datelineDelivery;
    }

    public void setDatelineDelivery(Long datelineDelivery) {
        this.datelineDelivery = datelineDelivery;
    }

    public Long getDatelinePaid() {
        return datelinePaid;
    }

    public void setDatelinePaid(Long datelinePaid) {
        this.datelinePaid = datelinePaid;
    }

    public Long getDatelineReceived() {
        return datelineReceived;
    }

    public void setDatelineReceived(Long datelineReceived) {
        this.datelineReceived = datelineReceived;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

}