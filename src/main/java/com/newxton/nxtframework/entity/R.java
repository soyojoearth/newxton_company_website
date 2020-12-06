package com.newxton.nxtframework.entity;


import com.newxton.nxtframework.enums.RStatus;

/**
 * 通用返回类
 * 可以代替后台返回Map有2大好处
 * 1. 可以节省代码，使用error，ok等静态方法快速构造返回内容,一行可以顶替1-3行，效果可观
 * 2. 可以使得返回内容更加清晰，即使不阅读方法内容，也可以知道方法具体的返回类型
 *
 * @param <T>
 */
public class R<T> {

    private Integer status;
    private String message;
    private T result;


    public static <T> R<T> error(RStatus e) {
        R<T> r = new R<>();
        r.setMessage(e.getMessage());
        r.setStatus(e.getStatus());
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setMessage(RStatus.SUCCESS.getMessage());
        r.setStatus(RStatus.SUCCESS.getStatus());
        r.setResult(data);
        return r;
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setMessage(RStatus.SUCCESS.getMessage());
        r.setStatus(RStatus.SUCCESS.getStatus());
        return r;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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
