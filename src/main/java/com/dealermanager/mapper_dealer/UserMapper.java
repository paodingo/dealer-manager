package com.dealermanager.mapper_dealer;


import com.dealermanager.entity.CompanyEntity;
import com.dealermanager.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;



@Mapper
@Component
public interface UserMapper {

    UserEntity queryUser(@Param("mobile") String  mobile);

    List<UserEntity> queryUserList(@Param("companyID") Long companyID);

    int  mergeUserInfo(@Param("userId") Long userId,@Param("token")String token,@Param("userInfo") String  userInfo);

    void deleteToken(@Param("userId") Long userId);

    int updatePwd(@Param("userId") Long userId,@Param("pwd") String pwd);

    int resetPwd(@Param("mobile") String mobile,@Param("pwd") String pwd);

    int updateLoginInfo(@Param("mobile") String  mobile);

    UserEntity queryUserInfoByToken(@Param("token")  String token);

    void addCompany(@Param("params") CompanyEntity params);

    void addUser(@Param("params")UserEntity params);

    CompanyEntity getCompanyBySec(@Param("companySec") String companySec);

    List<CompanyEntity> getCompanyList();

}
