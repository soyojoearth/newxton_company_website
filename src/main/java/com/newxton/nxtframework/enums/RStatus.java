package com.newxton.nxtframework.enums;

/**
 * 错误状态的枚举类
 *
 * @author qiqi.chen
 */
public enum RStatus {

    /**
     * 通用的参数错误
     */
    ERROR_PARAM(52, "参数错误"),

    /**
     * 成功
     */
    SUCCESS(0, ""),

    /**
     * 无内容
     */
    NO_CONTENT(49, "找不到对应的内容"),

    /**
     * 提前引用
     */
    QUOTE_IN_ADVANCE(55, "该品牌已被产品引用，请先取消引用");


    private Integer status;
    private String message;

    RStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
