package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtTransaction)实体类
 *
 * @author makejava
 * @since 2020-12-09 18:55:58
 */
public class NxtTransaction implements Serializable {
    private static final long serialVersionUID = 154642680511172387L;
    /**
    * 资金流动账本
    */
    private Long id;
    
    private Long userId;
    /**
    * 账本金额（正数进，负数出）
    */
    private Long amount;
    
    private Long balance;
    /**
    * 发生时间
    */
    private Long dateline;
    /**
    * 交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
    */
    private Integer type;
    /**
    * 外部主键（如果是提现，就是withdraw主键，消费就是order_form_pay主键；以此类推）
    */
    private Long outerId;


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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOuterId() {
        return outerId;
    }

    public void setOuterId(Long outerId) {
        this.outerId = outerId;
    }

}