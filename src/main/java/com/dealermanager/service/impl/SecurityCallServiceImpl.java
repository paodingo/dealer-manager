package com.dealermanager.service.impl;

import com.dealermanager.entity.SecurityCallConfigEntity;
import com.dealermanager.entity.SecurityCommonConfigEntity;
import com.dealermanager.mapper_dealer.SecurityCallMapper;
import com.dealermanager.service.SecurityCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SecurityCallServiceImpl implements SecurityCallService {
    @Autowired
    SecurityCallMapper securityCallMapper;
    @Override
    public List<SecurityCommonConfigEntity> getCommonConfig() {
        return securityCallMapper.getCommonConfig();
    }

    @Override
    public List<SecurityCallConfigEntity> getCallConfig() {
        return securityCallMapper.getCallConfig();
    }

    @Override
    @Transactional
    public void addSecurityValues(int date, String company, Collection<Map<String, Object>> callValueCollection){
        securityCallMapper.deleteSecurityCallByCompnayAndDate(date,company);
        List<Map<String, Object>> tempList = new ArrayList<>();
        int size = 1;
        for(Map<String, Object> callValu : callValueCollection){
            tempList.add(callValu);
            size++;
            if(size == 2000){
                securityCallMapper.addSecurityValues(tempList);
                size = 1;
                tempList.clear();
            }
        }
        if(tempList.size() > 0){
            securityCallMapper.addSecurityValues(tempList);
        }
    }
}
