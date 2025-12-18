package com.dealermanager.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaybillEntity {

    @ApiModelProperty(value = "主键")
    private Long id ;

    @ApiModelProperty(value = "出发地")
    private String departurePlace;

    @ApiModelProperty(value = "目的地")
    private int destination;

    @ApiModelProperty(value = "运单编号")
    private String orderNo;

    @ApiModelProperty(value = "制单日期,Y-m-d H:i:s")
    private String orderTime;

    @ApiModelProperty(value = "预计到达日,Y-m-d H:i:s")
    private String expectArrDate;

    @ApiModelProperty(value = "货物内容")
    private String goodsDesc;

    @ApiModelProperty(value = "货物内容:数量")
    private int goodsDescNum;

    @ApiModelProperty(value = "件数")
    private int num;

    @ApiModelProperty(value = "重量，单位千克")
    private Double weight;

    @ApiModelProperty(value = "体积，单位立方米")
    private Double volume;

    @ApiModelProperty(value = "密度")
    private Double density;

    @ApiModelProperty(value = "货物最新状态  1-出库,2-出境")
    private Integer packageStat;

    @ApiModelProperty(value = "状态更新日期")
    private Integer statDate;

    @ApiModelProperty(value = "客户号")
    private String custNo;

    @ApiModelProperty(value = "单价")
    private Double unitPrice;

    @ApiModelProperty(value = "货值")
    private Double goodsValue;

    @ApiModelProperty(value = "保险费")
    private Double insurance;

    @ApiModelProperty(value = "保险费利率")
    private Double insuranceRate;

    @ApiModelProperty(value = "利润")
    private Double profit;

    @ApiModelProperty(value = "每千克货物价值")
    private Double valuePerKg;

    @ApiModelProperty(value = "打包费")
    private Double packingFee;

    @ApiModelProperty(value = "外部成本")
    private Double foreignCost;

    @ApiModelProperty(value = "总成本")
    private Double finalCost;

    @ApiModelProperty(value = "转运费")
    private Double forwardingFee;

    @ApiModelProperty(value = "垫资")
    private Double advanceCapital;

    @ApiModelProperty(value = "应收总金额（US）")
    private Double totalAmountUS;

    @ApiModelProperty(value = "应收总金额（CN）")
    private Double totalAmountCN;

    @ApiModelProperty(value = "float")
    private Double exchangeRate;

    @ApiModelProperty(value = "国内供应商")
    private String domesticSuppliers;

    @ApiModelProperty(value = "清关公司")
    private String custClrCom;

    @ApiModelProperty(value = "更新时间")
    private String createtime;

    @ApiModelProperty(value = "修改时间")
    private String updatetime;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorID;

    @ApiModelProperty(value = "修改人ID")
    private Long  updatorID;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "修改人")
    private String  updator;

    @ApiModelProperty(value = "所属公司")
    private String company;

    @ApiModelProperty(value = "所属公司ID")
    private Long companyID;

    @ApiModelProperty(value = "公司秘钥")
    private String companySec;

    @ApiModelProperty(value = "运输方式")
    private int transport;

    private int page;

    private int limit;

    @ApiModelProperty(value = "订单查询开始时间")
    private String beginDate;

    @ApiModelProperty(value = "订单查询结束时间")
    private String endDate;

    @ApiModelProperty(value = "订单月份")
    private String month;

    @ApiModelProperty(value = "员工1提成")
    private Double employee1Commission;

    @ApiModelProperty(value = "员工1工资")
    private Double employee1Salary;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "减少或增多百分比")
    private Double percent;

    @ApiModelProperty(value = "减少或增多值")
    private Double difference;

    @ApiModelProperty(value = "减少或增长标志 0|减少，1|增加")
    private Integer compareFlag;
}
