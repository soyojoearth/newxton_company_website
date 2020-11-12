package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtContent)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:06:09
 */
public class NxtContent implements Serializable {
    private static final long serialVersionUID = -85833802351431190L;
    /**
    * 网站内容表【包含资讯新闻、单页标题和内容如about等】
    */
    private Long id;
    /**
    * 资讯类别
    */
    private Long categoryId;
    /**
    * 内容标题
    */
    private String contentTitle;
    /**
    * 内容HTML
    */
    private String contentDetail;
    /**
    * 更新时间（精确到毫秒）
    */
    private Long datelineUpdate;
    /**
    * 创建时间（精确到毫秒）
    */
    private Long datelineCreate;
    /**
    * 推荐
    */
    private Integer isRecommend;
    /**
    * 排序，大的在前面
    */
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