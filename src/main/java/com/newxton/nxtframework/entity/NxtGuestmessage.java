package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtGuestmessage)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:03
 */
public class NxtGuestmessage implements Serializable {
    private static final long serialVersionUID = -58368174012142587L;
    /**
    * 【留言板】
    */
    private Long id;
    /**
    * 留言者公司
    */
    private String guestCompany;
    /**
    * 留言者名称
    */
    private String guestName;
    /**
    * 联系电话
    */
    private String guestPhone;
    /**
    * 联系邮箱
    */
    private String guestEmail;
    /**
    * 内容
    */
    private String messageContent;
    /**
    * 留言时间
    */
    private Long messageDateline;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestCompany() {
        return guestCompany;
    }

    public void setGuestCompany(String guestCompany) {
        this.guestCompany = guestCompany;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Long getMessageDateline() {
        return messageDateline;
    }

    public void setMessageDateline(Long messageDateline) {
        this.messageDateline = messageDateline;
    }

}