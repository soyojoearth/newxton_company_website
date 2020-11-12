package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclUserRole)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:28
 */
public class NxtAclUserRole implements Serializable {
    private static final long serialVersionUID = 728080350396805156L;
    
    private Long id;
    
    private Long userId;
    
    private Long roleId;


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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}