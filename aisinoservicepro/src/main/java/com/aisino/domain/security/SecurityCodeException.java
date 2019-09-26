package com.aisino.domain.security;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public class SecurityCodeException extends Exception {
    private static final long serialVersionUID = -2737411271565362433L;
    private String originalExceptionMsg = "";
    private String exceptionMsg;

    public SecurityCodeException() {
    }

    public String getOriginalExceptionMsg() {
        return this.originalExceptionMsg;
    }

    public void setOriginalExceptionMsg(String originalExceptionMsg) {
        this.originalExceptionMsg = originalExceptionMsg;
    }

    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public SecurityCodeException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
