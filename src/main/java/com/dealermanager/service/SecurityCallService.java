package com.dealermanager.service;

import com.dealermanager.entity.SecurityCallConfigEntity;
import com.dealermanager.entity.SecurityCommonConfigEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SecurityCallService {

    List<SecurityCommonConfigEntity> getCommonConfig();
    List<SecurityCallConfigEntity> getCallConfig();
    void addSecurityValues(int date, String company, Collection<Map<String, Object>> callValueCollection);
}
