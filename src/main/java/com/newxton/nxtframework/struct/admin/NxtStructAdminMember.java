package com.newxton.nxtframework.struct.admin;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
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

    private String phone;

    private String email;
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
    /**
     * 性别（0:保密 1:男 2:女 3:其它）
     */
    private Integer gender;

    /**
     * 是否已经被拉黑
     */
    private boolean isBlock;

    /**
     * 分销权限是否打开
     * @return
     */
    private boolean canInvite;
    /**
     * 邀请人id
     */
    private Long inviterUserId;
    /**
     * 邀请人名字
     */
    private String inviterUsername;

    /**
     * 余额
     */
    private Float balance;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public boolean isCanInvite() {
        return canInvite;
    }

    public void setCanInvite(boolean canInvite) {
        this.canInvite = canInvite;
    }

    public Long getInviterUserId() {
        return inviterUserId;
    }

    public void setInviterUserId(Long inviterUserId) {
        this.inviterUserId = inviterUserId;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
