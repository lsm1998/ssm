/*
 * 作者：刘时明
 * 时间：2019/10/13-18:05
 * 作用：bean实例别名
 */
package com.lsm1998.spring.core;

public interface MyAliasRegistry
{
    void registerAlias(String name, String alias);

    void removeAlias(String alias);

    boolean isAlias(String name);

    String[] getAliases(String name);
}
