package com.dealermanager.entity;

import lombok.Data;

@Data
public class SecurityCallConfigEntity {
    private String company;
    private String callIndex;
    private String callTime;
    private int callValueCol;
}
