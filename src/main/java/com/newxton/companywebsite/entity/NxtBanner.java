package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtBanner)实体类
 *
 * @author makejava
 * @since 2020-08-26 09:15:28
 */
public class NxtBanner implements Serializable {
    private static final long serialVersionUID = 673646185635307163L;
    
    private Long id;
    
    private Long categoryId;
    
    private Long uploadfileId;
    
    private String urlpath;
    
    private String clickUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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