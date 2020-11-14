//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newxton.nxtframework.config;

import com.github.yt.web.result.BaseResultConfig;

/**
 * http请求返回体的默认实现
 * @author liujiasheng
 */
public class SimpleResultConfig implements BaseResultConfig {
    public SimpleResultConfig() {
    }

    @Override
    public String getErrorCodeField() {
        return "status";
    }

    @Override
    public String getMessageField() {
        return "message";
    }

    @Override
    public String getResultField() {
        return "result";
    }

    @Override
    public String getMoreResultField() {
        return "moreResult";
    }

    @Override
    public Object getDefaultSuccessCode() {
        return 0;
    }

    @Override
    public Object getDefaultSuccessMessage() {
        return "操作成功";
    }

    @Override
    public Object getDefaultErrorCode() {
        return 1;
    }

    @Override
    public Object getDefaultErrorMessage() {
        return "系统异常";
    }

    @Override
    public Object convertErrorCode(Object errorCode) {
        if (errorCode instanceof String) {
            String strErrorCode = (String)errorCode;
            return strErrorCode.contains("_") ? Integer.parseInt(strErrorCode.split("_")[1]) : Integer.parseInt(strErrorCode);
        } else {
            return errorCode instanceof Integer ? errorCode : errorCode;
        }
    }
}
