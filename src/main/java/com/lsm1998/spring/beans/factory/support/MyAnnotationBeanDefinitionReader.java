/*
 * 作者：刘时明
 * 时间：2019/10/13-18:34
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;
import com.lsm1998.spring.core.MyResource;
import com.lsm1998.spring.core.io.MyResourceLoader;

import java.util.List;

public class MyAnnotationBeanDefinitionReader implements MyBeanDefinitionReader
{
    private String[] configLoadPath;

    public MyAnnotationBeanDefinitionReader(String... configLoadPath)
    {
        this.configLoadPath = configLoadPath;
    }

    @Override
    public MyBeanDefinitionRegistry getRegistry()
    {
        return null;
    }

    @Override
    public MyResourceLoader getResourceLoader()
    {
        return null;
    }

    @Override
    public ClassLoader getBeanClassLoader()
    {
        return null;
    }

    @Override
    public MyBeanNameGenerator getBeanNameGenerator()
    {
        return null;
    }

    @Override
    public int loadBeanDefinitions(MyResource resource) throws Exception
    {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(MyResource... resources) throws Exception
    {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String location) throws Exception
    {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws Exception
    {
        return 0;
    }

    @Override
    public List<MyBeanDefinition> loadBeanDefinitions()
    {
        return null;
    }
}
