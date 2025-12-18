package com.dealermanager.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenUtil {

    //jjwt加密秘钥
    private static  String jjwt_secret = "yixun20230508";

    //jjwt加密方式"HS256"
    private static  String jjwt_encryption  = "HS256";

    //token 过期时间, 单位: 秒
    private static  long jjwt_expiredtime = 24 * 60 * 60;

    private static final char[] HEX_TAB = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 创建JWT
     */
    public static String createJWT(Map<String, Object> claims, Long time) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        Date now = new Date(System.currentTimeMillis());

        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        //下面就是在为payload添加各种标准声明和私有声明了
        log.info(">>>>>>>>>>>TokenUtil createJWT claims."+claims.toString());
        log.info(">>>>>>>>>>>TokenUtil createJWT now."+now.toString());
        log.info(">>>>>>>>>>>TokenUtil createJWT signatureAlgorithm."+signatureAlgorithm.toString());
        log.info(">>>>>>>>>>>TokenUtil createJWT secretKey."+secretKey.toString());

        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(now)           //iat: jwt的签发时间
                .signWith(signatureAlgorithm, secretKey);//设置签名使用的签名算法和签名使用的秘钥

        log.info(">>>>>>>>>>>TokenUtil createJWT builder."+builder);

        return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static boolean verifyToken(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        Claims claims;
        try {
            claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)         //设置签名的秘钥
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }//设置需要解析的jwt

        if (null==claims){
            return  false;
        }else{
            return true;
        }

    }


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(jjwt_secret);
        SecretKey key = new SecretKeySpec(encodedKey, jjwt_encryption );
        return key;
    }


    /**
     * 根据令牌解析内容
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt){
        SecretKey key = generalKey();
        //SecretKey key = new SecretKeySpec( Base64.decodeBase64(jjwt_secret), "HS256");
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 生成token
     */
    public static String generateToken(String userName,String phone) {
        log.info("==>生成token入参id:{},userId:{},username:{}",userName,phone);
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("phone", phone);
        String token = createJWT(map, jjwt_expiredtime);
        log.info("==>token生成成功token:{}",token);
        return token;
    }


    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            log.info("加密异常" , e);
        }
        return re_md5;
    }


    /**
     * 密码加密
     * */
    public static String  pwdEncryption(String pwd)  {
        byte[] bytes;
        //处理MD5加密算法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");//PwdAlgorithmUtil
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(pwd.getBytes());
        bytes=md5.digest();
        return toHex32(bytes);
    }

    /**
     * 16进制字符串转换方法，处理为32位长度
     * @param buffer 字节数组
     * @return       16进制字符串
     */
    public static String toHex32(byte[] buffer) {
        if(buffer.length == 0) {
            return "";
        } else {
            boolean negative = false;
            if(buffer[0] < 0) {
                negative = true;
            }

            StringBuilder sb = new StringBuilder();

            int i;
            for(i = buffer.length * 2; i < 32; ++i) {
                sb.append((negative?'f':'0'));
            }

            for(i = 0; i < buffer.length; ++i) {
                sb.append(HEX_TAB[(buffer[i] & 240) >> 4]);
                sb.append(HEX_TAB[buffer[i] & 15]);
            }

            return sb.toString();
        }
    }

   public static void main(String args[]){
        String pwd = "20230513Admin";
        String encryPwd = pwdEncryption(pwd);
        System.out.println("-----------pwd------------" + encryPwd);
   }
}

