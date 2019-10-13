/*
 * 作者：刘时明
 * 时间：2019/10/13-18:09
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;

public interface MyBeanNameGenerator
{
    String generateBeanName(MyBeanDefinition definition, MyBeanDefinitionRegistry registry);
}
