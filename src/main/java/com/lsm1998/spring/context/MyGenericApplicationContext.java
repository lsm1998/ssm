package com.lsm1998.spring.context;

import com.lsm1998.spring.aop.annotation.MyEnableAspectJAutoProxy;
import com.lsm1998.spring.beans.annotation.MyComponentScan;
import com.lsm1998.spring.beans.annotation.MyConfiguration;

import java.util.Collection;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间:2018/12/20-20:58
 * @说明：ApplicationContext实现类
 */
public class MyGenericApplicationContext extends MyActionApplicationContext implements MyApplicationContext
{
    protected MyGenericApplicationContext()
    {
    }

    /**
     * 加载容器，注册主键
     *
     * @param clazz
     */
    protected void loadApplicationContext(Class<?> clazz)
    {
        // 检验配置类
        if (!validateConfig(clazz))
        {
            System.err.println(clazz.getName() + "不是一个Spring配置类");
            return;
        }

        // 注册组件
        this.scanComponent(clazz);

        // 注入组件
        super.autowiredComponent(this);

        if(clazz.isAnnotationPresent(MyEnableAspectJAutoProxy.class))
        {
            // 加载切面组件
            super.loadAspect();

            // 注入代理组件
            super.autowiredProxy();
        }
    }

    /**
     * 检验Spring配置类
     *
     * @param clazz
     * @return
     */
    private boolean validateConfig(Class<?> clazz)
    {
        return clazz.isAnnotationPresent(MyConfiguration.class) && clazz.isAnnotationPresent(MyComponentScan.class);
    }

    @Override
    public Object getBean(String name)
    {
        return beanMap.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType)
    {
        return (T) beanMap.get(name);
    }

    @Override
    public Object getBean(String name, Object... args)
    {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType)
    {
        return getBeanByType(requiredType, false);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args)
    {
        return null;
    }

    @Override
    public boolean containsBean(String name)
    {
        return false;
    }

    @Override
    public boolean isSingleton(String name)
    {
        return false;
    }

    @Override
    public boolean isPrototype(String name)
    {
        return false;
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch)
    {
        return false;
    }

    @Override
    public Class<?> getType(String name)
    {
        return null;
    }

    @Override
    public String[] getAliases(String name)
    {
        return new String[0];
    }

    @Override
    public boolean register(String name, Object bean)
    {
        return false;
    }

    @Override
    public boolean remove(String naem)
    {
        return false;
    }

    @Override
    public boolean remove(Class<?> requiredType)
    {
        return false;
    }

    @Override
    public Map<String, Object> getIoc()
    {
        return beanMap;
    }

    /**
     * 根据类型返回Bean
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    private <T> T getBeanByType(Class<T> requiredType, boolean isSpread)
    {
        Collection<Object> values = beanMap.values();
        for (Object o : values)
        {
            if (o.getClass() == requiredType)
            {
                return (T) o;
            }
        }
        if (isSpread)
        {
            return null;
        }
        return getBeanBySubclass(requiredType);
    }

    /**
     * 根据类型返回子类bean
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    private <T> T getBeanBySubclass(Class<T> requiredType)
    {
        for (Object obj : beanMap.values())
        {
            if (obj.getClass().getSuperclass() == requiredType)
            {
                return (T) obj;
            }
            for (Class clazz : obj.getClass().getInterfaces())
            {
                if (clazz == requiredType)
                {
                    return (T) obj;
                }
            }
        }
        return null;
    }
}
