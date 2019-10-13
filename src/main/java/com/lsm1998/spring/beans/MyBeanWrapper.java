/*
 * 作者：刘时明
 * 时间：2019/10/13-17:38
 * 作用：容器保存的实例，封装后的bean
 */
package com.lsm1998.spring.beans;

public class MyBeanWrapper
{
    private Object wrapperInstance;
    private Class<?> wrapperClass;

    public MyBeanWrapper(Object wrapperInstance)
    {
        this.wrapperInstance = wrapperInstance;
    }

    public Object getWrapperInstance()
    {
        return wrapperInstance;
    }

    public Class<?> getWrapperClass()
    {
        return wrapperClass;
    }
}
