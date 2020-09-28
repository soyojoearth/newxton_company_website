package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductCategory)实体类
 *
 * @author makejava
 * @since 2020-08-03 10:22:22
 */
public class NxtProductCategory implements Serializable {
    private static final long serialVersionUID = 944715962544684930L;
    
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