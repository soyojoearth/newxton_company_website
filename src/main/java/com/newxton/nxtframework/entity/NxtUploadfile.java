package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtUploadfile)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:07:09
 */
public class NxtUploadfile implements Serializable {
    private static final long serialVersionUID = -13575440800168348L;
    
    private Long id;
    /**
    * 0:网盘文件 1:七牛云oss 2:阿里云oss 3:本地
    */
    private Integer fileLocation;
    /**
    * 0:图片 正数:category表分类
    */
    private Long categoryId;
    /**
    * 文件类型后缀（小写）
    */
    private String fileExt;
    /**
    * 原始文件名
    */
    private String filenameSource;
    /**
    * 存储文件名
    */
    private String filenameSaved;
    /**
    * 相对于ftp或oss根目录的路径
    */
    private String filepath;
    /**
    * 相对于域名的路径
    */
    private String urlpath;
    /**
    * 文件大小 字节
    */
    private Long filesize;
    /**
    * 保存或更新时间（精确到毫秒）
    */
    private Long datelineUpdate;
    /**
    * 网盘地址
    */
    private String netdiskUrl;
    /**
    * 网盘下载密码
    */
    private String netdiskPwd;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(Integer fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFilenameSource() {
        return filenameSource;
    }

    public void setFilenameSource(String filenameSource) {
        this.filenameSource = filenameSource;
    }

    public String getFilenameSaved() {
        return filenameSaved;
    }

    public void setFilenameSaved(String filenameSaved) {
        this.filenameSaved = filenameSaved;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Long getDatelineUpdate() {
        return datelineUpdate;
    }

    public void setDatelineUpdate(Long datelineUpdate) {
        this.datelineUpdate = datelineUpdate;
    }

    public String getNetdiskUrl() {
        return netdiskUrl;
    }

    public void setNetdiskUrl(String netdiskUrl) {
        this.netdiskUrl = netdiskUrl;
    }

    public String getNetdiskPwd() {
        return netdiskPwd;
    }

    public void setNetdiskPwd(String netdiskPwd) {
        this.netdiskPwd = netdiskPwd;
    }

}