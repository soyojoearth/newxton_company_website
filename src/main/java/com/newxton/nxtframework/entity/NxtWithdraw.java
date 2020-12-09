package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtWithdraw)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:49
 */
public class NxtWithdraw implements Serializable {
    private static final long serialVersionUID = -81578547813222654L;
    /**
     * 提现表
     */
    private Long id;

    private Long userId;

    private Long transactionId;
    /**
     * 状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
     */
    private Integer status;
    /**
     * 正数：提现
     */
    private Long amount;

    private Long datelineCreate;

    private Long datelineEnd;
    /**
     * 平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
     */
    private Integer platform;
    /**
     * 收款平台收款人
     */
    private String platformPerson;
    /**
     * 收款平台账号
     */
    private String platformAccount;
    /**
     * 收款平台汇款编号
     */
    private String platformSerialNum;
    /**
     * 用户备注
     */
    private String remarkUser;
    /**
     * 管理员备注
     */
    private String remarkAdmin;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPlatformPerson() {
        return platformPerson;
    }

    public void setPlatformPerson(String platformPerson) {
        this.platformPerson = platformPerson;
    }

    public String getPlatformAccount() {
        return platformAccount;
    }

    public void setPlatformAccount(String platformAccount) {
        this.platformAccount = platformAccount;
    }

    public String getPlatformSerialNum() {
        return platformSerialNum;
    }

    public void setPlatformSerialNum(String platformSerialNum) {
        this.platformSerialNum = platformSerialNum;
    }

    public String getRemarkUser() {
        return remarkUser;
    }

    public void setRemarkUser(String remarkUser) {
        this.remarkUser = remarkUser;
    }

    public String getRemarkAdmin() {
        return remarkAdmin;
    }

    public void setRemarkAdmin(String remarkAdmin) {
        this.remarkAdmin = remarkAdmin;
    }

}