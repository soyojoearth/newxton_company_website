package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructUserInfo {

    /**
     * 用户名
     */
    private String username;
    /**
     * 头像地址
     */
    private String avatarPicUrl;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String phone;
    /**
     * inviteCode
     */
    private Long inviteCode;
    /**
     * inviteUrl
     */
    private String inviteUrl;
    /**
     * inviteQrCodePicUrl
     */
    private String inviteUrlQrImageUrl;

    private Integer status;
    private String statusText;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPicUrl() {
        return avatarPicUrl;
    }

    public void setAvatarPicUrl(String avatarPicUrl) {
        this.avatarPicUrl = avatarPicUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(Long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public String getInviteUrlQrImageUrl() {
        return inviteUrlQrImageUrl;
    }

    public void setInviteUrlQrImageUrl(String inviteUrlQrImageUrl) {
        this.inviteUrlQrImageUrl = inviteUrlQrImageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
