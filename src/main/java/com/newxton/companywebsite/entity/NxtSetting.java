package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtSetting)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:23:53
 */
public class NxtSetting implements Serializable {
    private static final long serialVersionUID = -74249768519835695L;
    
    private Long id;
    
    private String settingKey;
    
    private String settingValue;
    
    private String settingName;
    
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

    public Long getDatelineUpdated() {
        return datelineUpdated;
    }

    public void setDatelineUpdated(Long datelineUpdated) {
        this.datelineUpdated = datelineUpdated;
    }

}