/*
 * 作者：刘时明
 * 时间：2019/10/13-17:32
 * 作用：保存Bean配置信息
 */
package com.lsm1998.spring.beans.factory.config;

public class MyBeanDefinition
{
    private String beanClassName;

    private boolean isLazy;

    private String factoryBeanName;

    public String getBeanClassName()
    {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName)
    {
        this.beanClassName = beanClassName;
    }

    public boolean isLazy()
    {
        return isLazy;
    }

    public void setLazy(boolean lazy)
    {
        isLazy = lazy;
    }

    public String getFactoryBeanName()
    {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName)
    {
        this.factoryBeanName = factoryBeanName;
    }
}
