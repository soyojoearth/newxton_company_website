package com.newxton.supercompanyapi.entity;

import java.io.Serializable;

/**
 * (NxtSetting)实体类
 *
 * @author makejava
 * @since 2020-07-23 19:30:08
 */
public class NxtSetting implements Serializable {
    private static final long serialVersionUID = -15725629083862033L;
    
    private Long id;
    
    private String settingKey;
    
    private String settingValue;
    
    private String settingName;
    
    private String displayType;
    
    private Long datelineUpdated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Long getDatelineUpdated() {
        return datelineUpdated;
    }

    public void setDatelineUpdated(Long datelineUpdated) {
        this.datelineUpdated = datelineUpdated;
    }

}