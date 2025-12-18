package com.dealermanager.mapper_dealer;

import com.dealermanager.entity.SecurityCallConfigEntity;
import com.dealermanager.entity.SecurityCommonConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SecurityCallMapper {
    List<SecurityCommonConfigEntity> getCommonConfig();
    List<SecurityCallConfigEntity> getCallConfig();
    int deleteSecurityCallByCompnayAndDate(@Param("date") int date, @Param("company")String company);
    int addSecurityValues(@Param("valueCollection") List<Map<String, Object>> callValueCollection);
}
