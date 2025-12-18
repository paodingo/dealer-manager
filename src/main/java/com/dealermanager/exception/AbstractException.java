package com.dealermanager.exception;

import com.dealermanager.model.ResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象异常类
 *
 * @author WuLiang
 * @date 2023-03-22
 */
public abstract class AbstractException extends RuntimeException {

    @Getter
    @Setter
    private String code;

    public AbstractException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public AbstractException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
