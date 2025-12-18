package com.dealermanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.dealermanager.annotation.Access;
import com.dealermanager.constants.Constants;
import com.dealermanager.entity.CompanyEntity;
import com.dealermanager.entity.UserEntity;
import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import com.dealermanager.model.login.LoginUserModel;
import com.dealermanager.service.UserService;
import com.dealermanager.util.TokenUtil;
import com.dealermanager.util.UserContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@Api(tags="用户")
@RequestMapping("/yixun/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;


    @CrossOrigin
    @ApiOperation(value="用户登录", notes="login")
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    public CommonResponse login(@RequestBody LoginUserModel params) {
        CommonResponse rm = new CommonResponse();
        log.info("-----login------");
        try {
            UserEntity userEntity = userService.queryUser(params.getUsername());
            if (userEntity == null) {
                rm = new CommonResponse(ResponseEnum.USER_NOT_EXIST);
                return rm;
            }
            if(!userEntity.getPassword().equals(TokenUtil.pwdEncryption(params.getPassword()))) {
                rm = new CommonResponse(ResponseEnum.PASSWORD_ERROR);
            } else {
                //a、获取用户信息
                //b、生成token
                //c、获取用户权限
                //d、放入session
                //e、返回给前端

                String token = TokenUtil.generateToken(userEntity.getName(),userEntity.getMobile());
                Map<String, Object> result = new HashMap();
                //result.put("userEntity", userEntity);
                result.put("token", token);
                userEntity.setPassword("");
                rm.setData(result);
                log.info("生成token: ",token);
                userService.mergeUserInfo(userEntity.getId(),token,userEntity.toString());
                userService.updateLoginInfo(params.getUsername());
                log.info("==login()==result: " + JSONObject.toJSONString(result));
            }
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("login()==params:["+JSONObject.toJSONString(params) +"]",e);
        }
        return rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="根据token获取用户信息", notes="根据token获取用户信息")
    @RequestMapping(value="/getUserInfo",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse getUserInfo(){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----logout------");
            UserEntity userEntity = UserContextHolder.getUser().getEntity();
            rm.setData(userEntity);
            log.info("logout():");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("logout()==]",e);
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="用户登出", notes="用户登出")
    @RequestMapping(value="/logout",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse logout(){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----logout------");
            userService.deleteToken(UserContextHolder.getUserId());//删除该用户的token记录
            log.info("logout():");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("logout()==]",e);
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="修改密码", notes="修改密码")
    @RequestMapping(value="/changePwd",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse changePwd(@RequestBody LoginUserModel params){
        CommonResponse rm = new CommonResponse();
        log.info("-----changePwd----- " );
        try {
            UserEntity userEntity = userService.queryUser(UserContextHolder.getUser().getEntity().getMobile());
            if (userEntity == null) {
                rm = new CommonResponse(ResponseEnum.USER_NOT_EXIST);
                return rm;
            }
            if(!userEntity.getPassword().equals(TokenUtil.pwdEncryption(params.getPassword()))) {
                rm = new CommonResponse(ResponseEnum.PASSWORD_ERROR);
            } else {
                String newPwd = TokenUtil.pwdEncryption(params.getNewLoginPwd());
                userService.updatePwd(UserContextHolder.getUserId(),newPwd);
                userService.deleteToken(UserContextHolder.getUserId());
                log.info("-----changePwd success-----" );
            }
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("修改密码异常",e);
        }
        return rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ADMIN)
    @ApiOperation(value="管理员重置用户密码", notes="管理员重置用户密码,需要用户手机号")
    @RequestMapping(value="/resetPwd",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse resetPwd(@RequestBody LoginUserModel params){
        CommonResponse rm = new CommonResponse();
        log.info("-----resetPwd----- " );
        try {
            UserEntity userEntity = userService.queryUser(params.getUsername());
            if (userEntity == null) {
                rm = new CommonResponse(ResponseEnum.USER_NOT_EXIST);
                return rm;
            }
            String newPwd = TokenUtil.pwdEncryption(params.getNewLoginPwd());
            userService.resetPwd(params.getUsername(),newPwd);
            userService.deleteToken(userEntity.getId());
            log.info("-----resetPwd success-----" );
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("修改密码异常",e);
        }
        return rm;
    }


    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ADMIN)
    @ApiOperation(value="新增用户", notes="新增用户")
    @RequestMapping(value="/addUser",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse addUser(@RequestBody UserEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----addUser----- " + JSONObject.toJSONString(params));
        try {
            UserEntity userEntity = userService.queryUser(params.getMobile());
            if(userEntity != null){
                rm = new CommonResponse(ResponseEnum.USER_EXIST);
            }else {
                params.setPassword(TokenUtil.pwdEncryption(params.getPassword()));
                userService.addUser(params);
            }
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("新增用户异常",e);
        }
        return rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="查询用户列表", notes="查询用户列表")
    @RequestMapping(value="/getUserList",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse getUserList(@RequestBody UserEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----getUserList----- " );
        try {
            if(Constants.USER_IDENTIFY_ADMIN.equals(UserContextHolder.getIdentity())){
                if(params.getCompanyID()==null){
                    return new CommonResponse(ResponseEnum.COMPANY_NOT_EXIST);
                }
            }else {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            List<UserEntity> userEntityList = userService.queryUserList(params.getCompanyID());
            rm.setData(userEntityList);
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("新增用户异常",e);
        }
        log.info("-----getUserList-----end " );
        return rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ADMIN)
    @ApiOperation(value="新增公司", notes="新增公司")
    @RequestMapping(value="/addCompany",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse addCompany(@RequestBody CompanyEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----addCompany----- " + JSONObject.toJSONString(params) );
        try {
            userService.addCompany(params);
            rm.setData(params);
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("新增用户异常",e);
        }
        log.info("-----addCompany-----end ");
        return rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ADMIN)
    @ApiOperation(value="公司列表查询", notes="公司列表查询")
    @RequestMapping(value="/getCompanyList",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse getCompanyList(){
        CommonResponse rm = new CommonResponse();
        log.info("-----getCompanyList----- " );
        try {
            List<CompanyEntity> companyEntityList = userService.getCompanyList();
            rm.setData(companyEntityList);
            return rm;
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("新增用户异常",e);
        }
        log.info("-----getCompanyList-----end " );
        return rm;
    }

}
