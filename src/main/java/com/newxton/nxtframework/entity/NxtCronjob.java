package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtCronjob)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:06:10
 */
public class NxtCronjob implements Serializable {
    private static final long serialVersionUID = 761977878877770207L;
    /**
    *  后台任务
    */
    private Long id;
    /**
    * 任务名称
    */
    private String jobName;
    /**
    * 任务唯一标识
    */
    private String jobKey;
    /**
    * 任务状态（0:off 1:on)
    */
    private Integer jobStatus;
    /**
    * 任务进度状态描述
    */
    private String jobStatusDescription;
    /**
    * 任务进度状态更新时间
    */
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