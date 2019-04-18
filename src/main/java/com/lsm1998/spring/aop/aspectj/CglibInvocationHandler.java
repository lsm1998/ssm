package com.lsm1998.spring.aop.aspectj;

import com.lsm1998.spring.aop.annotation.MyAfter;
import com.lsm1998.spring.aop.annotation.MyAfterThrowing;
import com.lsm1998.spring.aop.annotation.MyBefore;
import com.lsm1998.spring.aop.enums.Type;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @作者：刘时明
 * @时间:2018/12/23-23:14
 * @说明：使用cglib API生成动态代理类
 */
public class CglibInvocationHandler implements MethodInterceptor
{
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
    {
        before(method,args);
        try
        {
            Object result = proxy.invokeSuper(obj, args);
            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            afterThrowing(method,args);
        }finally
        {
            after(method,args);
        }
        return null;
    }

    /**
     * 异常处理
     * @param method
     * @param args
     */
    private void afterThrowing(Method method, Object[] args)
    {
        if (method.isAnnotationPresent(MyAfterThrowing.class))
        {
            MyAfterThrowing afterThrowing = method.getAnnotation(MyAfterThrowing.class);
            StringBuffer sb=new StringBuffer(afterThrowing.value());
            if (sb.toString().startsWith("args(") && sb.toString().endsWith(")"))
            {
                sb.delete(0,5);
                sb.delete(sb.length()-1,sb.length());
                int index;
                while ((index=sb.indexOf("#{"))!=-1)
                {
                    int lastIndex=sb.indexOf("}");
                    Integer num=Integer.parseInt(sb.substring(index+2,lastIndex));
                    sb.delete(index,lastIndex+1);
                    sb.insert(index,args[num].toString());
                }
            } else
            {
                sb.append("事务结束");
            }
            if (afterThrowing.type() == Type.PRINT)
            {
                System.out.println("打印：" + sb.toString());
            } else if (afterThrowing.type() == Type.LOGS)
            {
                System.out.println("日志：" + sb.toString());
            }
        }
    }

    /**
     * 后置处理
     */
    private void after(Method method,Object[] args)
    {
        if (method.isAnnotationPresent(MyAfter.class))
        {
            MyAfter after = method.getAnnotation(MyAfter.class);
            StringBuffer sb=new StringBuffer(after.value());
            if (sb.toString().startsWith("args(") && sb.toString().endsWith(")"))
            {
                sb.delete(0,5);
                sb.delete(sb.length()-1,sb.length());
                int index;
                while ((index=sb.indexOf("#{"))!=-1)
                {
                    int lastIndex=sb.indexOf("}");
                    Integer num=Integer.parseInt(sb.substring(index+2,lastIndex));
                    sb.delete(index,lastIndex+1);
                    sb.insert(index,args[num].toString());
                }
            } else
            {
                sb.append("事务结束");
            }
            if (after.type() == Type.PRINT)
            {
                System.out.println("打印：" + sb.toString());
            } else if (after.type() == Type.LOGS)
            {
                System.out.println("日志：" + sb.toString());
            }
        }
    }

    /**
     * 前置处理
     */
    private void before(Method method,Object[] args)
    {
        if (method.isAnnotationPresent(MyBefore.class))
        {
            MyBefore before = method.getAnnotation(MyBefore.class);
            StringBuffer sb=new StringBuffer(before.value());
            if (sb.toString().startsWith("args(") && sb.toString().endsWith(")"))
            {
                sb.delete(0,5);
                sb.delete(sb.length()-1,sb.length());
                int index;
                while ((index=sb.indexOf("#{"))!=-1)
                {
                    int lastIndex=sb.indexOf("}");
                    Integer num=Integer.parseInt(sb.substring(index+2,lastIndex));
                    sb.delete(index,lastIndex+1);
                    sb.insert(index,args[num].toString());
                }
            } else
            {
                sb.append("事务结束");
            }
            if (before.type() == Type.PRINT)
            {
                System.out.println("打印：" + sb.toString());
            } else if (before.type() == Type.LOGS)
            {
                System.out.println("日志：" + sb.toString());
            }
        }
    }
}
