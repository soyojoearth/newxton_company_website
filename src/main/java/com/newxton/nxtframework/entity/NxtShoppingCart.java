package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtShoppingCart)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
public class NxtShoppingCart implements Serializable {
    private static final long serialVersionUID = -10383043115702879L;
    /**
     * 购物车，仅用token也可以查询（访客）
     */
    private Long id;
    /**
     * 不一定需要登录
     */
    private Long userId;
    /**
     * 可以仅使用token操作查看购物车
     */
    private String token;


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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}