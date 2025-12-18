package com.dealermanager.exception;

import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 后台运行异常处理
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResponse handleSystemException(Exception e, HttpServletRequest request) {
        // 参数绑定相关异常
        if (e instanceof NestedServletException || e instanceof PropertyAccessException) {
            log.error(" {} illegal request exception!", request.getRequestURI(), e);
            return new CommonResponse(ResponseEnum.ARGUMENTERROR.getCode(), e.getMessage());
        }

        if (e instanceof AbstractException) {
            log.error("{}:{} exception!", request.getMethod(), request.getRequestURI(), e);
            return new CommonResponse(((AbstractException) e).getCode(), e.getMessage());
        }

        log.error(request.getRequestURI() + ":服务运行异常", e);
        CommonResponse res = new CommonResponse(ResponseEnum.FAILED);
        if(e != null && e.getMessage() != null){
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
