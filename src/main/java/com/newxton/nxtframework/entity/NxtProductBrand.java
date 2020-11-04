package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductBrand)实体类
 *
 * @author makejava
 * @since 2020-11-04 17:12:51
 */
public class NxtProductBrand implements Serializable {
    private static final long serialVersionUID = 846573605618068739L;
    /**
    * 产品品牌管理
    */
    private Long id;
    /**
    * 分类名称
    */
    private String brandName;
    
    private Long uploadfileId;


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

}