package com.lsm1998.ibatis.reflect;

import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:47
 * @说明：
 */
public class MyMapperProxyFactory
{
    public static <T> T getProxy(Class clazz, Connection connection)
    {
        MyInvocationHandler handler = new MyInvocationHandler(clazz, connection);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
    }
}
