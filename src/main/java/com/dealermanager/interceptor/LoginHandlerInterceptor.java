package com.dealermanager.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dealermanager.entity.UserEntity;
import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import com.dealermanager.model.User;
import com.dealermanager.service.UserService;
import com.dealermanager.util.TokenUtil;
import com.dealermanager.util.UserContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dealermanager.annotation.Access;
import com.dealermanager.constants.Constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author
 */
@Slf4j
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("jacksonObjectMapper")
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /* 不是controller方法不进行拦截 */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("x-auth-token");
        Access access = ((HandlerMethod) handler).getMethod().getAnnotation(Access.class);
        // 获取接口访问身份
        String apiIdentity = Optional.ofNullable(access).map(as -> as.identity()).orElse(Constants.USER_IDENTIFY_GUEST);

        // 游客和正式用户能访问的接口都需要有session
        if (Objects.equals(apiIdentity, Constants.USER_IDENTIFY_FORMAL) || Objects.equals(apiIdentity, Constants.USER_IDENTIFY_GUEST) ||
                Objects.equals(apiIdentity, Constants.USER_IDENTIFY_SEMI_FORMAL) ||  Objects.equals(apiIdentity, Constants.USER_IDENTIFY_ADMIN)) {
            // 校验session不能为空
            if (Objects.isNull(token)) {
                return writeResponse(response, new CommonResponse(ResponseEnum.SESSION_NOT_EXIST));
            }
        }

        // 获取用户身份,查数据库
        String identity = Constants.USER_IDENTIFY_GUEST;
        if(token != null ){
            UserEntity userEntity = userService.queryUserInfoByToken(token);
            if(!Objects.isNull(userEntity)){
                identity = userEntity.getUserType() == Constants.USER_TYPE_ADMIN ? Constants.USER_IDENTIFY_ADMIN:Constants.USER_IDENTIFY_FORMAL;
                List<String> roles = new ArrayList<String>();
                roles.add(identity);
                userEntity.setRoles(roles);
                UserContextHolder.setUser( new User(userEntity));
                UserContextHolder.getUser().setIdentity(identity);
            }else {
                return writeResponse(response,new CommonResponse(ResponseEnum.USER_INVAILD));
            }
        }



        // 接口权限为正式用户时,用户也必须为正式用户
        if (Objects.equals(apiIdentity, Constants.USER_IDENTIFY_FORMAL)
                && !(identity == Constants.USER_IDENTIFY_FORMAL || identity == Constants.USER_IDENTIFY_ADMIN)) {
            return writeResponse(response, new CommonResponse(ResponseEnum.NOT_FORMAL_USER));
        }
        // 接口权限是为管理员的，用户也必须是管理员
        if (Objects.equals(apiIdentity, Constants.USER_IDENTIFY_ADMIN)
                && !Objects.equals(apiIdentity,identity)) {
            return writeResponse(response, new CommonResponse(ResponseEnum.NOT_ADMIN_USER));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContextHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (Objects.nonNull(UserContextHolder.getUser())) {
            UserContextHolder.clear();
        }
    }


    private boolean writeResponse(HttpServletResponse httpServletResponse, CommonResponse response) throws IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(jacksonObjectMapper
                    .writeValueAsString(response)
                    .getBytes());
        } catch (IOException exception) {
            log.error("write response exception! response = {}", response, exception);
            throw exception;
        }
        return false;
    }
}
