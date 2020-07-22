package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtUploadfileCategory)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:23:31
 */
public class NxtUploadfileCategory implements Serializable {
    private static final long serialVersionUID = -29088063605844432L;
    
    private Long id;
    
    private String categoryName;
    
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