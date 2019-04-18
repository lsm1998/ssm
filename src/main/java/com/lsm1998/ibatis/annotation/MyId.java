package com.lsm1998.ibatis.annotation;

import com.lsm1998.ibatis.enums.AutoIncrement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者：刘时明
 * @时间：18-12-24-上午10:55
 * @说明：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyId
{
    AutoIncrement value() default AutoIncrement.TRUE;
}
