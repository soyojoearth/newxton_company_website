package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtContentCategory)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:24:14
 */
public class NxtContentCategory implements Serializable {
    private static final long serialVersionUID = -65859599285996789L;
    
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