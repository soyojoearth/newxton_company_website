package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUser)实体类
 *
 * @author makejava
 * @since 2020-12-10 16:47:56
 */
public class NxtUser implements Serializable {
    private static final long serialVersionUID = -85671402503128999L;
    
    private Long id;
    /**
    * 登录用户名
    */
    private String username;
    /**
    * 登录密码 md5(password+salt) 全小写
    */
    private String password;
    /**
    * 密码盐
    */
    private String salt;
    /**
    * 每次注销/登录都要变化
    */
    private String token;
    
    private String phone;
    
    private String email;
    /**
    * 头像（uploadfile_id)
    */
    private Long avatarId;
    /**
    * 性别（0:保密 1:男 2:女 3:其它）
    */
    private Integer gender;
    
    private Integer levelNum;
    /**
    * 余额（放大100倍）
    */
    private Long moneyBalance;
    /**
    * 0:正常 -1:黑名单
    */
    private Integer status;
    
    private Integer isAdmin;
    
    private Long datelineCreate;
    /**
    * 上家
    */
    private Long inviterUserId;
    /**
    * 分销权限(0:不能 1:能）
    */
    private Integer canInvite;
    /**
    * 下家数量
    */
    private Long inviteesCount;
    /**
    * 推广码（唯一）
    */
    private Long inviteCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Long getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(Long moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public Long getInviterUserId() {
        return inviterUserId;
    }

    public void setInviterUserId(Long inviterUserId) {
        this.inviterUserId = inviterUserId;
    }

    public Integer getCanInvite() {
        return canInvite;
    }

    public void setCanInvite(Integer canInvite) {
        this.canInvite = canInvite;
    }

    public Long getInviteesCount() {
        return inviteesCount;
    }

    public void setInviteesCount(Long inviteesCount) {
        this.inviteesCount = inviteesCount;
    }

    public Long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(Long inviteCode) {
        this.inviteCode = inviteCode;
    }

}