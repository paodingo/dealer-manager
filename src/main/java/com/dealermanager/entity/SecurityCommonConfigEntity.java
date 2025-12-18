package com.dealermanager.entity;

import lombok.Data;

@Data
public class SecurityCommonConfigEntity {

    private String company;
    private int beginRow;
    private int securityCodeCol;
    private int securityNameCol;
    private int sizeCol;
    private int sizeUnit;

}
