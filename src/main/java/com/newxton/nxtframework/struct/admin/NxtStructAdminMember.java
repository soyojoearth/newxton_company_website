package com.newxton.nxtframework.struct.admin;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 */
public class NxtStructAdminMember {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户等级数字
     */
    private Integer levelNum;
    /**
     * 用户等级名称
     */
    private String levelName;
    /**
     * 注册时间
     */
    private Long datelineRegister;
    /**
     * 注册时间
     */
    private String datelineRegisterReadable;
    /**
     * 状态 0:正常 -1:黑名单
     */
    private Integer status;
    /**
     * 状态
     */
    private String statusText;
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

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getDatelineRegister() {
        return datelineRegister;
    }

    public void setDatelineRegister(Long datelineRegister) {
        this.datelineRegister = datelineRegister;
    }

    public String getDatelineRegisterReadable() {
        return datelineRegisterReadable;
    }

    public void setDatelineRegisterReadable(String datelineRegisterReadable) {
        this.datelineRegisterReadable = datelineRegisterReadable;
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

    public Long getInviteesCount() {
        return inviteesCount;
    }

    public void setInviteesCount(Long inviteesCount) {
        this.inviteesCount = inviteesCount;
    }

}
