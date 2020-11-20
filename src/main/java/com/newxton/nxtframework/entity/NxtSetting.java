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

}