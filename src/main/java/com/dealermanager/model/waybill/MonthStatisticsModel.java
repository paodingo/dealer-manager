package com.dealermanager.model.waybill;

import lombok.Data;

@Data
public class MonthStatisticsModel {

    private String month;
    private Long orderNum;
    private Double weight;
    private Double totalAmountUS;
    private Double profit;
    private Long custNum;

    public MonthStatisticsModel(){
        this.month = "";
        this.orderNum = 0l;
        this.weight = 0d;
        this.totalAmountUS = 0d;
        this.profit = 0d;
        this.custNum = 0l;
    }
    public MonthStatisticsModel(String month,Long orderNum,Double weight,Double totalAmountUS,Double profit,Long custNum){
        this.month = month;
        this.orderNum = orderNum;
        this.weight = weight;
        this.totalAmountUS = totalAmountUS;
        this.profit = profit;
        this.custNum = custNum;
    }
}
