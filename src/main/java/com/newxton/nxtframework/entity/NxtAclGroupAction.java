package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclGroupAction)实体类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public class NxtAclGroupAction implements Serializable {
    private static final long serialVersionUID = -70691574712041897L;
    
    private Long id;
    
    private Long groupId;
    
    private Long actionId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

}