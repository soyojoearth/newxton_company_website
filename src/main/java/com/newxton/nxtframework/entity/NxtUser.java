package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUser)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:11
 */
public class NxtUser implements Serializable {
    private static final long serialVersionUID = -57152744993817674L;
    
    private Long id;
    /**
    * 登录用户名
    */
    private String username;
    /**
    * 登录密码 md5(password+salt) 全小写
    */
    private String password;
    /**
    * 密码盐
    */
    private String salt;
    /**
    * 每次注销/登录都要变化
    */
    private String token;
    /**
    * 0:正常 -1:黑名单
    */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}