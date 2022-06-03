package com.foxmo.crm.commons.domain;

public class ReturnObject {
    private String code;
    private String message;
    private Object retData;

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object retData) {
        this.code = code;
        this.message = message;
        this.retData = retData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
