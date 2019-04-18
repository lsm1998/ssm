package com.lsm1998.spring.web.method;

import java.util.HashMap;

/**
 * @作者：刘时明
 * @时间：18-12-21-上午11:16
 * @说明：视图&数据
 */
public class MyModelAndView extends HashMap<String, Object>
{
    private String viewName;

    public String getViewName()
    {
        return viewName;
    }

    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }

    public MyModelAndView(String viewName)
    {
        this.viewName = viewName;
    }

    public MyModelAndView()
    {
        this("");
    }

    public boolean addObject(String key, Object value)
    {
        return super.put(key, value) == null;
    }
}
