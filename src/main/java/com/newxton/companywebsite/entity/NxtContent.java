package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtContent)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:24:23
 */
public class NxtContent implements Serializable {
    private static final long serialVersionUID = 787591561971152003L;
    
    private Long id;
    
    private Integer contentType;
    
    private String pageKey;
    
    private String pageTitle;
    
    private Long categoryId;
    
    private String contentTitle;
    
    private String contentDetail;
    
    private Long datelineUpdate;
    
    private Long datelineCreate;
    
    private Integer isRecommend;
    
    private Integer sortId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }

    public Long getDatelineUpdate() {
        return datelineUpdate;
    }

    public void setDatelineUpdate(Long datelineUpdate) {
        this.datelineUpdate = datelineUpdate;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

}