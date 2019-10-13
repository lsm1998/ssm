/*
 * 作者：刘时明
 * 时间：2019/10/13-17:45
 * 作用：
 */
package com.lsm1998.spring.core.io;

import com.lsm1998.spring.core.MyResource;

public interface MyResourceLoader
{
    String CLASSPATH_URL_PREFIX = "classpath:";

    MyResource getResource(String location);

    ClassLoader getClassLoader();
}
