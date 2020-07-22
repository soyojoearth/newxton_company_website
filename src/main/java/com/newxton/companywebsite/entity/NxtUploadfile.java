package com.newxton.companywebsite.entity;

import java.io.Serializable;

/**
 * (NxtUploadfile)实体类
 *
 * @author makejava
 * @since 2020-07-22 15:23:46
 */
public class NxtUploadfile implements Serializable {
    private static final long serialVersionUID = -29669339640837893L;
    
    private Long id;
    
    private Integer fileLocation;
    
    private Long categoryId;
    
    private String fileExt;
    
    private String filenameSource;
    
    private String filenameSaved;
    
    private String filepath;
    
    private String urlpath;
    
    private Long filesize;
    
    private Long datelineUpdate;


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

}