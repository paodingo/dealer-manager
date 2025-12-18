package com.dealermanager.model.login;


public class LogModel {
    private String userID;
    private String name;
    private String operation;
    private String Operation_type;
    private String info;

    public LogModel(String userID, String name, String operation, String operation_type, String info, String ip) {
        this.userID = userID;
        this.name = name;
        this.operation = operation;
        Operation_type = operation_type;
        this.info = info;
        this.ip = ip;
    }

    private String ip;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation_type() {
        return Operation_type;
    }

    public void setOperation_type(String Operation_type) {
        this.Operation_type = Operation_type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
