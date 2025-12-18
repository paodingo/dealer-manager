package com.dealermanager.model;

import lombok.Getter;

public enum ResponseEnum {
    SUCCESS("20000", "success"),

    SESSION_NOT_EXIST("A0101", "token 不存在!"),

    SESSION_EXPIRED_ERROR("A0102", "token 过期!"),

    NOT_FORMAL_USER("A0103", "您无权限操作!"),

    NOT_ADMIN_USER("A0104", "非管理员，无权限操作!"),

    PASSWORD_ERROR("A0201", "密码错误"),

    USER_NOT_EXIST("A0202", "用户不存在"),

    USER_EXIST("A0203", "用户已存在"),

    COMPANYSEC("A0204", "请补充公司标识"),

    USER_INVAILD("A0205", "登录已失效，请重新登录"),

    COMPANY_NOT_EXIST("A0206", "公司不存在"),

    BILL_AND_USER_COMPANY_NOT_CONSTISTENT("A0301", "运单公司和用户公司不一致"),

    BILL_ID_NOT_EXIST("A0301", "运单ID错误"),

    BILL_ID_NULL("A0302", "请补充运单ID"),

    BILLSTAT_ID_NULL("A0303", "请补充运单状态ID"),

    FAILED("A9999", "加载失败,请稍后重试"),

    AUTHENTICATIONFAILED("A9998", "鉴权失败"),

    ARGUMENTERROR("A9997", "参数异常"),

    CALL_CONFIG_ERROR("A0401", "证券报价配置缺少"),;



    @Getter
    private String code;
    @Getter
    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
