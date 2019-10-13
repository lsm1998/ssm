/*
 * 作者：刘时明
 * 时间：2019/10/13-17:52
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;
import com.lsm1998.spring.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext
{
    protected final Map<String, MyBeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>();
}
