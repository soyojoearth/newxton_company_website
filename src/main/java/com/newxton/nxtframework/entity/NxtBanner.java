package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtBanner)实体类
 *
 * @author makejava
 * @since 2020-08-26 16:48:06
 */
public class NxtBanner implements Serializable {
    private static final long serialVersionUID = 828387650090459002L;
    
    private Long id;
    
    private String locationName;
    
    private Long uploadfileId;
    
    private String urlpath;
    
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

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

}