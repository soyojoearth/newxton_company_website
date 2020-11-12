package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclRoleGroup)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:26
 */
public class NxtAclRoleGroup implements Serializable {
    private static final long serialVersionUID = -59264606455757146L;
    
    private Long id;
    
    private Long roleId;
    
    private Long groupId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}