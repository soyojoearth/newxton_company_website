package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 受邀者（下家）
 */
public class NxtStructInvitees {

    private Long userId;
    private String username;
    private Long datelineCreate;
    private String datelineCreateReadable;
    private String email;
    /**
     * 下家数量
     */
    private Long inviteesCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public String getDatelineCreateReadable() {
        return datelineCreateReadable;
    }

    public void setDatelineCreateReadable(String datelineCreateReadable) {
        this.datelineCreateReadable = datelineCreateReadable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getInviteesCount() {
        return inviteesCount;
    }

    public void setInviteesCount(Long inviteesCount) {
        this.inviteesCount = inviteesCount;
    }
}
