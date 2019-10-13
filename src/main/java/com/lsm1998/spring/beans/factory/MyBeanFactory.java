/*
 * 作者：刘时明
 * 时间：2019/10/13-17:29
 * 作用：IOC顶层容器
 */
package com.lsm1998.spring.beans.factory;

public interface MyBeanFactory
{
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String name) throws Exception;

    Object getBean(Class<?> requiredType) throws Exception;

    boolean containsBean(String name);

    boolean isSingleton(String name) throws Exception;
}
