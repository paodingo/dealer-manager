package com.dealermanager.model.login;

import java.io.Serializable;

public class LoginResultModel implements Serializable {
    private static final long serialVersionUID = -323398655677535293L;

    public LoginResultModel(){}

    public LoginResultModel(int code, String note, Object result, int totalRows, String duration){
        this.code = code;
        this.note = note;
        this.result = result;
        this.totalRows = totalRows;
        this.duration = duration;
    }

    private int code = -1;

    private String note = "";

    private Object result;

    private int totalRows = 0;

    private String duration = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
