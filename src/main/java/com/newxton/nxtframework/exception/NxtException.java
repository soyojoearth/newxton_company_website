package com.newxton.nxtframework.exception;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 */
public class NxtException extends Exception {

    private String nxtExecptionCode;
    private String nxtExecptionMessage;

    public NxtException() {
        super();
    }

    public NxtException(String message) {
        super(message);
        nxtExecptionMessage = message;
    }

    public NxtException(String code, String message) {
        super();
        this.nxtExecptionCode = code;
        this.nxtExecptionMessage = message;
    }

    public String getNxtExecptionCode() {
        return nxtExecptionCode;
    }

    public String getNxtExecptionMessage() {
        return nxtExecptionMessage;
    }

}
