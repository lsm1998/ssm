/*
 * 作者：刘时明
 * 时间：2019/10/13-18:06
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;
import com.lsm1998.spring.core.MyAliasRegistry;

public interface MyBeanDefinitionRegistry extends MyAliasRegistry
{
    void removeBeanDefinition(String beanName) throws Exception;

    MyBeanDefinition getBeanDefinition(String beanName) throws Exception;

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();

    boolean isBeanNameInUse(String beanName);
}
