package com.lsm1998.ibatis.auto;

import com.lsm1998.ibatis.annotation.*;
import com.lsm1998.ibatis.builder.MyAnnotationConfigBuilder;
import com.lsm1998.ibatis.enums.AutoIncrement;
import com.lsm1998.ibatis.util.MyFieldUtil;
import com.lsm1998.ibatis.util.MySQLUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import static com.lsm1998.ibatis.enums.AutoIncrement.TRUE;

/**
 * @作者：刘时明
 * @时间：18-12-24-下午4:43
 * @说明：
 */
public class AutoTables
{
    private String dir;
    private String webPath;

    public AutoTables()
    {
        this.dir = MyAnnotationConfigBuilder.SEPARATE;
        scanPath("src" + dir + "main" + dir + "java" + dir);
    }

    private void init()
    {
        webPath = this.getClass().getClassLoader().getResource("/").getPath();
        File[] files = new File(webPath).listFiles();
        for (File f : files)
        {
            test(f.getAbsolutePath());
        }
    }

    private void test(String path)
    {
        File file = new File(path);
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                test(f.getAbsolutePath());
            }
        } else
        {
            if (path.endsWith(".class"))
            {
                int firstIndex = path.indexOf("classes");
                int lastIndex = path.lastIndexOf(".");
                path = path.substring(0, lastIndex);
                String classpath=path.substring(firstIndex+8).replace(dir,".");
                try
                {
                    Class<?> clazz=Class.forName(classpath);
                    if(clazz.isAnnotationPresent(MyEntry.class))
                    {
                        loadEntry(clazz);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void scanPath(String path)
    {
        if (new File(path).length() == 0)
        {
            init();
        }else
        {
            File[] files = new File(path).listFiles((dir, src) -> dir.isDirectory() || src.endsWith(".java"));
            if (files != null)
            {
                for (File f : files)
                {
                    if (f.isDirectory())
                    {
                        scanPath(f.getAbsolutePath());
                    } else
                    {
                        loadEntry(f.getAbsolutePath());
                    }
                }
            }
        }
    }

    private void loadEntry(String path)
    {
        int index = path.indexOf("src" + dir + "main" + dir + "java" + dir);
        int lastIndex = path.lastIndexOf(".");
        Class<?> clazz;
        try
        {
            clazz = Class.forName(path.substring(index + 14, lastIndex).replace(dir, "."));
        } catch (ClassNotFoundException e)
        {
            System.err.println("路径找不到：" + path.substring(index + 14, lastIndex).replace(dir, "."));
            return;
        }
        if (clazz.isAnnotationPresent(MyEntry.class) && clazz.isAnnotationPresent(MyTable.class))
        {
            loadEntry(clazz);
        }
    }

    private void loadEntry(Class<?> clazz)
    {
        System.out.println("找到实体：" + clazz);
        MyTable table = clazz.getAnnotation(MyTable.class);
        String tableName = table.name();

        if (MySQLUtil.tableIsExis(tableName))
        {
            Map<String, String[]> map = MySQLUtil.getTableStructure(tableName);
            Set<String> fieldSet = new HashSet<>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields)
            {
                if (f.isAnnotationPresent(MyNotColumn.class))
                {
                    System.out.println("不映射字段");
                } else
                {
                    String fieldName;
                    if (f.isAnnotationPresent(MyColumn.class))
                    {
                        MyColumn myColumn = f.getAnnotation(MyColumn.class);
                        fieldName = myColumn.value();
                        if (fieldName.equals(""))
                        {
                            fieldName = f.getName();
                        }
                    } else
                    {
                        fieldName = f.getName();
                    }
                    StringBuilder sql = new StringBuilder();
                    boolean isId = f.isAnnotationPresent(MyId.class);
                    boolean isIncrement = false;
                    if (isId)
                    {
                        MyId myId = f.getAnnotation(MyId.class);
                        isIncrement = myId.value() == TRUE;
                    }
                    if (map.containsKey(fieldName))
                    {
                        System.out.println(fieldName + "存在，准备更新");
                        sql.append("ALTER TABLE " + tableName + " MODIFY ");
                        String type = MyFieldUtil.getDefultType(f);
                        int len = MyFieldUtil.getDefultLen(f);
                        boolean isUnique = false;
                        boolean isNullable = false;
                        if (f.isAnnotationPresent(MyColumn.class))
                        {
                            MyColumn myColumn = f.getAnnotation(MyColumn.class);
                            if (!myColumn.type().equals(""))
                            {
                                type = myColumn.type();
                            }
                            if (myColumn.length() != -1)
                            {
                                len = myColumn.length();
                            }
                            isUnique = myColumn.unique();
                            isNullable = myColumn.nullable();
                        }

                        if (type.equals(map.get(fieldName)[0]) && len == Integer.parseInt(map.get(fieldName)[1]))
                        {
                            System.out.println("与原表一致");
                        } else
                        {
                            System.out.println("type=" + type);
                            System.out.println("len=" + len);
                            System.out.println(fieldName + Arrays.toString(map.get(fieldName)));
                            String exeSql = MySQLUtil.generateSQL(sql, fieldName, type, len, isUnique, isNullable, false, false);
                            if (isIncrement)
                            {
                                System.out.println("当前=" + fieldName);
                                exeSql = exeSql + " auto_increment";
                            }
                            System.out.println("与原表不一致，需要更新，SQL语句=" + exeSql);
                            boolean result = MySQLUtil.exeSql(exeSql);
                            System.out.println("result=" + result);
                        }
                    } else
                    {
                        // ALTER TABLE [表名] ADD [字段名] NVARCHAR (50) NULL
                        sql.append("ALTER TABLE " + tableName + " ADD ");
                        String type;
                        boolean isUnique;
                        boolean isNullable;
                        int len;
                        if (f.isAnnotationPresent(MyColumn.class))
                        {
                            MyColumn myColumn = f.getAnnotation(MyColumn.class);
                            isUnique = myColumn.unique();
                            isNullable = myColumn.nullable();
                            type = myColumn.type();
                            if (type.equals(""))
                            {
                                type = MyFieldUtil.getDefultType(f);
                            }
                            len = myColumn.length();
                            if (len == -1)
                            {
                                len = MyFieldUtil.getDefultLen(f);
                            }
                        } else
                        {
                            isUnique = false;
                            isNullable = false;
                            len = MyFieldUtil.getDefultLen(f);
                            type = MyFieldUtil.getDefultType(f);
                        }
                        String exeSql = MySQLUtil.generateSQL(sql, fieldName, type, len, isUnique, isNullable, isId, isIncrement);
                        System.out.println("新增字段，SQL语句=" + exeSql);
                        try
                        {
                            MySQLUtil.exeSql(exeSql);
                        } catch (Exception e)
                        {
                            MySQLUtil.exeSql("ALTER TABLE " + tableName + " DROP COLUMN " + fieldName);
                            System.out.println("修改的是主键");
                            MySQLUtil.exeSql(exeSql);
                        }
                    }
                    fieldSet.add(fieldName);
                }
            }
            // 删除没有映射的字段
            for (String s : map.keySet())
            {
                if (!fieldSet.contains(s))
                {
                    System.out.println(s + "没有映射，需要删除");
                    MySQLUtil.exeSql("ALTER TABLE " + tableName + " DROP COLUMN " + s);
                }
            }
        } else
        {
            System.out.println("新建表：" + tableName);
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + tableName + "(");
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields)
            {
                if (!f.isAnnotationPresent(MyNotColumn.class))
                {
                    boolean isId = false;
                    boolean isIncrement = true;
                    boolean isUnique = false;
                    boolean isNullable = false;
                    String value;
                    String type;
                    int len = -1;
                    if (f.isAnnotationPresent(MyId.class))
                    {
                        isId = true;
                        MyId myId = f.getAnnotation(MyId.class);
                        isIncrement = myId.value() == TRUE;
                    }
                    if (f.isAnnotationPresent(MyColumn.class))
                    {
                        MyColumn myColumn = f.getAnnotation(MyColumn.class);
                        isUnique = myColumn.unique();
                        isNullable = myColumn.nullable();
                        value = myColumn.value();
                        len = myColumn.length();
                        if (len == -1)
                        {
                            len = MyFieldUtil.getDefultLen(f);
                        }
                        if (value.equals(""))
                        {
                            value = f.getName();
                        }
                        type = myColumn.type();
                    } else
                    {
                        value = f.getName();
                        type = MyFieldUtil.getDefultType(f);
                        len = MyFieldUtil.getDefultLen(f);
                    }
                    sql.append(MySQLUtil.generateSQL(new StringBuilder(), value, type, len, isUnique, isNullable, isId, isIncrement) + ",");
                }
            }
            List<String> list = MySQLUtil.getIndexNameList(fields);

            for (String str : list)
            {
                sql.append("index(" + str + "),");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(")character set utf8");
            MySQLUtil.exeSql(sql.toString());
            System.out.println("建表SQL:" + sql);
        }
    }
}
