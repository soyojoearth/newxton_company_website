package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormRefund)实体类
 *
 * @author makejava
 * @since 2020-11-25 17:25:07
 */
public class NxtOrderFormRefund implements Serializable {
    private static final long serialVersionUID = -29733876956351333L;
    
    private Long id;
    
    private Long userId;
    
    private Long orderFormId;
    /**
    * 0:无理由 1:质量问题
    */
    private Integer reasonType;
    /**
    * 退货原因描述
    */
    private String reasionDescription;
    
    private Long transactionId;
    /**
    * 状态（-1:拒绝退款 0:已申请 1:完成 2:等用户发货 3:收到货退款 4:收到货有问题，请修改金额 5:用户已寄出物品）
    */
    private Integer status;
    
    private Long datelineCreate;
    
    private Long datelineEnd;


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

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Integer getReasonType() {
        return reasonType;
    }

    public void setReasonType(Integer reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasionDescription() {
        return reasionDescription;
    }

    public void setReasionDescription(String reasionDescription) {
        this.reasionDescription = reasionDescription;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public Long getDatelineEnd() {
        return datelineEnd;
    }

    public void setDatelineEnd(Long datelineEnd) {
        this.datelineEnd = datelineEnd;
    }

}