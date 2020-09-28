package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtGuestmessage)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:24:31
 */
public class NxtGuestmessage implements Serializable {
    private static final long serialVersionUID = -40051767999543023L;
    
    private Long id;
    
    private String guestCompany;
    
    private String guestName;
    
    private String guestPhone;
    
    private String guestEmail;
    
    private String messageContent;
    
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