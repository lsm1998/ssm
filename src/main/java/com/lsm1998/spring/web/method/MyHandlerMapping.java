package com.lsm1998.spring.web.method;

import com.lsm1998.spring.web.annotation.MyJson;
import com.lsm1998.spring.web.annotation.MyRequestMapping;
import com.lsm1998.spring.web.annotation.MyRequestParam;
import com.lsm1998.spring.web.enums.MyRequestMethod;
import com.lsm1998.util.lson.Lson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @作者：刘时明
 * @时间：18-12-21-上午10:54
 * @说明：处理路径映射
 */
public class MyHandlerMapping
{
    private static Map<String, MyHandlerMethod> handlerMap = new ConcurrentHashMap();

    public static void registerMapping(Class clazz, Object bean)
    {
        MyRequestMapping controller = (MyRequestMapping) clazz.getAnnotation(MyRequestMapping.class);
        String url = subUrl(controller.value());
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods)
        {
            if (m.isAnnotationPresent(MyRequestMapping.class))
            {
                MyRequestMapping requestMapping = m.getDeclaredAnnotation(MyRequestMapping.class);
                String value = subUrl(requestMapping.value());
                MyHandlerMethod handlerMethod = new MyHandlerMethod(bean, m);
                handlerMap.put(url + value, handlerMethod);
            }
        }
    }

    private static String subUrl(String url)
    {
        if (url.length() > 0 && url.charAt(0) != '/')
        {
            url = '/' + url;
        }
        return url;
    }

    public void request(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html; charset=utf-8");
        String url = req.getRequestURI();
        url = url.substring(0, url.length() - 3);
        if (!handlerMap.containsKey(url))
        {
            resp.getWriter().println("<h1>" + url + "请求不存在，404</h1>");
        }
        MyHandlerMethod handlerMethod = handlerMap.get(url);
        if (validate(req, resp, handlerMethod.method))
        {
            // 注入参数并返回
            Object[] args = initParameter(req, resp, handlerMethod.method);
            Object result = null;
            try
            {
                // 执行方法并获取结果
                result = handlerMethod.method.invoke(handlerMethod.bean, args);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            if (handlerMethod.method.getAnnotation(MyJson.class) != null)
            {
                // 返回json请求
                resp.setContentType("text/json; charset=utf-8");
                Lson lson = new Lson();
                resp.getWriter().println(lson.ObjToJsonStr(result));
//                Gson gson=new Gson();
//                resp.getWriter().println(gson.toJson(result));
            } else
            {
                if (result instanceof String)
                {
                    req.getRequestDispatcher(result.toString()).forward(req, resp);
                } else if (result instanceof MyModelAndView)
                {
                    MyModelAndView modelAndView = (MyModelAndView) result;
                    for (String key : modelAndView.keySet())
                    {
                        req.setAttribute(key, modelAndView.get(key));
                    }
                    System.out.println("页面跳转=" + modelAndView.getViewName());
                    req.getRequestDispatcher(modelAndView.getViewName()).forward(req, resp);
                } else
                {
                    resp.getWriter().println("<h1>返回值异常，500</h1>");
                }
            }
        } else
        {
            resp.getWriter().println("，请求错误，405</h1>");
        }
    }


    private boolean validate(HttpServletRequest req, HttpServletResponse resp, Method method) throws IOException
    {
        String methodType = req.getMethod();
        MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
        if (requestMapping.method().length == 0)
        {
            return true;
        }

        for (MyRequestMethod requestMethod : requestMapping.method())
        {
            if (requestMethod.toString().equals(methodType))
            {
                return true;
            }
        }
        resp.getWriter().append("<h1>你的请求类型为：" + methodType + "，但是接受类型是：" + Arrays.toString(requestMapping.method()));
        return false;
    }

    // 初始化方法参数
    private Object[] initParameter(HttpServletRequest req, HttpServletResponse resp, Method method)
    {
        Parameter[] parameters = method.getParameters();
        Map<String, String[]> parameMap = req.getParameterMap();

        Object[] args = new Object[parameters.length];
        // 注入默认的参数
        for (int i = 0; i < args.length; i++)
        {
            if (parameters[i].getType() == HttpServletRequest.class)
            {
                args[i] = req;
            } else if (parameters[i].getType() == HttpServletResponse.class)
            {
                args[i] = resp;
            } else if (parameters[i].getType() == MyModelAndView.class)
            {
                args[i] = new MyModelAndView();
            } else if (parameters[i].isAnnotationPresent(MyRequestParam.class))
            {
                MyRequestParam requestParam = parameters[i].getAnnotation(MyRequestParam.class);
                String key = requestParam.value();
                if (parameMap.containsKey(key))
                {
                    if (parameMap.get(key).length == 1)
                    {
                        args[i] = parameMap.get(key)[0];
                    } else
                    {
                        args[i] = parameMap.get(key);
                    }
                }
            } else
            {
                try
                {
                    Object obj = parameters[i].getType().getConstructor().newInstance();
                    Field[] fields = parameters[i].getType().getDeclaredFields();
                    for (Field f : fields)
                    {
                        if (parameMap.containsKey(f.getName()))
                        {
                            f.setAccessible(true);
                            if (parameMap.get(f.getName()) != null)
                            {
                                setValue(f, obj, parameMap.get(f.getName())[0]);
                            }
                        }
                    }
                    args[i] = obj;
                } catch (Exception e)
                {
                    System.err.println("无法创建对象：" + parameters[i].getType());
                    e.printStackTrace();
                }
                System.out.println("无法初始化的类型：" + parameters[i].getType());
            }
        }
        return args;
    }

    private void setValue(Field f, Object obj, String s) throws Exception
    {
        if (f.getType() == String.class)
        {
            f.set(obj, s);
        } else if (f.getType() == Integer.class || f.getType() == int.class)
        {
            f.set(obj, Integer.parseInt(s));
        } else if (f.getType() == Double.class || f.getType() == double.class)
        {
            f.set(obj, Double.parseDouble(s));
        } else if (f.getType() == Float.class || f.getType() == float.class)
        {
            f.set(obj, Float.parseFloat(s));
        } else if (f.getType() == Long.class || f.getType() == long.class)
        {
            f.set(obj, Long.parseLong(s));
        } else if (f.getType() == Byte.class || f.getType() == byte.class)
        {
            f.set(obj, Byte.parseByte(s));
        } else if (f.getType() == Short.class || f.getType() == short.class)
        {
            f.set(obj, Short.parseShort(s));
        } else if (f.getType() == Character.class || f.getType() == char.class)
        {
            f.set(obj, s);
        }
    }
}
