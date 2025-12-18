package com.dealermanager.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)

public class UserEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户类型")
    private int userType;

    @ApiModelProperty(value = "座机")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态 （0 作废，1 正常）")
    private Integer isValid;

    @ApiModelProperty(value = "职务")
    private String title;

    @ApiModelProperty(value = "微信ID")
    private String wechat;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "公司ID")
    private Long companyID;

    @ApiModelProperty(value = "公司ID")
    private String company;

    @ApiModelProperty(value = "部门ID")
    private Long departmentID;

    @ApiModelProperty(value = "部门ID")
    private String department;

    @ApiModelProperty(value = "证件号")
    private String certno;

    @ApiModelProperty(value = "登陆次数")
    private Integer loginTimes;

    @ApiModelProperty(value = "最后登陆时间")
    private Date lastloginTime;

    @ApiModelProperty(value = "创建日期")
    private int createDate;

    @ApiModelProperty(value = "roles")
    private List<String> roles;
    
}
