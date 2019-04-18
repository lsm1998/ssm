package com.lsm1998.spring.transaction;

/**
 * @作者：刘时明
 * @时间:2018/12/23-21:44
 * @说明：事务管理器接口
 */
public interface MyPlatformTransactionManager
{
    void commit(MyTransactionStatus status);

    MyTransactionStatus getTransaction(MyTransactionDefinition definition);
}
