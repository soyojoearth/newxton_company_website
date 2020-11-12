package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclGroupAction)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:24
 */
public class NxtAclGroupAction implements Serializable {
    private static final long serialVersionUID = 569794397849953372L;
    
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