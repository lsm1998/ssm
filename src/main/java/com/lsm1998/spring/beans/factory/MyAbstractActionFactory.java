package com.lsm1998.spring.beans.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @作者：刘时明
 * @时间:2018/12/20-22:02
 * @说明：IOC容器初始化行为定义抽象类
 */
public abstract class MyAbstractActionFactory
{
    // 支持高并发的Map
    protected static Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 扫描并加载组件
     *
     * @param clazz
     */
    protected abstract void scanComponent(Class clazz);

    /**
     * 装配组件
     *
     * @param beanFactory
     */
    protected abstract void autowiredComponent(MyBeanFactory beanFactory);

    /**
     * 加载切面类型的组件
     */
    protected abstract void loadAspect();
}
