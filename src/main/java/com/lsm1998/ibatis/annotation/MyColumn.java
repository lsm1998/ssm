package com.lsm1998.ibatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者：刘时明
 * @时间：18-12-21-下午4:59
 * @说明：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyColumn
{
    String value() default "";

    String type() default "";

    boolean unique() default false;

    boolean index() default false;

    boolean nullable() default false;

    int length() default -1;
}
