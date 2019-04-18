package com.lsm1998.spring.web.annotation;

import com.lsm1998.spring.web.enums.MyRequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者：刘时明
 * @时间:2018/12/20-18:06
 * @说明：请求映射标识
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestMapping
{
    MyRequestMethod[] method() default {};

    String value() default "";
}
