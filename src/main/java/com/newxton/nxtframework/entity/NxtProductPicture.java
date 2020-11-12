package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtProductPicture)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:07
 */
public class NxtProductPicture implements Serializable {
    private static final long serialVersionUID = -43107735759297115L;
    
    private Long id;
    /**
    * 产品id
    */
    private Long productId;
    /**
    * 图片id
    */
    private Long uploadfileId;
    /**
    * 排序（小的在前）
    */
    private Long sortId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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