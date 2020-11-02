package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductBrand)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:04:21
 */
public class NxtProductBrand implements Serializable {
    private static final long serialVersionUID = -42711501511584401L;
    /**
    * 产品品牌管理
    */
    private Long id;
    /**
    * 分类名称
    */
    private String brandName;
    
    private Long uploadfileId;
    /**
    * 排序，大的在前
    */
    private Long sortId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getUploadfileId() {
        return uploadfileId;
    }

    public void setUploadfileId(Long uploadfileId) {
        this.uploadfileId = uploadfileId;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

}