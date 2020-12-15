package com.newxton.nxtframework.struct.admin;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminCommission {

    private Long id;

    private Long orderFormId;
    private String orderFormSerialNum;

    private Long orderFormProductId;
    private String orderFormProductName;

    /**
     * 成交数量
     */
    private Long quantityDeal;
    /**
     * 退货数量
     */
    private Long quantityRefund;

    private Long inviterUserId;
    private String inviterUsername;

    /**
     * 成交价
     */
    private Float priceDeal;

    /**
     * 佣金额
     */
    private Float commissionAmount;

    /**
     * 佣金百分比（放大100倍）
     */
    private Long commissionRate;

    /**
     * 获佣等级（1上架 2:上上家 3:上上上家）
     */
    private Integer inviterLevel;

    /**
     * 最新状态变化时间
     */
    private Long dateline;
    private String datelineReadable;

    /**
     * 状态
     */
    private String statusText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getOrderFormSerialNum() {
        return orderFormSerialNum;
    }

    public void setOrderFormSerialNum(String orderFormSerialNum) {
        this.orderFormSerialNum = orderFormSerialNum;
    }

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
    }

    public String getOrderFormProductName() {
        return orderFormProductName;
    }

    public void setOrderFormProductName(String orderFormProductName) {
        this.orderFormProductName = orderFormProductName;
    }

    public Long getQuantityDeal() {
        return quantityDeal;
    }

    public void setQuantityDeal(Long quantityDeal) {
        this.quantityDeal = quantityDeal;
    }

    public Long getQuantityRefund() {
        return quantityRefund;
    }

    public void setQuantityRefund(Long quantityRefund) {
        this.quantityRefund = quantityRefund;
    }

    public Long getInviterUserId() {
        return inviterUserId;
    }

    public void setInviterUserId(Long inviterUserId) {
        this.inviterUserId = inviterUserId;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }

    public Float getPriceDeal() {
        return priceDeal;
    }

    public void setPriceDeal(Float priceDeal) {
        this.priceDeal = priceDeal;
    }

    public Float getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Float commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Integer getInviterLevel() {
        return inviterLevel;
    }

    public void setInviterLevel(Integer inviterLevel) {
        this.inviterLevel = inviterLevel;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public String getDatelineReadable() {
        return datelineReadable;
    }

    public void setDatelineReadable(String datelineReadable) {
        this.datelineReadable = datelineReadable;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

}
