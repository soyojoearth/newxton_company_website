package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductCategory)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:08
 */
public class NxtProductCategory implements Serializable {
    private static final long serialVersionUID = 665039909079748919L;
    
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