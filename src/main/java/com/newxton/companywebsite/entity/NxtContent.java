package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtContent)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:24:12
 */
public class NxtContent implements Serializable {
    private static final long serialVersionUID = 767880652293491476L;
    
    private Long id;

    private Long categoryId;
    
    private String contentTitle;
    
    private String contentDetail;
    
    private Long datelineUpdate;
    
    private Long datelineCreate;
    
    private Integer isRecommend;
    
    private Long sortId;


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

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

}