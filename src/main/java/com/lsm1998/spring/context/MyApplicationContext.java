/*
 * 作者：刘时明
 * 时间：2019/10/13-17:42
 * 作用：
 */
package com.lsm1998.spring.context;

import com.lsm1998.spring.beans.MyBeanWrapper;
import com.lsm1998.spring.beans.factory.MyBeanFactory;
import com.lsm1998.spring.beans.factory.config.MyBeanDefinition;
import com.lsm1998.spring.beans.factory.support.MyAnnotationBeanDefinitionReader;
import com.lsm1998.spring.beans.factory.support.MyBeanDefinitionReader;
import com.lsm1998.spring.beans.factory.support.MyDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory
{
    private String[] configLoadPath;
    private MyBeanDefinitionReader reader;

    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();
    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public MyApplicationContext(String... configLoadPath)
    {
        this.configLoadPath = configLoadPath;
        try
        {
            refresh();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception
    {
        // 处理配置文件
        reader = new MyAnnotationBeanDefinitionReader(configLoadPath);

        // 加载配置文件，封装为BeanDefinition
        List<MyBeanDefinition> beanDefinitionList=reader.loadBeanDefinitions();
    }

    @Override
    public Object getBean(String name) throws Exception
    {
        return null;
    }

    @Override
    public Object getBean(Class<?> requiredType) throws Exception
    {
        return null;
    }

    @Override
    public boolean containsBean(String name)
    {
        return false;
    }

    @Override
    public boolean isSingleton(String name) throws Exception
    {
        return false;
    }
}
