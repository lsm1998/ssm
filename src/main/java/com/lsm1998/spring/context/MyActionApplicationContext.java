package com.lsm1998.spring.context;

import com.lsm1998.spring.aop.annotation.*;
import com.lsm1998.spring.aop.aspectj.MyAspectInstanceFactory;
import com.lsm1998.spring.beans.annotation.*;
import com.lsm1998.spring.beans.factory.MyAbstractActionFactory;
import com.lsm1998.spring.beans.factory.MyBeanFactory;
import com.lsm1998.spring.web.method.MyHandlerMapping;
import com.lsm1998.spring.web.annotation.MyController;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间:2018/12/20-22:06
 * @说明：实现IOC容器初始化行为
 */
public class MyActionApplicationContext extends MyAbstractActionFactory
{
    // 分隔符号
    public static final String SEPARATE;
    // 项目路径
    public static final String PROJECT_PATH;
    // 获取IOC容器的功能
    private MyBeanFactory beanFactory;
    // 代理对象容器
    private Map<String,Object> porxyMap=new HashMap<>();

    static
    {
        if (System.getProperty("os.name").startsWith("Windows"))
        {
            SEPARATE = "\\";
        } else
        {
            SEPARATE = "/";
        }
        PROJECT_PATH = System.getProperty("user.dir");
    }

    /**
     * 扫描并注册组件
     *
     * @param clazz
     */
    @Override
    protected void scanComponent(Class clazz)
    {
        MyComponentScan componentScan = (MyComponentScan) clazz.getDeclaredAnnotation(MyComponentScan.class);

        String classpath = PROJECT_PATH + SEPARATE + "src" + SEPARATE + "main" + SEPARATE + "java" + SEPARATE + componentScan.value().replace(".", SEPARATE);
        File file = new File(classpath);
        if (!file.exists())
        {
            String path = this.getClass().getClassLoader().getResource(componentScan.value().replace(".", SEPARATE)).getPath();
            File temp = new File(path);
            loadComponent(temp, true);
        } else
        {
            loadComponent(file, false);
        }
    }

    /**
     * 装配组件
     */
    @Override
    protected void autowiredComponent(MyBeanFactory beanFactory)
    {
        this.beanFactory = beanFactory;
        for (Object bean : beanMap.values())
        {
            autowiredComponent(bean);
        }
    }

    /**
     * 根据bean对象注入组件
     *
     * @param bean
     */
    private void autowiredComponent(Object bean)
    {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(MyAutowired.class))
            {
                f.setAccessible(true);
                try
                {
                    Object obj = beanFactory.getBean(f.getType());
                    if (obj == null)
                    {
                        System.out.println("组件类型不存在：" + f.getType());
                    } else
                    {
                        Object temp=beanFactory.getBean(f.getType());
                        f.set(bean, temp);
                        System.out.println("依赖注入一个组件：" + beanFactory.getBean(f.getType()));
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadComponent(File file, Boolean isWeb)
    {
        File[] files = file.listFiles((dir, name) -> dir.isDirectory() || name.endsWith(".java"));
        if (files == null)
        {
            return;
        }
        for (File f : files)
        {
            if (f.isDirectory())
            {
                loadComponent(f, isWeb);
            } else
            {
                loadComponent(f.getAbsoluteFile().toString(), isWeb);
            }
        }
    }

    private void loadComponent(String classpath, boolean isWeb)
    {
        int lastIndex = classpath.lastIndexOf(".");
        classpath = classpath.substring(0, lastIndex);
        if (isWeb)
        {
            int firstIndex = classpath.indexOf("WEB-INF" + SEPARATE + "classes");
            classpath = classpath.substring(firstIndex + 16).replace(SEPARATE, ".");
        } else
        {
            int firstIndex = classpath.indexOf("src" + SEPARATE + "main" + SEPARATE + "java");
            classpath = classpath.substring(firstIndex + 14).replace(SEPARATE, ".");
        }
        try
        {
            Class clazz = Class.forName(classpath);
            if (clazz.isAnnotationPresent(MyComponent.class) || clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class) || clazz.isAnnotationPresent(MyConfiguration.class))
            {
                System.out.println("获取一个bean组件，开始加载：" + clazz.getName());

                Object bean = loadComponent(clazz);

                if (clazz.isAnnotationPresent(MyController.class))
                {
                    MyHandlerMapping.registerMapping(clazz, bean);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Object loadComponent(Class clazz)
    {
        try
        {
            Object bean = clazz.getConstructor().newInstance();
            this.beanMap.put(clazz.getName(), bean);
            // 注入Value值
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields)
            {
                if (f.isAnnotationPresent(MyValue.class))
                {
                    MyValue myValue = f.getAnnotation(MyValue.class);
                    f.setAccessible(true);
                    String value = myValue.value();
                    if (clazz.isAnnotationPresent(MyPropertySource.class))
                    {
                        if (value.startsWith("${") && value.endsWith("}"))
                        {
                            MyPropertySource propertySource = (MyPropertySource) clazz.getAnnotation(MyPropertySource.class);
                            Properties properties = new Properties();
                            properties.load(this.getClass().getClassLoader().getResourceAsStream(propertySource.value()));
                            value = value.substring(2, value.length() - 1);
                            f.set(bean, properties.getProperty(value));
                            continue;
                        }
                    }
                    if (f.getType() == String.class || f.getType() == char.class || f.getType() == Character.class)
                    {
                        f.set(bean, value);
                    } else if (f.getType() == Integer.class || f.getType() == int.class)
                    {
                        f.set(bean, Integer.parseInt(value));
                    } else if (f.getType() == Double.class || f.getType() == double.class)
                    {
                        f.set(bean, Double.parseDouble(value));
                    } else if (f.getType() == Float.class || f.getType() == float.class)
                    {
                        f.set(bean, Float.parseFloat(value));
                    } else if (f.getType() == Long.class || f.getType() == long.class)
                    {
                        f.set(bean, Long.parseLong(value));
                    } else if (f.getType() == Short.class || f.getType() == short.class)
                    {
                        f.set(bean, Short.parseShort(value));
                    } else if (f.getType() == Byte.class || f.getType() == byte.class)
                    {
                        f.set(bean, Byte.parseByte(value));
                    } else if (f.getType() == Boolean.class || f.getType() == boolean.class)
                    {
                        f.set(bean, Boolean.parseBoolean(value));
                    }
                }
            }

            // 初始化方法级Bean
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods)
            {
                if (m.isAnnotationPresent(MyBean.class))
                {
                    Object result = m.invoke(bean);
                    beanMap.put(result.getClass().getName(), result);
                    System.out.println("注入了一个方法级Bean=" + result.toString());
                }
            }
            return bean;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void loadAspect()
    {
        System.out.println("切面组件开始加载");
        for (String key : beanMap.keySet())
        {
            Object bean = beanMap.get(key);
            if (bean.getClass().isAnnotationPresent(MyAspect.class))
            {
                // 创建代理类
                GenerationProxy(key, bean);
            }
        }
    }

    private void GenerationProxy(String key, Object bean)
    {
        Object proxy = MyAspectInstanceFactory.getAspectInstance(bean);
        Field[] fields=bean.getClass().getDeclaredFields();
        try
        {
            // 对象的属性赋给代理对象
            for (Field f:fields)
            {
                f.setAccessible(true);
                Field temp=bean.getClass().getDeclaredField(f.getName());
                temp.setAccessible(true);
                f.set(proxy,temp.get(bean));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        porxyMap.put(key,proxy);
        System.out.println("一个组件的代理对象已经生成，proxy="+proxy.getClass());
    }

    protected void autowiredProxy()
    {
        for (Object o:beanMap.values())
        {
            Field[] fields=o.getClass().getDeclaredFields();
            for (Field f:fields)
            {
                if(f.isAnnotationPresent(MyAutowired.class))
                {
                    autowiredProxy(f,o);
                }
            }
        }
    }

    private void autowiredProxy(Field f,Object bean)
    {
        Class<?> clazz=f.getType();
        System.out.println(this.porxyMap);

        String name=null;
        for (String key:beanMap.keySet())
        {
            Object o=beanMap.get(key);
            if(o.getClass()==clazz||o.getClass().getSuperclass()==clazz)
            {
                name=key;
            }else
            {
                for (Class c:o.getClass().getInterfaces())
                {
                    if(c==clazz)
                    {
                        name=key;
                        break;
                    }
                }
            }
        }
        if(name!=null)
        {
            try
            {
                if(this.porxyMap.containsKey(name))
                {
                    f.setAccessible(true);
                    f.set(bean,this.porxyMap.get(name));
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
