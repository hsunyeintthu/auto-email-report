package com.hnt.reportautoemail.model;

public enum EmailCode {

    Success(000, "Success"),
    Fail(001,"Fail");

    public final int code;
    public final String message;

    EmailCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
