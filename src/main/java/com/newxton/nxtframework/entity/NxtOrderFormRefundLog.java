package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormRefundLog)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:55
 */
public class NxtOrderFormRefundLog implements Serializable {
    private static final long serialVersionUID = 402256342859119924L;

    private Long id;

    private Long orderFormRefundId;
    /**
     * 用户或管理员id
     */
    private Long userId;

    private Integer isAdmin;

    private Long dateline;

    private String logName;

    private String logRemark;

    private Integer statusPaid;

    private Integer statusDelivery;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormRefundId() {
        return orderFormRefundId;
    }

    public void setOrderFormRefundId(Long orderFormRefundId) {
        this.orderFormRefundId = orderFormRefundId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark;
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

}