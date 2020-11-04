package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtDeliveryCompany)实体类
 *
 * @author makejava
 * @since 2020-11-04 14:20:58
 */
public class NxtDeliveryCompany implements Serializable {
    private static final long serialVersionUID = -20051884662255458L;
    /**
    * 快递管理
    */
    private Long id;
    
    private String name;
    
    private String code100;
    /**
    * 是否生效 1:生效 0:不生效
    */
    private Integer activity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode100() {
        return code100;
    }

    public void setCode100(String code100) {
        this.code100 = code100;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

}