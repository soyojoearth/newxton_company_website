package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtCommission)实体类
 *
 * @author makejava
 * @since 2020-12-05 20:20:39
 */
public class NxtCommission implements Serializable {
    private static final long serialVersionUID = 298046260750629523L;
    /**
    * 佣金记录表
    */
    private Long id;
    /**
    * 获佣会员user_id
    */
    private Long userId;
    /**
    * 获佣等级（1上架 2:上上家 3:上上上家）
    */
    private Integer inviterLevel;
    
    private Long orderFormId;
    
    private Long orderFormProductId;
    /**
    * 有数字代表已结算
    */
    private Long commissionTransferInId;
    /**
    * 佣金百分比（放大100倍）
    */
    private Long commissionRate;
    /**
    * 佣金（放大100倍）负数表示退佣金
    */
    private Long commissionAmount;
    /**
    * 交易创建时间
    */
    private Long datelineCreate;
    
    private Long datelineReceived;
    /**
    * 交易完成时间
    */
    private Long datelineEnd;
    
    private Long quantityDeal;
    
    private Long quantityRefund;
    /**
    * 已经支付
    */
    private Integer isPaid;
    /**
    * 已结转完成
    */
    private Integer isTransfer;


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

    public Integer getInviterLevel() {
        return inviterLevel;
    }

    public void setInviterLevel(Integer inviterLevel) {
        this.inviterLevel = inviterLevel;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
    }

    public Long getCommissionTransferInId() {
        return commissionTransferInId;
    }

    public void setCommissionTransferInId(Long commissionTransferInId) {
        this.commissionTransferInId = commissionTransferInId;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public Long getDatelineReceived() {
        return datelineReceived;
    }

    public void setDatelineReceived(Long datelineReceived) {
        this.datelineReceived = datelineReceived;
    }

    public Long getDatelineEnd() {
        return datelineEnd;
    }

    public void setDatelineEnd(Long datelineEnd) {
        this.datelineEnd = datelineEnd;
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

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(Integer isTransfer) {
        this.isTransfer = isTransfer;
    }

}