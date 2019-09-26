package com.aisino.domain.security.entity;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class UserResponsePacket {
    private String state;
    private byte[] responseData;
    private String responseText;
    private String errorCode;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public byte[] getResponseData() {
        return this.responseData;
    }

    public void setResponseData(byte[] reponseData) {
        this.responseData = reponseData;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResponseText() {
        return this.responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
