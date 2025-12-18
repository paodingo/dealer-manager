package com.dealermanager.service;


import com.dealermanager.entity.CompanyEntity;
import com.dealermanager.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {


    UserEntity queryUser(String mobile);

    List<UserEntity> queryUserList(Long companryID);

    void deleteToken(Long userId);

    int mergeUserInfo(Long userId, String token, String userInfo);

    void  updatePwd(Long userId,String pwd);

    void  resetPwd(String mobile,String pwd);

    void updateLoginInfo(String mobile);

    UserEntity queryUserInfoByToken(@Param("token")  String token);

    void addCompany(CompanyEntity params);

    List<CompanyEntity> getCompanyList();

    void addUser(UserEntity params);

    CompanyEntity getCompanyBySec(String companySec);

}
