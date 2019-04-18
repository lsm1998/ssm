package com.lsm1998.ibatis.util;

import com.lsm1998.ibatis.annotation.MyColumn;
import com.lsm1998.ibatis.annotation.MyId;
import com.lsm1998.ibatis.annotation.MyNotColumn;
import com.lsm1998.ibatis.annotation.MyTable;
import com.lsm1998.ibatis.session.MyConfiguration;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @作者：刘时明
 * @时间：18-12-24-下午5:33
 * @说明：
 */
public class MySQLUtil
{
    private static MyConfiguration configuration;
    private static Connection connection;

    public static void setConfiguration(MyConfiguration configuration)
    {
        MySQLUtil.configuration = configuration;
    }

    private static Connection getConnection() throws SQLException
    {
        if (connection == null || connection.isClosed())
        {
            connection = DriverManager.getConnection(configuration.url, configuration.username, configuration.password);
        }
        return connection;
    }

    public static boolean tableIsExis(String tableName)
    {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try
        {
            connection = getConnection();
            String sql = "show tables";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next())
            {
                if (rs.getObject(1).toString().equals(tableName))
                {
                    return true;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            closeAll(connection, statement, rs);
        }
        return false;
    }

    public static Map<String, String[]> getTableStructure(String tableName)
    {
        Map<String, String[]> map = new HashMap<>();
        String sql = "select column_name,data_type,CHARACTER_MAXIMUM_LENGTH \n" +
                "\n" +
                "        from information_schema.columns\n" +
                "\n" +
                "        where table_name=?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try
        {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, tableName);
            rs = statement.executeQuery();
            while (rs.next())
            {
                String[] strings = new String[2];
                strings[0] = rs.getString(2);
                strings[1] = rs.getString(3);
                if (strings[1] == null)
                {
                    strings[1] = "-1";
                }
                map.put(rs.getString(1), strings);
            }
            return map;
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            closeAll(connection, statement, rs);
        }
        return map;
    }

    public static void closeAll(Connection connection, Statement statement, ResultSet rs)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (statement != null)
                {
                    statement.close();
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (connection != null)
                    {
                        connection.close();
                    }
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        System.gc();
    }

    /**
     * 字段名、类型、长度、是否索引、是否唯一约束、是否不为空、是否ID、是否自增
     *
     * @param sql
     * @param name
     * @param type
     * @param len
     * @param isUnique
     * @param isNullable
     * @param isId
     * @param isIncrement
     * @return
     */
    public static String generateSQL(StringBuilder sql, String name, String type, int len, boolean isUnique, boolean isNullable, boolean isId, boolean isIncrement)
    {
        return sql.append(name + " " + type + ((len == -1) ? "" : "(" + len + ")") + " " +
                (isId ? "primary key " + ((isIncrement) ? "auto_increment" : "") : "") + (isUnique ? " unique" : "") +
                (isNullable ? " notnull" : "")).toString();
    }

    public static List<String> getIndexNameList(Field[] fields)
    {
        List<String> list=new ArrayList<>();
        for (Field f:fields)
        {
            if(f.isAnnotationPresent(MyColumn.class))
            {
                MyColumn column=f.getAnnotation(MyColumn.class);
                if(column.index())
                {
                    if(column.value().equals(""))
                    {
                        list.add(f.getName());
                    }else
                    {
                        list.add(column.value());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 执行一个SQL语句
     *
     * @param sql
     * @return
     */
    public static boolean exeSql(String sql)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        try
        {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            return statement.execute();
        } catch (SQLException e)
        {
            if (e.getClass() == SQLSyntaxErrorException.class)
            {

            } else
            {
                e.printStackTrace();
            }
        } finally
        {
            closeAll(connection, statement, null);
        }
        return false;
    }

    /**
     * 根据类型获取表名
     *
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz)
    {
        if (clazz.isAnnotationPresent(MyTable.class))
        {
            MyTable myTable = clazz.getAnnotation(MyTable.class);
            return myTable.name();
        } else
        {
            System.err.println("该对象不是实体：" + clazz.getSimpleName());
        }
        return null;
    }

    /**
     * 根据结果集封装返回一个集合
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getListByResult(ResultSet rs, Class<T> clazz)
    {
        List<T> list = new ArrayList<>();
        try
        {
            while (rs.next())
            {
                T t = clazz.getConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields)
                {
                    if (!f.isAnnotationPresent(MyNotColumn.class))
                    {
                        MyColumn column = f.getAnnotation(MyColumn.class);
                        String name;
                        if (column == null || column.value().equals(""))
                        {
                            name = f.getName();
                        } else
                        {
                            name = column.value();
                        }
                        f.setAccessible(true);
                        if (rs.getObject(name) == null)
                        {
                            continue;
                        }
                        f.set(t, TypeUtil.typeConversion(f.getType(), rs.getObject(name)));
                    }
                }
                list.add(t);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取逐渐列名
     *
     * @param clazz
     * @return
     */
    public static String getIdColumnName(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(MyId.class) && f.isAnnotationPresent(MyColumn.class))
            {
                MyColumn column = f.getAnnotation(MyColumn.class);
                if (column.value().equals(""))
                {
                    return f.getName();
                } else
                {
                    return column.value();
                }
            } else if (f.isAnnotationPresent(MyId.class))
            {
                return f.getName();
            }
        }
        return null;
    }

    public static Map<String, Object> getMapByObject(Object object)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try
        {
            for (Field f : fields)
            {
                if (!f.isAnnotationPresent(MyNotColumn.class))
                {
                    f.setAccessible(true);
                    Object value = f.get(object);
                    if (value == null)
                    {
                        continue;
                    }
                    System.out.println("封装map集合:key=" + f.getName() + ",value=" + value);
                    if (f.isAnnotationPresent(MyColumn.class))
                    {
                        MyColumn column = f.getAnnotation(MyColumn.class);
                        if (column.value().equals(""))
                        {
                            map.put(f.getName(), value);
                        } else
                        {
                            map.put(column.value(), value);
                        }
                    } else
                    {
                        map.put(f.getName(), value);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
}
