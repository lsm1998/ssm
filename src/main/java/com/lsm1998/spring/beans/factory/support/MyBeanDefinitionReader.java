/*
 * 作者：刘时明
 * 时间：2019/10/13-18:03
 * 作用：
 */
package com.lsm1998.spring.beans.factory.support;

import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;
import com.lsm1998.spring.core.MyResource;
import com.lsm1998.spring.core.io.MyResourceLoader;

import java.util.List;

public interface MyBeanDefinitionReader
{
    MyBeanDefinitionRegistry getRegistry();

    MyResourceLoader getResourceLoader();

    ClassLoader getBeanClassLoader();

    MyBeanNameGenerator getBeanNameGenerator();

    int loadBeanDefinitions(MyResource resource) throws Exception;

    int loadBeanDefinitions(MyResource... resources) throws Exception;

    int loadBeanDefinitions(String location) throws Exception;

    int loadBeanDefinitions(String... locations) throws Exception;

    List<MyBeanDefinition> loadBeanDefinitions();
}
