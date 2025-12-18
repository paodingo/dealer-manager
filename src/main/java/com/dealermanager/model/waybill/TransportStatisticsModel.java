package com.dealermanager.model.waybill;

import lombok.Data;

@Data
public class TransportStatisticsModel {

    private int transport;

    private Double weight;

    private Double totalAmountUS;
}
