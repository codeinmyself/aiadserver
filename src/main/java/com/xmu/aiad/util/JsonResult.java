package com.xmu.aiad.util;

import java.io.Serializable;

/**
 * Created by yyp on 2016/10/8.
 */
public class JsonResult implements Serializable{

    private String code;
    private String message;
    private Object detail;

    public JsonResult() {
    }

    public JsonResult(String code, String message, Object detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
