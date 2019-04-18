package com.lsm1998.spring.aop.annotation;

import com.lsm1998.spring.aop.enums.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者：刘时明
 * @时间:2018/12/23-20:39
 * @说明：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAfterThrowing
{
    String value() default "";

    Type type() default Type.PRINT;
}
