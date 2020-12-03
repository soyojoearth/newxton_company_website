package com.newxton.nxtframework.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 */
public class NxtStructApiResult {

    private Integer status = 0;
    private String message = "";
    private Object result = null;

    public Map<String,Object> toMap(){
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("status", this.status);
        mapData.put("message", this.message);
        mapData.put("result", this.result);
        return mapData;
    }

    public NxtStructApiResult(){
        this.result = null;
    }

    public NxtStructApiResult(Object obj){
        this.result = obj;
    }

    public NxtStructApiResult(Integer status, String message){
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
