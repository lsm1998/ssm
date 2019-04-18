package com.lsm1998.spring.aop.aspectj;

import net.sf.cglib.proxy.Enhancer;

/**
 * @作者：刘时明
 * @时间:2018/12/22-11:43
 * @说明：提供AspectJ方面的实例,与Spring的bean工厂分离
 */
public abstract class MyAspectInstanceFactory
{
    public static <T> T getAspectInstance(T instance)
    {
        CglibInvocationHandler handler = new CglibInvocationHandler();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }

    public ClassLoader getAspectClassLoader()
    {
        return this.getClass().getClassLoader();
    }
}
