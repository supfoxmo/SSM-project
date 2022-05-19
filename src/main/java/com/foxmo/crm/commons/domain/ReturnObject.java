package com.foxmo.crm.commons.domain;

public class ReturnObject {
    private String code;
    private String message;
    private Object retDate;

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object retDate) {
        this.code = code;
        this.message = message;
        this.retDate = retDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetDate() {
        return retDate;
    }

    public void setRetDate(Object retDate) {
        this.retDate = retDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
