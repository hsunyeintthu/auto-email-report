package com.hnt.reportautoemail.model.response;

public class BaseResponse {

    private int errorCode;
    private Object object;
    private String message;

    public BaseResponse() {
    }

    public BaseResponse(int errorCode, Object object, String message) {
        this.errorCode = errorCode;
        this.object = object;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
