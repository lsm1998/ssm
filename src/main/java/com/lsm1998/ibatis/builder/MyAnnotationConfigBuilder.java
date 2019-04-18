package com.lsm1998.ibatis.builder;

import com.lsm1998.ibatis.auto.AutoTables;
import com.lsm1998.ibatis.session.MyConfiguration;
import com.lsm1998.ibatis.util.MySQLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：18-12-24-下午3:46
 * @说明：
 */
public class MyAnnotationConfigBuilder extends MyBaseBuilder
{
    private Properties properties;
    private boolean update;
    protected MyConfiguration configuration;

    public static final String SEPARATE;

    static
    {

        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows"))
        {
            SEPARATE = "\\";
        } else
        {
            SEPARATE = "/";
        }
    }

    public MyAnnotationConfigBuilder(Properties properties, boolean update)
    {
        this.properties = properties;
        this.update = update;
    }

    public MyAnnotationConfigBuilder(String path, boolean update)
    {
        this.properties = new Properties();
        try
        {
            properties.load(new FileInputStream("src/main/resources/" + path));
        } catch (Exception e)
        {
            try
            {
                InputStream is=MyAnnotationConfigBuilder.class.getClassLoader().getResourceAsStream(path);
                properties.load(is);
            } catch (Exception e1)
            {
                System.err.println("配置文件找不到");
                e1.printStackTrace();
            }
        }
        this.update = update;
    }


    public MyConfiguration parse()
    {
        configuration = new MyConfiguration(properties);
        configuration.setUpdate(update);
        MySQLUtil.setConfiguration(configuration);
        // 3.自动建表
        System.out.println(update ? "开始自动建表" : "自动建表未开启");
        if (update)
        {
            new AutoTables();
        }
        return configuration;
    }
}
