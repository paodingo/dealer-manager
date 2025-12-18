package com.dealermanager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dealermanager.annotation.Access;
import com.dealermanager.constants.Constants;
import com.dealermanager.model.CommonResponse;
import com.github.benmanes.caffeine.cache.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;


/**
 * @ClassName TACoordinationReport
 * @Description TA协调表
 * @Author wujunfeng
 * @Date 2021/9/22 14:44
 * @Version 1.0
 **/
@Slf4j
@Api(tags = "公共接口")
@RestController
@RequestMapping("/yixun/token")
public class CommonController {


    private final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    private final static String APP_ID = "wxccc5d56b86f5cd54";

    private final static String APP_SECRET = "49787f787740140fd9cd834e7a73ed83";

    @Autowired
    Cache<String, Object> accessToken;

    @CrossOrigin
    @ApiOperation(value="获取token", notes="获取token")
    @RequestMapping(value="/getToken",method = RequestMethod.POST)
    @ResponseBody
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    public CommonResponse getToken(){
        CommonResponse rm = new CommonResponse();
        try {
            Object token = accessToken.asMap().get("token");
            if (token == null) {
                HttpClient client = HttpClients.createDefault();
                String tokenUrl = MessageFormat.format(GET_TOKEN_URL, APP_ID, APP_SECRET);
                HttpGet request = new HttpGet(tokenUrl);
                HttpResponse response = client.execute(request);
                JSONObject object = getResponseJson(response);
                if (object == null) {
                    return null;
                }
                token = object.getString("access_token");
                accessToken.put("token", token);
            }
            rm.setData(token);

        }catch (Exception e){
            log.info("获取token异常",e.getMessage());
        }
        return rm;
    }

    //getResponseJson();
    private static JSONObject getResponseJson(HttpResponse response) throws IOException {
        JSONObject json = null;
        HttpEntity entity = response.getEntity();
        if(entity!=null){
            String result = EntityUtils.toString(entity,"UTF-8");
            json = JSONObject.parseObject(result);
        }
        if(ObjectUtils.isEmpty(json)){
            return null;
        }
        return json;
    }


    /**
     * 微信用户token认证
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/check", method = {RequestMethod.GET})
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    @CrossOrigin
    @ApiOperation(value="微信用户token认证", notes="微信用户token认证")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("开始进行微信token认证");
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // 校验通过，原样返回echostr参数内容
        } else {
            log.error("不是微信发来的请求！");
        }
    }

    private static final String token = "yixunToken"; //这个token值要和服务器配置一致

    public static boolean checkSignature(String signature, String timestamp, String nonce) {

        String[] arr = new String[]{token, timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        // sha1加密
        String temp = getSHA1String(content.toString());

        return temp.equals(signature); // 与微信传递过来的签名进行比较
    }

    private static String getSHA1String(String data) {
        // 使用commons codec生成sha1字符串
        return DigestUtils.shaHex(data);
    }


}
