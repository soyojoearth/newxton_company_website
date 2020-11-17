package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormLog)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:51
 */
public class NxtOrderFormLog implements Serializable {
    private static final long serialVersionUID = -71503943062741274L;
    /**
     * 订单操作日志表
     */
    private Long id;

    private Long orderFormId;
    /**
     * 操作员（非订单用户）如果是用户操作，那么这里是用户user_id，如果是管理员操作，则是管理员user_id
     */
    private Long userId;
    /**
     * 0用户 1管理员
     */
    private Integer isAdmin;

    private Long dateline;
    /**
     * 是否已经支付（0：未支付 1:已支付）（支付失败的还是0）
     */
    private Integer statusPaid;
    /**
     * 发货状态（0:未发货 1:已发货）
     */
    private Integer statusDelivery;

    private Integer statusRefund;
    /**
     * 操作描述
     */
    private String logName;
    /**
     * 操作备注
     */
    private String logRemark;


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

    public Integer getStatusRefund() {
        return statusRefund;
    }

    public void setStatusRefund(Integer statusRefund) {
        this.statusRefund = statusRefund;
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

}