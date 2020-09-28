package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtWebPage)实体类
 *
 * @author makejava
 * @since 2020-08-04 11:21:23
 */
public class NxtWebPage implements Serializable {
    private static final long serialVersionUID = -36009658178861883L;
    
    private Long id;
    
    private String webKey;
    
    private String webTitle;
    
    private String contentTitle;
    
    private String contentDetail;
    
    private String seoKeyword;
    
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