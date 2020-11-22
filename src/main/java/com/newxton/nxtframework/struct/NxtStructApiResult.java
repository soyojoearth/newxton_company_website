package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 */
public class NxtStructApiResult {

    private Integer code = 0;
    private String message = "";
    private Object result = null;

    public NxtStructApiResult(){
        this.result = null;
    }

    public NxtStructApiResult(Object obj){
        this.result = obj;
    }

    public NxtStructApiResult(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
