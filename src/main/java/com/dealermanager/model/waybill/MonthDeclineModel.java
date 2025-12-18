package com.dealermanager.model.waybill;

import lombok.Data;

@Data
public class MonthDeclineModel {

    String custNo;
    Double thisWeight;
    Double lastWeight;
    Double compareWeight;
    Double comparePercent;
}
