package com.dealermanager.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaybillStatEntity {

    @ApiModelProperty(value = "状态ID")
    private Long id;

    @ApiModelProperty(value = "运单ID")
    private Long waybillID;

    @ApiModelProperty(value = "货物状态  1-出库,2-出境")
    private int packageStat;

    @ApiModelProperty(value = "创建时间")
    private String createtime ;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorID;
}
