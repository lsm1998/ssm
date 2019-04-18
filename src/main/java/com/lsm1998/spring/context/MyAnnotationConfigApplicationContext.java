package com.lsm1998.spring.context;

/**
 * @作者：刘时明
 * @时间:2018/12/20-20:36
 * @说明：基于注解的ApplicationContext
 */
public class MyAnnotationConfigApplicationContext extends MyGenericApplicationContext
{
    public MyAnnotationConfigApplicationContext(String rootClass)
    {
        System.out.println("你使用的是web项目，请确保使用了构建工具");
        try
        {
            Class clazz = Class.forName(rootClass);
            loadIOC(clazz);
        } catch (Exception e)
        {
            System.err.println("webRoot配置类找不到：" + rootClass);
        }
    }

    public MyAnnotationConfigApplicationContext(Class<?> rootClazz)
    {
        System.out.println("你使用的是javaSE项目，请确保使用了构建工具");
        loadIOC(rootClazz);
    }

    private void loadIOC(Class<?> rootClazz)
    {
        MyGenericApplicationContext context = new MyGenericApplicationContext();
        context.loadApplicationContext(rootClazz);
    }
}
