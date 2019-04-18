package com.lsm1998.util.lson;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @作者：刘时明
 * @时间:2018/12/18-15:13
 * @说明：自定义Json
 */
public class Lson
{
    /**
     * json字符转对象
     *
     * @param lson
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T strToObj(String lson, Class<T> clazz)
    {
        if (lson == null || lson.length() <= 2)
        {
            System.err.println("无效的lson字符");
            return null;
        }
        try
        {
            T obj = clazz.getDeclaredConstructor().newInstance();
            if (obj instanceof Map || obj instanceof Collection)
            {
                if (lson.charAt(0) == '[' && lson.charAt(lson.length() - 1) == ']')
                {
                    return toTypes(lson, obj);
                } else
                {
                    System.err.println("此lson字符无法转换为集合类型");
                    return null;
                }
            } else
            {
                if (lson.charAt(0) == '{' && lson.charAt(lson.length() - 1) == '}')
                {
                    return toType(lson, obj);
                } else
                {
                    System.err.println("此lson字符无法转换为复合类型");
                    return null;
                }
            }
        } catch (Exception e)
        {
            System.err.println(clazz.getTypeName() + "类型的对象创建失败");
            e.printStackTrace();
        }
        return null;
    }

    private <T> T toType(String lson, T obj) throws Exception
    {
        lson = subLastAndFirst(lson);
        if (obj.getClass() == String.class || obj.getClass() == char.class || obj.getClass() == Character.class)
        {
            return (T) lson;
        } else
        {
            String[] strs = lson.split(",");
            Map<String, String> map = new HashMap<>();
            for (String s : strs)
            {
                String[] fs = s.split(":");
                map.put(fs[0].substring(1, fs[0].length() - 1), fs[1]);
            }
            for (String s : map.keySet())
            {
                Field field = obj.getClass().getDeclaredField(s);
                field.setAccessible(true);
                Class clazz = field.getType();
                if (clazz == String.class || clazz == Character.class || clazz == char.class)
                {
                    field.set(obj, map.get(s).substring(1, map.get(s).length() - 1));
                } else if (clazz == Integer.class || clazz == int.class)
                {
                    field.set(obj, Integer.parseInt(map.get(s)));
                } else if (clazz == byte.class || clazz == Byte.class)
                {
                    field.set(obj, Byte.parseByte(map.get(s)));
                }
            }
        }
        return obj;
    }

    private <T> T toTypes(String lson, T obj) throws Exception
    {
        lson = subLastAndFirst(lson);
        return null;
    }

    private String subLastAndFirst(String lson)
    {
        return lson.substring(1, lson.length() - 1);
    }

    /**
     * 把一个对象转换为Json字符串
     *
     * @param object
     * @return
     */
    public String ObjToJsonStr(Object object)
    {
        String result = typeOrField(object);
        if (isList(object) || isSet(object))
        {
            if (result.toCharArray()[0] == '[')
            {
                return typeOrField(object);
            } else
            {
                String temp=typeOrField(object);
                if(temp.length()>1)
                {
                    return "[" + typeOrField(object) + "]";
                }else
                {
                    return "[" + typeOrField(object);
                }
            }
        } else
        {
            if (result.toCharArray()[0] == '{')
            {
                return typeOrField(object);
            } else
            {
                return "{" + typeOrField(object) + "}";
            }
        }
    }

    private String typeOrField(Object object)
    {
        if (object instanceof Number || object.getClass() == boolean.class || object.getClass() == Boolean.class)
        {
            return object.toString();
        } else if (object.getClass() == String.class || object.getClass() == char.class || object.getClass() == Character.class)
        {
            return "\"" + object + "\"";
        } else if (isList(object))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List list = (List) object;
            int size = list.size();
            for (int i = 0; i < size; i++)
            {
                sb.append(typeOrField(list.get(i)) + ",");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("]");
            return sb.toString();
        } else if (isSet(object))
        {
            StringBuilder sb = new StringBuilder();
            Set set = (Set) object;
            for (Object o : set)
            {
                sb.append(typeOrField(o) + ",");
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        } else if (isMap(object))
        {
            Map map = (Map) object;
            StringBuilder sb = new StringBuilder();
            for (Object o : map.keySet())
            {
                sb.append("\"" + o + "\"" + ":" + typeOrField(map.get(o)) + ",");
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        } else
        {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            for (Field f : fields)
            {
                sb.append(fieldToStr(f, object));
            }
            sb.delete(sb.length() - 1, sb.length());
            return "{" + sb.toString() + "}";
        }
    }

    private String fieldToStr(Field field, Object object)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            field.setAccessible(true);
            if(field.get(object)==null)
            {
                // 如果字段为空，则返回空字符串
                return "";
            }
            if (field.getType() == String.class || field.getType() == char.class || field.getType() == Character.class)
            {
                sb.append("\""+field.getName() + "\":\"" + field.get(object) + "\",");
            } else if (field.get(object) instanceof Number)
            {
                sb.append("\""+field.getName() + "\":" + field.get(object) + ",");
            } else if (isList(field.get(object)))
            {
                sb.append("\""+field.getName() + "\":" + typeOrField(field.get(object)) + ",");
            } else if (isMap(object))
            {
                sb.append("\""+field.getName() + "\":" + typeOrField(field.get(object)) + ",");
            } else if (isSet(object))
            {
                sb.append("\""+field.getName() + "\":" + typeOrField(field.get(object)) + ",");
            } else
            {
                sb.append("{" + typeOrField(object) + "}");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean isList(Object object)
    {
        return object instanceof List;
    }

    private boolean isMap(Object object)
    {
        return object instanceof Map;
    }

    private boolean isSet(Object object)
    {
        return object instanceof Set;
    }
}
