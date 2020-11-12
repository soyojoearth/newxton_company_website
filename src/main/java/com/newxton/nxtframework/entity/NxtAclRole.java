package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclRole)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:24
 */
public class NxtAclRole implements Serializable {
    private static final long serialVersionUID = 763926935213765033L;
    
    private Long id;
    
    private String roleName;
    
    private String roleRemark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

}