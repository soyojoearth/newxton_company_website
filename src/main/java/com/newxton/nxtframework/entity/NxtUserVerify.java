package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUserVerify)实体类
 *
 * @author makejava
 * @since 2020-11-28 11:29:47
 */
public class NxtUserVerify implements Serializable {
    private static final long serialVersionUID = -73590992075675281L;
    
    private Long id;
    /**
    * -1：修改绑定 1：绑定账户 2：找回密码 3：提现验证
    */
    private Integer type;
    
    private String phoneOrEmail;
    
    private Long code;
    
    private Long dateline;
    /**
    * 0:未使用 1:已使用
    */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}