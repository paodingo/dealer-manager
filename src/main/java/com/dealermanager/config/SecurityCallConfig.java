package com.dealermanager.config;

import com.dealermanager.entity.SecurityCallConfigEntity;
import com.dealermanager.entity.SecurityCommonConfigEntity;
import com.dealermanager.service.SecurityCallService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Order(1)
public class SecurityCallConfig implements CommandLineRunner {
    public static Map<String, SecurityCommonConfigEntity> commonConfigEntityMap = new HashMap<>();
    public static Map<String, List<SecurityCallConfigEntity>> callConfigEntityMap = new HashMap<>();

    @Autowired
    SecurityCallService securityCallService;

    @Override
    public void run(String... args) throws Exception {
        // 查询数据库数据
        commonConfigEntityMap.clear();
        callConfigEntityMap.clear();

        List<SecurityCallConfigEntity> callConfigList = securityCallService.getCallConfig();
        List<SecurityCommonConfigEntity> commonConfigList = securityCallService.getCommonConfig();
        //  callConfigEntityMap.putAll(callConfigList.stream().collect(Collectors.toMap(SecurityCallConfigEntity::getCompany, Function.identity(),(key1,key2) -> key2)));
        commonConfigEntityMap.putAll(commonConfigList.stream().collect(Collectors.toMap(SecurityCommonConfigEntity::getCompany, Function.identity(), (key1, key2) -> key2)));
        for (SecurityCallConfigEntity configEntity : callConfigList) {
            String key = configEntity.getCompany() ;
            if (callConfigEntityMap.containsKey(key)) {
                callConfigEntityMap.get(key).add(configEntity);
            } else {
                List<SecurityCallConfigEntity> tempList = new ArrayList<>();
                tempList.add(configEntity);
                callConfigEntityMap.put(key,tempList);
            }
        }
    }
}
