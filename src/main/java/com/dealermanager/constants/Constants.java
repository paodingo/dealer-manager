package com.dealermanager.constants;

/**
 * @Description：系统常量
 * @author：Rex
 * @date: 20200317
 */
public final class Constants {
    public static final String GlobalCharset = "UTF-8";

    public static final String exportCharset = "GBK"; //导出文件编码类型

    public static final String ENDSYMBOL = "\r\n";//导出文件结尾符

    public static final int INVALID_STATUS = 0;

    public static final int VALID_STATUS = 1;

    /**
     * 新注册
     */
    public static final int REGISTER_STATE_NEW = 0;

    /**
     * 通过
     */
    public static final int REGISTER_STATE_ACCEPT = 1;

    /**
     * 拒绝
     */
    public static final int REGISTER_STATE_REJECT = 2;

    /**
     * 匿名用户（未获取到session）
     */
    public static final String USER_IDENTIFY_ANONYMOUS = "anonymous";

    /**
     * 访客（未登陆系统用户更）
     */
    public static final String USER_IDENTIFY_GUEST = "guest";

    /**
     * 审核中的用户
     */
    public static final String USER_IDENTIFY_SEMI_FORMAL = "semiformal";

    /**
     * 正式用户（绑定过手机好，且有角色）
     */
    public static final String USER_IDENTIFY_FORMAL = "editor";

    /**
     * 正式用户（绑定过手机好，useerType = 2）
     */
    public static final String USER_IDENTIFY_ADMIN = "admin";

    /**
     * 正式用户（绑定过手机好，且有角色）
     */
    public static final int  USER_TYPE_ADMIN = 0;

}