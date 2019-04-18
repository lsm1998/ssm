package com.lsm1998.spring.web;

import com.lsm1998.spring.context.MyAnnotationConfigApplicationContext;
import com.lsm1998.spring.web.method.MyHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间:2018/12/20-17:52
 * @说明：实现拦截、分发的Servlet
 */
@WebServlet(urlPatterns = "*.do", loadOnStartup = 1)
public class MyDispatchServlet extends HttpServlet
{
    public MyDispatchServlet()
    {
        System.out.println("MySpring开始加载");
    }

    @Override
    public void init(ServletConfig config)
    {
        System.out.println("init初始化配置");
        // 1.获取Spring配置类路径
        Properties properties=doLoadConfig();
        String rootClass = properties.getProperty("webRoot");

        // 2.根据Spring配置类初始化IOC容器
        MyAnnotationConfigApplicationContext context = new MyAnnotationConfigApplicationContext(rootClass);
        System.out.println(context.getIoc());
        System.out.println("IOC容器初始化完毕");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        MyHandlerMapping handlerMapping=new MyHandlerMapping();
        handlerMapping.request(req, resp);
    }

    private Properties doLoadConfig()
    {
        Properties properties = new Properties();
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("spring.properties"))
        {
            properties.load(is);
            return properties;
        } catch (Exception e)
        {
            System.out.println("spring.properties配置文件出现错误");
        }
        return null;
    }
}
