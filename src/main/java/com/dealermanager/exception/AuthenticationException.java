package com.dealermanager.exception;

import com.dealermanager.model.ResponseEnum;

/**
 * 鉴权失败异常
 *
 * @author WuLiang
 * @date 2023-03-22
 */
public class AuthenticationException extends AbstractException{
    public AuthenticationException(String message) {
        super(ResponseEnum.AUTHENTICATIONFAILED.getCode(), message);
    }
}
