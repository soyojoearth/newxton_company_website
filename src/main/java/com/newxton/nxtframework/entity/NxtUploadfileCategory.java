package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUploadfileCategory)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:10
 */
public class NxtUploadfileCategory implements Serializable {
    private static final long serialVersionUID = -12438535885269010L;
    
    private Long id;
    /**
    * 分类名称
    */
    private String categoryName;
    /**
    * 上级分类id
    */
    private Long categoryPid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryPid() {
        return categoryPid;
    }

    public void setCategoryPid(Long categoryPid) {
        this.categoryPid = categoryPid;
    }

}