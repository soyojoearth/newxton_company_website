package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUserLevel)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:48
 */
public class NxtUserLevel implements Serializable {
    private static final long serialVersionUID = 733690505403325778L;
    /**
     * 用户等级(不要自增长)
     */
    private Long id;

    private Integer num;

    private String name;
    /**
     * 所需消费额度（放大100倍）
     */
    private Long cost;
    /**
     * 折扣，百分比（放大100倍）
     */
    private Long discount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

}