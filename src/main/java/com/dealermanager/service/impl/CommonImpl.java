package com.dealermanager.service.impl;


import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import com.dealermanager.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TACoordinationReportImpl
 * @Description TODO
 * @Author wujunfeng
 * @Date 2021/9/22 17:14
 * @Version 1.0
 **/
@Slf4j
@Service
public class CommonImpl implements CommonService {



    @Override
    public CommonResponse downloadFile(String fileId, HttpServletResponse response) {
        try {


        } catch (Exception e) {
            log.error("下载附件异常", e);
            return new CommonResponse(ResponseEnum.FAILED);
        }
        return null;
    }
}