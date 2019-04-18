package com.lsm1998.ibatis.session;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @作者：刘时明
 * @时间：18-12-24-下午4:03
 * @说明：
 */
public class MyDefaultSqlSessionFactory implements MySqlSessionFactory
{
    private MyConfiguration configuration;
    private Connection connection;

    protected MyDefaultSqlSessionFactory(MyConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public MySqlSession openSession()
    {
        return openSession(getConnection());
    }

    @Override
    public MySqlSession openSession(boolean autoCommit)
    {
        return openSession(getConnection(), autoCommit);
    }

    private MySqlSession openSession(Connection connection, boolean autoCommit)
    {
        return new MySqlSession(connection, autoCommit);
    }

    @Override
    public MySqlSession openSession(Connection connection)
    {
        return new MySqlSession(connection);
    }

    @Override
    public MyConfiguration getConfiguration()
    {
        return null;
    }

    private Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(configuration.url, configuration.username, configuration.password);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
