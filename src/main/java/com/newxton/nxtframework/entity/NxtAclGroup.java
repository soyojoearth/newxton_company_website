package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclGroup)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:22
 */
public class NxtAclGroup implements Serializable {
    private static final long serialVersionUID = 959107009854924007L;
    
    private Long id;
    
    private String groupName;
    
    private String groupRemark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

}