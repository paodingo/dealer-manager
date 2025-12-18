package com.dealermanager.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author
 */
@Setter
@Getter
@ToString(exclude = {"resultCode"})
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -4907672564611787529L;

    @JsonIgnore
    private transient ResponseEnum resultCode;

    @ApiModelProperty(value = "响应编码")
    private String code;

    @ApiModelProperty(value = "响应描述")
    @JsonAlias({"msg"})
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public CommonResponse() {
        this(ResponseEnum.SUCCESS);
    }

    public CommonResponse(ResponseEnum responseEnum) {
        this.resultCode = responseEnum;
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }


    public CommonResponse(ResponseEnum responseEnum, String message) {
        this.resultCode = responseEnum;
        this.code = responseEnum.getCode();
        this.message = message == null ? responseEnum.getMessage() : message;
    }

    /**
     * 新逻辑不推荐使用！！
     */
    @Deprecated
    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(T data) {
        this();
        this.data = data;
    }

    public boolean success() {
        return ResponseEnum.SUCCESS.getCode().equals(this.code);
    }

    public boolean fail(){
        return !success();
    }

}

