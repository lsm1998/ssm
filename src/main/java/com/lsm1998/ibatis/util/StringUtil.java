package com.lsm1998.ibatis.util;

/**
 * @作者：刘时明
 * @时间:2018/12/27-16:39
 * @说明：
 */
public class StringUtil
{
    public static String firstCharToLow(String str)
    {
        if (str == null || str.length() == 0)
            return null;
        else
        {
            char c = str.charAt(0);
            if ((int) c >= 65 && (int) c <= 90)
            {
                String temp = (char) ((int) c + 32) + str.substring(1);
                return temp;
            }
            return str;
        }
    }
}
