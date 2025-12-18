package com.dealermanager.service;

import com.dealermanager.model.CommonResponse;

import javax.servlet.http.HttpServletResponse;


public interface CommonService {

    CommonResponse downloadFile(String fileId, HttpServletResponse response);
}
