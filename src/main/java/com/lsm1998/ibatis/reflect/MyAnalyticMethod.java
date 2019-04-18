package com.lsm1998.ibatis.reflect;

import com.lsm1998.ibatis.annotation.*;
import com.lsm1998.ibatis.util.MySQLUtil;
import com.lsm1998.ibatis.util.TypeUtil;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * @作者：刘时明
 * @时间:2018/12/27-15:22
 * @说明：
 */
public class MyAnalyticMethod
{
    protected static Object invoke(Method method, Object[] args, Connection connection)
    {
        Object restlt = null;
        StringBuilder sql = new StringBuilder();
        if (method.isAnnotationPresent(MySelect.class))
        {
            restlt = exeSelect(method, args, sql, connection);
        } else if (method.isAnnotationPresent(MyInsert.class) || method.isAnnotationPresent(MyUpdate.class) || method.isAnnotationPresent(MyDelete.class))
        {
            restlt = exeUpdate(method, args, sql, connection);
        }

        Class<?> resultType = method.getReturnType();
        if (TypeUtil.isComposite(resultType))
        {
            return restlt;
        } else if (TypeUtil.isString(resultType))
        {
            return restlt.toString();
        }
        if (resultType == int.class || resultType == Integer.class)
        {
            return Integer.parseInt(restlt.toString());
        } else if (resultType == long.class || resultType == Long.class)
        {
            return Long.parseLong(restlt.toString());
        } else if (resultType == byte.class || resultType == Byte.class)
        {
            return Byte.parseByte(restlt.toString());
        } else if (resultType == short.class || resultType == Short.class)
        {
            return Byte.parseByte(restlt.toString());
        }
        return null;
    }

    private static Object exeUpdate(Method method, Object[] args, StringBuilder sql, Connection connection)
    {
        String value;
        if (method.isAnnotationPresent(MyInsert.class))
        {
            MyInsert myInsert = method.getAnnotation(MyInsert.class);
            value = myInsert.value();
        } else if (method.isAnnotationPresent(MyDelete.class))
        {
            MyDelete myInsert = method.getAnnotation(MyDelete.class);
            value = myInsert.value();
        } else
        {
            MyUpdate myInsert = method.getAnnotation(MyUpdate.class);
            value = myInsert.value();
        }
        sql.append(value);
        initParameter(method.getParameters(), sql, args);
        System.out.println("执行SQL语句：" + sql);
        PreparedStatement ps = null;
        try
        {
            ps = connection.prepareStatement(sql.toString());
            return ps.executeUpdate();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, null);
        }
        return null;
    }

    private static Object exeSelect(Method method, Object[] args, StringBuilder sql, Connection connection)
    {
        MySelect mySelect = method.getAnnotation(MySelect.class);
        sql.append(mySelect.value());
        Parameter[] parameters = method.getParameters();
        initParameter(parameters, sql, args);
        System.out.println("执行SQL语句：" + sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            Type type = method.getGenericReturnType();
            Class clazz = method.getReturnType();
            if (clazz.isInterface())
            {
                if (type instanceof ParameterizedType)
                {
                    Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                    Type t = types[0];
                    return MySQLUtil.getListByResult(rs, Class.forName(t.getTypeName()));
                }
            } else
            {
                List list=MySQLUtil.getListByResult(rs, clazz);
                if (list.size() == 0)
                {
                    return null;
                } else if(list.size()==1)
                {
                    return list.get(0);
                }else
                {
                    return list;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, rs);
        }
        return null;
    }

    private static void initParameter(Parameter[] parameters, StringBuilder sql, Object[] args)
    {
        if (args == null)
        {
            return;
        }
        for (int i = 0; i < args.length; i++)
        {
            if (parameters[i].isAnnotationPresent(MyParam.class))
            {
                MyParam myParam = parameters[i].getAnnotation(MyParam.class);
                String value = myParam.value();
                String temp = null;
                if (TypeUtil.isComposite(parameters[i].getType()))
                {
                    Object o = args[i];
                    Map<String, Object> map = new HashMap<>();
                    Field[] fields = o.getClass().getDeclaredFields();
                    try
                    {
                        for (Field f : fields)
                        {
                            f.setAccessible(true);
                            if (f.get(o) != null)
                            {
                                map.put(f.getName(), f.get(o));
                            } else
                            {
                                if (TypeUtil.isNumber(f.getType()))
                                {
                                    map.put(f.getName(), 0);
                                } else
                                {
                                    map.put(f.getName(), "");
                                }
                            }
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    for (String key : map.keySet())
                    {
                        if (map.get(key) == null)
                        {
                            continue;
                        }
                        if (TypeUtil.isNumber(map.get(key).getClass()))
                        {
                            temp = sql.toString().replace("#{" + value + "." + key + "}", map.get(key).toString());
                        } else
                        {
                            temp = sql.toString().replace("#{" + value + "." + key + "}", "'" + map.get(key) + "'");
                        }
                        sql = sql.delete(0, sql.length()).append(temp);
                    }
                    System.out.println("map=" + map);
                } else if (TypeUtil.isNumber(parameters[i].getType()))
                {
                    temp = sql.toString().replace("#{" + value + "}", args[i].toString());
                } else
                {
                    temp = sql.toString().replace("#{" + value + "}", "'" + args[i].toString() + "'");
                }
                sql.delete(0, sql.length()).append(temp);
            }
        }
    }
}
