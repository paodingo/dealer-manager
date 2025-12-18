package com.dealermanager.util;

import com.dealermanager.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author
 */
public class UserContextHolder {

    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    private static ThreadLocal<String> unionIdHolder = new ThreadLocal<>();

    private static ThreadLocal<String> openIdHolder = new ThreadLocal<>();

    private static ThreadLocal<Map<String,Object>> bizParameters = new ThreadLocal<>();

    public static Map<String, Object> getBizParameters() {
        return bizParameters.get();
    }

    public static void setBizParameters(Map<String, Object> parameters) {
        bizParameters.set(parameters);
    }

    public static User getUser() {
        return userHolder.get();
    }

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static Long getUserId() {
        return getUser().getUserId();
    }

    public static String getMobile() {
        return getUser().getMobile();
    }

    public static Long getCompanyID() {
        return getUser().getCompanyID();
    }

    public static String getIdentity() {
        return getUser().getIdentity();
    }


    public static String getUnionId() {
        return unionIdHolder.get();
    }

    public static void setUnionId(String id) {
        unionIdHolder.set(id);
    }

    public static String getOpenId() {
        return openIdHolder.get();
    }

    public static void setOpenId(String id) {
        openIdHolder.set(id);
    }



    public static <T> T getBizParam(String key) {
        //noinspection unchecked
        return Optional.ofNullable(getBizParameters()).map(x -> (T)x.get(key)).orElse(null);
    }

    public static void putBizParam(String key, Object value) {
        Map<String, Object> local = getBizParameters();
        if (local == null) {
            local = new HashMap<>();
            setBizParameters(local);
        }

        local.put(key, value);
    }

    /**
     * 判断是否为正式注册已审核用户
     *
     * @return true/false
     */
    public static boolean isFormalUser() {
        User user = getUser();
        return user != null && user.isFormalUser();
    }

    public static void clear() {
        userHolder.remove();
        openIdHolder.remove();
        unionIdHolder.remove();
        bizParameters.remove();
    }

}
