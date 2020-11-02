package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtWebPage)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:12
 */
public class NxtWebPage implements Serializable {
    private static final long serialVersionUID = -84904335115730564L;
    /**
    * 网站页面表【包含页面标题和其它设置等】
    */
    private Long id;
    /**
    * 标识(会根据web_key 查询)
    */
    private String webKey;
    /**
    * 页面标题
    */
    private String webTitle;
    /**
    * 内容标题
    */
    private String contentTitle;
    /**
    * 内容HTML
    */
    private String contentDetail;
    /**
    * SEO关键词
    */
    private String seoKeyword;
    /**
    * 更新时间（精确到毫秒）
    */
    private Long datelineUpdate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
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

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public Long getDatelineUpdate() {
        return datelineUpdate;
    }

    public void setDatelineUpdate(Long datelineUpdate) {
        this.datelineUpdate = datelineUpdate;
    }

}