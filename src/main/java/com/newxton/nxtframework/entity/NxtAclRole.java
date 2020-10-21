package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclRole)实体类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public class NxtAclRole implements Serializable {
    private static final long serialVersionUID = 876785789759595456L;
    
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