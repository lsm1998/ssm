package test.controller;

import com.lsm1998.spring.beans.annotation.MyAutowired;
import com.lsm1998.spring.web.annotation.MyController;
import com.lsm1998.spring.web.annotation.MyJson;
import com.lsm1998.spring.web.annotation.MyRequestMapping;
import com.lsm1998.spring.web.annotation.MyRequestParam;
import com.lsm1998.spring.web.enums.MyRequestMethod;
import test.domain.User;
import test.service.UserService;
import test.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @作者：刘时明
 * @时间:2018/12/29-20:36
 * @说明：
 */
@MyController
@MyRequestMapping("user")
public class UserController
{
    @MyAutowired
    private UserService userService;

    @MyRequestMapping(value = "login", method = MyRequestMethod.POST)
    @MyJson
    public Object login(@MyRequestParam("userName") String userName, @MyRequestParam("passWord") String passWord, HttpServletRequest request)
    {
        User user = userService.login(userName, passWord);
        if (user == null)
        {
            return Result.of(0, "登录失败");
        } else
        {
            request.getSession().setAttribute("user", user);
            return Result.of(1, "登录成功");
        }
    }

    @MyRequestMapping(value = "get", method = MyRequestMethod.GET)
    @MyJson
    public Object get()
    {
        return userService.getAll();
    }

    @MyRequestMapping(value = "find", method = MyRequestMethod.GET)
    @MyJson
    public Object find(@MyRequestParam("id") Long id)
    {
        return userService.getUserById(id);
    }

    @MyRequestMapping(value = "save", method = MyRequestMethod.POST)
    @MyJson
    public Object save(User user)
    {
        boolean result = userService.saveUser(user);
        if (result)
            return Result.of(1, "保存完成");
        else
            return Result.of(0, "保存失败");
    }

    @MyRequestMapping(value = "test", method = MyRequestMethod.GET)
    @MyJson
    public Object test()
    {
        return "调用完成";
    }
}
