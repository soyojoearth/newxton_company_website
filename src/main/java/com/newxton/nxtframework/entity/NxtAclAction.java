package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtAclAction)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:05:20
 */
public class NxtAclAction implements Serializable {
    private static final long serialVersionUID = 122423059868593877L;
    
    private Long id;
    
    private String actionClass;
    
    private String actionName;
    
    private String actionRemark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionRemark() {
        return actionRemark;
    }

    public void setActionRemark(String actionRemark) {
        this.actionRemark = actionRemark;
    }

}