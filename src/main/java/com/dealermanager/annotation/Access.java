package com.dealermanager.annotation;

import com.dealermanager.constants.Constants;

import java.lang.annotation.*;

/**
 * 接口访问控制
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access {

    String identity() default Constants.USER_IDENTIFY_ANONYMOUS;

}
