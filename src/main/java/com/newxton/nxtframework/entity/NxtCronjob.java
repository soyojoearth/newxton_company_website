package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtCronjob)实体类
 *
 * @author makejava
 * @since 2020-10-18 16:11:33
 */
public class NxtCronjob implements Serializable {
    private static final long serialVersionUID = 807690527375034291L;
    
    private Long id;
    
    private String jobName;
    
    private String jobKey;
    
    private Integer jobStatus;
    
    private String jobStatusDescription;
    
    private Long jobStatusDateline;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatusDescription() {
        return jobStatusDescription;
    }

    public void setJobStatusDescription(String jobStatusDescription) {
        this.jobStatusDescription = jobStatusDescription;
    }

    public Long getJobStatusDateline() {
        return jobStatusDateline;
    }

    public void setJobStatusDateline(Long jobStatusDateline) {
        this.jobStatusDateline = jobStatusDateline;
    }

}