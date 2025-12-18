package com.dealermanager.exception;

import com.dealermanager.model.ResponseEnum;

/**
 * 自定义异常
 *
 * @author WuLiang
 * @date 2023-03-22
 */
public class CustomException extends AbstractException{
    public CustomException(ResponseEnum responseEnum, String message) {
        super(responseEnum.getCode(), message);
    }

    public CustomException(String code, String message) {
        super(code, message);
    }
}
