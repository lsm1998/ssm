package com.lsm1998.ibatis.util;

import java.lang.reflect.Field;

/**
 * @作者：刘时明
 * @时间：18-12-25-上午10:38
 * @说明：
 */
public class MyFieldUtil
{
    private static final String VAR_CHAR = "varchar";
    private static final String CHAR = "char";
    private static final String INT = "int";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String SMALLINT = "smallint";
    private static final String TINYINT = "tinyint";
    private static final String BIGINT = "bigint";

    public static String getDefultType(Field field)
    {
        if (field.getType() == String.class)
        {
            return VAR_CHAR;
        } else if (field.getType() == Character.class || field.getType() == char.class)
        {
            return CHAR;
        } else if (field.getType() == Integer.class || field.getType() == int.class)
        {
            return INT;
        } else if (field.getType() == Float.class || field.getType() == float.class)
        {
            return FLOAT;
        } else if (field.getType() == Double.class || field.getType() == double.class)
        {
            return DOUBLE;
        } else if (field.getType() == Short.class || field.getType() == short.class)
        {
            return SMALLINT;
        } else if (field.getType() == Byte.class || field.getType() == byte.class)
        {
            return TINYINT;
        } else if (field.getType() == Long.class || field.getType() == long.class)
        {
            return BIGINT;
        }
        return null;
    }

    public static int getDefultLen(Field field)
    {
        if (field.getType() == String.class)
        {
            return 255;
        } else if (field.getType() == Character.class || field.getType() == char.class)
        {
            return 1;
        } else if (field.getType() == Integer.class || field.getType() == int.class)
        {
            return -1;
        } else if (field.getType() == Float.class || field.getType() == float.class)
        {
            return -1;
        } else if (field.getType() == Double.class || field.getType() == double.class)
        {
            return -1;
        } else if (field.getType() == Short.class || field.getType() == short.class)
        {
            return -1;
        } else if (field.getType() == Byte.class || field.getType() == byte.class)
        {
            return -1;
        } else if (field.getType() == Long.class || field.getType() == long.class)
        {
            return -1;
        }
        return -1;
    }
}
