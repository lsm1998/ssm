/*
 * 作者：刘时明
 * 时间：2019/10/13-18:22
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

public abstract class MyAbstractBeanDefinitionReader implements MyBeanDefinitionReader
{
    private final MyBeanDefinitionRegistry registry;

    protected MyAbstractBeanDefinitionReader(MyBeanDefinitionRegistry registry)
    {
        this.registry = registry;
    }
}
