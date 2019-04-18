package com.lsm1998.spring.transaction;

import java.sql.Connection;

/**
 * @作者：刘时明
 * @时间:2018/12/23-21:45
 * @说明：事务基本定义接口
 */
public interface MyTransactionDefinition
{
    /**
     * 默认事务传播行为，如果当前没有事务则新建，有则沿用
     */
    int PROPAGATION_REQUIRED = 0;

    /**
     * 支持当前事务，如果当前没有事务，就以非事务方式执行
     */
    int PROPAGATION_SUPPORTS = 1;

    /**
     * 使用当前的事务，如果当前没有事务，就抛出异常
     */
    int PROPAGATION_MANDATORY = 2;

    /**
     * 新建事务，如果当前存在事务，把当前事务挂起
     */
    int PROPAGATION_REQUIRES_NEW = 3;

    /**
     * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
     */
    int PROPAGATION_NOT_SUPPORTED = 4;

    /**
     * 以非事务方式执行，如果当前存在事务，则抛出异常
     */
    int PROPAGATION_NEVER = 5;

    /**
     * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作
     */
    int PROPAGATION_NESTED = 6;

    /**
     * 默认的事务隔离级别
     */
    int ISOLATION_DEFAULT = -1;

    /***
     * 事务最低的隔离级别，它充许令外一个事务可以看到这个事务未提交的数据
     */
    int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

    /**
     * 保证一个事务修改的数据提交后才能被另外一个事务读取
     */
    int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

    /**
     * 可以防止脏读，不可重复读
     */
    int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * 顺序执行，防止脏读、不可重复读、幻读
     */
    int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
}
