package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructGuestMessage {

    private String guestCompany;
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private String messageContent;

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

}
