package com.lsm1998.spring.beans.factory;

/**
 * @作者：刘时明
 * @时间:2018/12/20-0:17
 * @说明：IOC容器顶层接口
 */
public interface MyBeanFactory
{
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String name);

    <T> T getBean(String name, Class<T> requiredType);

    Object getBean(String name, Object... args);

    <T> T getBean(Class<T> requiredType);

    <T> T getBean(Class<T> requiredType, Object... args);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    boolean isTypeMatch(String name, Class<?> typeToMatch);

    Class<?> getType(String name);

    String[] getAliases(String name);
}
