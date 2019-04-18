package com.lsm1998.ibatis.session;

import com.lsm1998.ibatis.annotation.MyColumn;
import com.lsm1998.ibatis.annotation.MyNotColumn;
import com.lsm1998.ibatis.annotation.MyTable;
import com.lsm1998.ibatis.util.MySQLUtil;
import com.lsm1998.ibatis.util.TypeUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间:2018/12/29-11:26
 * @说明：
 */
public class MySqlSessionDefultUtil
{
    protected static <T> List<T> getAll(Connection connection, Class<T> clazz)
    {
        String tableName = MySQLUtil.getTableName(clazz);
        String sql = "select * from " + tableName;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            List<T> list = MySQLUtil.getListByResult(rs, clazz);
            return list;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, rs);
        }
        return null;
    }

    protected static boolean delete(Connection connection, Class<?> clazz, Serializable id)
    {
        String tableName = MySQLUtil.getTableName(clazz);
        String IdColumnName = MySQLUtil.getIdColumnName(clazz);
        String sql = "deletr from " + tableName + " where " + IdColumnName + "=?";
        PreparedStatement ps = null;
        try
        {
            ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, null);
        }
        return false;
    }

    protected static boolean saveOrUpdate(Connection connection, Object object)
    {
        Class clazz = object.getClass();
        if (!clazz.isAnnotationPresent(MyTable.class))
        {
            System.err.println("此对象不是实体类：" + clazz.getName());
            return false;
        }
        Map<String, Object> map = MySQLUtil.getMapByObject(object);
        System.out.println(map);
        String IdColumnName = MySQLUtil.getIdColumnName(clazz);
        String tableName = MySQLUtil.getTableName(clazz);
        if (map.containsKey(IdColumnName))
        {
            return update(connection, tableName, map, IdColumnName);
        } else
        {
            return save(connection, tableName, map);
        }
    }

    public static <T> List<T> getAllByPage(Connection connection, Class<T> clazz, int start, int end)
    {

        return null;
    }

    public static int getCount(Connection connection)
    {
        return 0;
    }

    private static boolean update(Connection connection, String tableName, Map<String, Object> map, String IdColumnName)
    {
        Object idValue = map.get(IdColumnName);
        map.remove(IdColumnName);
        StringBuilder sql = new StringBuilder("update " + tableName + " set ");
        for (String key : map.keySet())
        {
            sql.append(key + "=?,");
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(" where " + IdColumnName + "=?");
        System.out.println("更新语句：" + sql);
        PreparedStatement ps = null;
        try
        {
            ps = connection.prepareStatement(sql.toString());
            int i = 1;
            System.out.println("map=" + map);
            for (String key : map.keySet())
            {
                if (map.get(key) != null)
                {
                    ps.setObject(i++, map.get(key));
                }
            }
            ps.setObject(map.size() + 1, idValue);
            return ps.executeUpdate() == 1;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, null);
        }
        return false;
    }

    private static boolean save(Connection connection, String tableName, Map<String, Object> map)
    {
        StringBuilder sql1 = new StringBuilder("insert into " + tableName + "(");
        StringBuilder sql2 = new StringBuilder("values(");
        for (String key : map.keySet())
        {
            sql1.append(key + ",");
            sql2.append("?,");
        }
        sql1.delete(sql1.length() - 1, sql1.length());
        sql1.append(") ");
        sql2.delete(sql2.length() - 1, sql2.length());
        sql2.append(")");
        String sql = sql1.toString() + sql2;
        System.out.println("新增语句：" + sql);
        PreparedStatement ps = null;
        try
        {
            ps = connection.prepareStatement(sql);
            int i = 1;
            for (String key : map.keySet())
            {
                ps.setObject(i++, map.get(key));
            }
            return ps.executeUpdate() == 1;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            MySQLUtil.closeAll(null, ps, null);
        }
        return false;
    }
}
