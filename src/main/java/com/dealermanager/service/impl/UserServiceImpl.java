package com.dealermanager.service.impl;

import com.dealermanager.entity.CompanyEntity;
import com.dealermanager.entity.UserEntity;
import com.dealermanager.mapper_dealer.UserMapper;

import com.dealermanager.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService  {


    @Autowired
    UserMapper userMapper;

    @Override
    public int mergeUserInfo(Long userId, String token, String userInfo){
        log.info("更新token");
        int res = userMapper.mergeUserInfo(userId,token,userInfo);
        return res;
    }

    @Override
    public void updatePwd(Long userId, String pwd) {
        int  res = userMapper.updatePwd(userId,pwd);
    }

    @Override
    public void resetPwd(String mobile, String pwd) {
        int  res = userMapper.resetPwd(mobile,pwd);
    }



    @Override
    public void updateLoginInfo(String mobile) {
        int  res = userMapper.updateLoginInfo(mobile);
    }

    @Override
    public UserEntity queryUser(String mobile) {
        log.info("查询用户：" + mobile);
        UserEntity userInfo = userMapper.queryUser(mobile);
        return userInfo;
    }

    @Override
    public List<UserEntity> queryUserList(Long companryID){
        return userMapper.queryUserList(companryID);
    }

    @Override
    public void deleteToken(Long userId){
        log.info("删除token：" +userId );
        userMapper.deleteToken(userId);
    }

    @Override
    public UserEntity queryUserInfoByToken(@Param("token")  String token){
        return  userMapper.queryUserInfoByToken(token);
    }

    @Override
    public void addCompany(CompanyEntity params) {
        params.setCompanySec(UUID.randomUUID().toString());
        userMapper.addCompany(params);
    }


    @Override
    public List<CompanyEntity> getCompanyList(){
        return userMapper.getCompanyList();
    }
    @Override
    public void addUser(UserEntity params) {
        userMapper.addUser(params);
    }

    @Override
    public CompanyEntity getCompanyBySec(String companySec){
        return userMapper.getCompanyBySec(companySec);
    }

}
