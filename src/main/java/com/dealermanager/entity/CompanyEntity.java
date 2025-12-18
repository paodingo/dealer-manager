package com.dealermanager.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

public class CompanyEntity {

    @ApiModelProperty(value = "公司ID")
    Long id ;

    @ApiModelProperty(value = "公司名称")
    String name ;

    @ApiModelProperty(value = "公司地址")
    String address ;

    @ApiModelProperty(value = "公司电话")
    String phone ;

    @ApiModelProperty(value = "公司秘鑰")
    String companySec ;
}
