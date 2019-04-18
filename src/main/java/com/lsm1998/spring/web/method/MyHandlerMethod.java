package com.lsm1998.spring.web.method;

import java.lang.reflect.Method;

/**
 * @作者：刘时明
 * @时间：18-12-21-上午10:47
 * @说明：绑定组件与方法
 */
public class MyHandlerMethod
{
    protected final Method method;
    protected final Object bean;


    public MyHandlerMethod(Object bean, Method method)
    {
        this.bean = bean;
        this.method = method;
    }
}
