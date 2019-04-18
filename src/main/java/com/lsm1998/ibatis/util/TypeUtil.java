package com.lsm1998.ibatis.util;

/**
 * @作者：刘时明
 * @时间:2018/12/27-15:43
 * @说明：类型工具类
 */
public class TypeUtil
{
    /**
     * 是否为字符串类型
     *
     * @param clazz
     * @return
     */
    public static boolean isString(Class<?> clazz)
    {
        return clazz == String.class;
    }

    /**
     * 是否为char类型
     *
     * @param clazz
     * @return
     */
    public static boolean isChar(Class<?> clazz)
    {
        return clazz == char.class || clazz == Character.class;
    }

    /**
     * 是否为数字类型
     *
     * @param clazz
     * @return
     */
    public static boolean isNumber(Class<?> clazz)
    {
        if (clazz == int.class || clazz == Integer.class)
        {
            return true;
        } else if (clazz == byte.class || clazz == Byte.class)
        {
            return true;
        } else if (clazz == short.class || clazz == Short.class)
        {
            return true;
        } else if (clazz == long.class || clazz == Long.class)
        {
            return true;
        } else if (clazz == float.class || clazz == Float.class)
        {
            return true;
        } else if (clazz == double.class || clazz == Double.class)
        {
            return true;
        }
        return false;
    }

    /**
     * 是否为复合类型
     *
     * @param clazz
     * @return
     */
    public static boolean isComposite(Class<?> clazz)
    {
        return !(isNumber(clazz) || isString(clazz) || isChar(clazz));
    }

    public static <T> T typeConversion(Class<T> clazz, Object value)
    {
        if (isComposite(clazz))
        {
            System.out.println("复合类型不支持转换");
        } else if (isChar(clazz))
        {
            Character c = Character.valueOf(value.toString().charAt(0));
            return (T) c;
        } else if (isString(clazz))
        {
            return (T) value.toString();
        } else
        {
            Object result;
            if (clazz == Integer.class || clazz == int.class)
            {
                result = Integer.parseInt(value.toString());
            } else if (clazz == Long.class || clazz == long.class)
            {
                result = Long.parseLong(value.toString());
            } else if (clazz == Byte.class || clazz == byte.class)
            {
                result = Byte.parseByte(value.toString());
            } else
            {
                result = Short.parseShort(value.toString());
            }
            return (T) result;
        }
        return null;
    }
}
