/*
 * 作者：刘时明
 * 时间：2019/10/13-17:46
 * 作用：
 */
package com.lsm1998.spring.core.io;

import com.lsm1998.spring.core.MyResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDefaultResourceLoader implements MyResourceLoader
{
    private ClassLoader classLoader;

    private final Map<Class<?>, Map<MyResource, ?>> resourceCaches = new ConcurrentHashMap<>(4);

    @Override
    public MyResource getResource(String location)
    {
        return null;
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return null;
    }
}
