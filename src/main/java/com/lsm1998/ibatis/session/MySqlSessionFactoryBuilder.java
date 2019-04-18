package com.lsm1998.ibatis.session;

import com.lsm1998.ibatis.builder.MyAnnotationConfigBuilder;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：18-12-21-下午2:58
 * @说明：
 */
public class MySqlSessionFactoryBuilder
{
    /**
     * 根据Reader建造
     *
     * @param reader
     * @return
     */
    public MySqlSessionFactory build(Reader reader)
    {
        return null;
    }

    /**
     * 根据InputStream建造
     *
     * @param inputStream
     * @return
     */
    public MySqlSessionFactory build(InputStream inputStream)
    {
        return null;
    }

    /**
     * 根据Properties建造
     *
     * @param properties
     * @return
     */
    public MySqlSessionFactory build(Properties properties, boolean update)
    {
        MyAnnotationConfigBuilder parser = new MyAnnotationConfigBuilder(properties, update);
        return build(parser.parse());
    }

    /**
     * 根据路径建造
     *
     * @param path
     * @return
     */
    public MySqlSessionFactory build(String path, boolean update)
    {
        MyAnnotationConfigBuilder parser = new MyAnnotationConfigBuilder(path, update);
        return build(parser.parse());
    }

    /**
     * 根据配置类建造
     * @param config
     * @return
     */
    public MySqlSessionFactory build(MyConfiguration config)
    {
        return new MyDefaultSqlSessionFactory(config);
    }
}
