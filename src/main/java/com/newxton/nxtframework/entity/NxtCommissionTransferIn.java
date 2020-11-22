package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtCommissionTransferIn)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:44:38
 */
public class NxtCommissionTransferIn implements Serializable {
    private static final long serialVersionUID = -61353904969321969L;
    /**
     * 佣金结转
     */
    private Long id;

    private Long userId;

    private Long transactionId;
    /**
     * 结转金额（放大100倍）
     */
    private Long amount;

    private Long datelineCreate;

    private Long datelineEnd;
    /**
     * 状态（0等待审核 1通过 -1驳回）
     */
    private Integer status;


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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}