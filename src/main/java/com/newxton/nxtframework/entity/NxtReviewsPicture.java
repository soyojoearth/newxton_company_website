package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtReviewsPicture)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:45
 */
public class NxtReviewsPicture implements Serializable {
    private static final long serialVersionUID = 670687922210915026L;
    /**
     * 评论携带图片
     */
    private Long id;

    private Long reviewsId;

    private Long uploadfileId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Long reviewsId) {
        this.reviewsId = reviewsId;
    }

    public Long getUploadfileId() {
        return uploadfileId;
    }

    public void setUploadfileId(Long uploadfileId) {
        this.uploadfileId = uploadfileId;
    }

}