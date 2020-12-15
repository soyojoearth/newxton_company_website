package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructProductReviewsItem {
    private Long id;
    /**
     * 来源类型
     */
    private Integer originType;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 评论时间
     */
    private String date;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 附带图片
     */
    private List<String> picUrlList = new ArrayList<>();

    private List<NxtStructProductReviewsItem> replyList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOriginType() {
        return originType;
    }

    public void setOriginType(Integer originType) {
        this.originType = originType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    public List<NxtStructProductReviewsItem> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<NxtStructProductReviewsItem> replyList) {
        this.replyList = replyList;
    }

}
