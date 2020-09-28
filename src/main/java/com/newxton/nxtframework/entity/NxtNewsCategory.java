package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtNewsCategory)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:25:13
 */
public class NxtNewsCategory implements Serializable {
    private static final long serialVersionUID = -39894514990635166L;
    
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