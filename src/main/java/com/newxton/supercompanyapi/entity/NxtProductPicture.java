package com.newxton.supercompanyapi.entity;

import java.io.Serializable;

/**
 * (NxtProductPicture)实体类
 *
 * @author makejava
 * @since 2020-08-03 10:22:38
 */
public class NxtProductPicture implements Serializable {
    private static final long serialVersionUID = 699371617093222521L;
    
    private Long id;
    
    private Long productId;
    
    private Long uploadfileId;
    
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