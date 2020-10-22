package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUser)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:25:55
 */
public class NxtUser implements Serializable {
    private static final long serialVersionUID = 419475128476300511L;
    
    private Long id;
    
    private String username;
    
    private String password;
    
    private String salt;
    
    private String token;
    
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