package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtSetting)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:09
 */
public class NxtSetting implements Serializable {
    private static final long serialVersionUID = -58763712983083270L;
    /**
    * 网站设置表【相当于一个key-value数据库】
    */
    private Long id;
    /**
    * key
    */
    private String settingKey;
    /**
    * value
    */
    private String settingValue;
    /**
    * 设置名
    */
    private String settingName;
    /**
    * input、textarea两种
    */
    private String displayType;
    /**
    * 保存或更新时间（精确到毫秒）
    */
    private Long datelineUpdated;
    /**
    * 填写说明
    */
    private String placeholder;


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

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}