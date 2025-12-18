package com.dealermanager.model.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by
 */
@Data
public class LoginUserModel implements Serializable {

    private static final long serialVersionUID = 1725550020415712199L;

 //   @ApiModelProperty(value = "登录用户名" , required = false,example = "wujunfeng")
 //   private String loginName;

    @ApiModelProperty(value = "登录用户密码" , required = true,example = "Y2FieHlqVWc3RUYyemRmStSxQdPUS1A0WHDqFC0gC/o=")
    private String password;

    @ApiModelProperty(value = "登录手机号" , required = true,example = "18814090734")
    private String username;

    @ApiModelProperty(value = "登录用户新密码 " , required = true,example = "Y2FieHlqVWc3RUYyemRmStSxQdPUS1A0WHDqFC0gC/o=")
    private String newLoginPwd;


}
