package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtBanner)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:06:08
 */
public class NxtBanner implements Serializable {
    private static final long serialVersionUID = -34111239133317114L;
    /**
    * 网站轮播图
    */
    private Long id;
    /**
    * 位置名
    */
    private String locationName;
    /**
    * 图片id
    */
    private Long uploadfileId;
    /**
    * 点击链接
    */
    private String clickUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getUploadfileId() {
        return uploadfileId;
    }

    public void setUploadfileId(Long uploadfileId) {
        this.uploadfileId = uploadfileId;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

}