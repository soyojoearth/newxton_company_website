package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtRecharge)实体类
 *
 * @author makejava
 * @since 2020-12-14 19:34:48
 */
public class NxtRecharge implements Serializable {
    private static final long serialVersionUID = 346138542683584711L;
    /**
    * 充值表
    */
    private Long id;
    
    private String serialNum;
    
    private Long userId;
    
    private Long transactionId;
    
    private Long orderFormId;
    /**
    * 状态（0:正在充值 1:成功 -1:失败）
    */
    private Integer status;
    /**
    * 平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
    */
    private Integer platform;
    /**
    * 时间
    */
    private Long dateline;
    /**
    * 金额（放大100倍）
    */
    private Long amount;
    /**
    * 接口通知时间
    */
    private Long notifyDateline;
    /**
    * 编号
    */
    private String notifySerialNum;
    /**
    * 通知数据（json、xml等原始数据）
    */
    private String notifyData;
    
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
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

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getNotifyDateline() {
        return notifyDateline;
    }

    public void setNotifyDateline(Long notifyDateline) {
        this.notifyDateline = notifyDateline;
    }

    public String getNotifySerialNum() {
        return notifySerialNum;
    }

    public void setNotifySerialNum(String notifySerialNum) {
        this.notifySerialNum = notifySerialNum;
    }

    public String getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(String notifyData) {
        this.notifyData = notifyData;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}