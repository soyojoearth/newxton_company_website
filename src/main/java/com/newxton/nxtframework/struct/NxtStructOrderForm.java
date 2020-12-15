package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderForm {

    /**
     * 订单id
     */
    private Long id;
    /**
     * 下单用户id
     */
    private Long userId;
    /**
     * 下单用户username
     */
    private String username;
    /**
     * 下单时间
     */
    private Long datelineCreate;
    /**
     * 下单时间（人类可读）
     */
    private String datelineCreateReadable;
    /**
     * 订单编号
     */
    private String serialNum;
    /**
     * 最初总金额
     */
    private Float amountInitial;
    /**
     * 优惠金额
     */
    private Float amountDiscount;
    /**
     * 最终总金额
     */
    private Float amountFinally;
    /**
     * 收货人地址
     */
    private String deliveryAddress;
    /**
     * 收货人名字
     */
    private String deliveryPerson;

    private String deliveryCountry;

    private String deliveryProvince;

    private String deliveryCity;
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
     * 运费
     */
    private Float deliveryCost;
    /**
     * 手动运费调整
     */
    private Float manualDeliveryCostDiscount;
    /**
     * 订单状态描述文字
     */
    private String statusText;
    /**
     * 是否已经支付
     */
    private Boolean isPaid;

    /**
     * 是否已经发货
     */
    private Boolean isDelivery;
    /**
     * 是否已经评价
     */
    private Boolean isReviews;
    /**
     * 是否有退货情况
     */
    private Boolean isRefund;
    /**
     * 成交平台
     */
    private String dealPlatform;

    /**
     * 发货时间
     */
    private Long datelineDelivery;
    private String datelineDeliveryReadable;

    /**
     * 付款时间
     */
    private Long datelinePaid;
    private String datelinePaidReadable;

    /**
     * 确认收货时间
     */
    private Long datelineReceived;
    private String datelineReceivedReadable;

    /**
     * 已经确认收货
     */
    private Boolean isDone;

    /**
     * 商家备注
     */
    private String sellerRemark;

    /**
     * 订单物品列表
     */
    private List<NxtStructOrderFormProduct> orderFormProductList = new ArrayList<>();

    /**
     * 发货信息
     */
    private NxtStructOrderFormDelivery orderFormDelivery;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Float getAmountInitial() {
        return amountInitial;
    }

    public void setAmountInitial(Float amountInitial) {
        this.amountInitial = amountInitial;
    }

    public Float getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Float amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Float getAmountFinally() {
        return amountFinally;
    }

    public void setAmountFinally(Float amountFinally) {
        this.amountFinally = amountFinally;
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

    public Float getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Float getManualDeliveryCostDiscount() {
        return manualDeliveryCostDiscount;
    }

    public void setManualDeliveryCostDiscount(Float manualDeliveryCostDiscount) {
        this.manualDeliveryCostDiscount = manualDeliveryCostDiscount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean delivery) {
        isDelivery = delivery;
    }

    public Boolean getReviews() {
        return isReviews;
    }

    public void setReviews(Boolean reviews) {
        isReviews = reviews;
    }

    public Boolean getRefund() {
        return isRefund;
    }

    public void setRefund(Boolean refund) {
        isRefund = refund;
    }

    public String getDealPlatform() {
        return dealPlatform;
    }

    public void setDealPlatform(String dealPlatform) {
        this.dealPlatform = dealPlatform;
    }

    public Long getDatelineDelivery() {
        return datelineDelivery;
    }

    public void setDatelineDelivery(Long datelineDelivery) {
        this.datelineDelivery = datelineDelivery;
    }

    public String getDatelineDeliveryReadable() {
        return datelineDeliveryReadable;
    }

    public void setDatelineDeliveryReadable(String datelineDeliveryReadable) {
        this.datelineDeliveryReadable = datelineDeliveryReadable;
    }

    public Long getDatelinePaid() {
        return datelinePaid;
    }

    public void setDatelinePaid(Long datelinePaid) {
        this.datelinePaid = datelinePaid;
    }

    public String getDatelinePaidReadable() {
        return datelinePaidReadable;
    }

    public void setDatelinePaidReadable(String datelinePaidReadable) {
        this.datelinePaidReadable = datelinePaidReadable;
    }

    public Long getDatelineReceived() {
        return datelineReceived;
    }

    public void setDatelineReceived(Long datelineReceived) {
        this.datelineReceived = datelineReceived;
    }

    public String getDatelineReceivedReadable() {
        return datelineReceivedReadable;
    }

    public void setDatelineReceivedReadable(String datelineReceivedReadable) {
        this.datelineReceivedReadable = datelineReceivedReadable;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public List<NxtStructOrderFormProduct> getOrderFormProductList() {
        return orderFormProductList;
    }

    public void setOrderFormProductList(List<NxtStructOrderFormProduct> orderFormProductList) {
        this.orderFormProductList = orderFormProductList;
    }

    public NxtStructOrderFormDelivery getOrderFormDelivery() {
        return orderFormDelivery;
    }

    public void setOrderFormDelivery(NxtStructOrderFormDelivery orderFormDelivery) {
        this.orderFormDelivery = orderFormDelivery;
    }
}
