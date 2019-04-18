package com.lsm1998.ibatis.session;

import java.sql.Connection;

/**
 * @作者：刘时明
 * @时间：18-12-21-下午2:59
 * @说明：
 */
public interface MySqlSessionFactory
{
    MySqlSession openSession();

    MySqlSession openSession(boolean autoCommit);

    MySqlSession openSession(Connection connection);

    MyConfiguration getConfiguration();
}
