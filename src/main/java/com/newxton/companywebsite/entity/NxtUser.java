package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtUser)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:23:12
 */
public class NxtUser implements Serializable {
    private static final long serialVersionUID = 706641861373004891L;
    
    private Long id;
    
    private String username;
    
    private String password;
    
    private String salt;
    
    private String token;
    
    private Integer status;
    
    private Integer type;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}