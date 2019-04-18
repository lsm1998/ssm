package test.service.impl;

import com.lsm1998.ibatis.session.MySqlSession;
import com.lsm1998.ibatis.session.MySqlSessionFactory;
import com.lsm1998.spring.aop.annotation.MyAfter;
import com.lsm1998.spring.aop.annotation.MyAfterThrowing;
import com.lsm1998.spring.aop.annotation.MyAspect;
import com.lsm1998.spring.aop.annotation.MyBefore;
import com.lsm1998.spring.aop.enums.Type;
import com.lsm1998.spring.beans.annotation.MyAutowired;
import com.lsm1998.spring.beans.annotation.MyService;
import test.domain.User;
import test.mapper.UserMapper;
import test.service.UserService;

import java.util.List;

/**
 * @作者：刘时明
 * @时间:2018/12/29-21:07
 * @说明：
 */
@MyService
@MyAspect
public class UserServiceImpl implements UserService
{
    @MyAutowired
    private MySqlSessionFactory sessionFactory;

    @Override
    public List<User> getAll()
    {
        MySqlSession session = sessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> result = mapper.getAll();
        session.close();
        return result;
    }

    @Override
    @MyBefore(value = "args(准备执行login方法，账号：#{0}，密码：#{1})",type = Type.PRINT)
    @MyAfter(value = "args(login方法执行完毕,账号：#{0}，密码：#{1})",type = Type.PRINT)
    @MyAfterThrowing(value = "args(发生异常，账号：#{0})")
    public User login(String userName, String passWord)
    {
        MySqlSession session = sessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User result = mapper.login(userName, passWord);
        session.close();
        return result;
    }

    @Override
    public User getUserById(Long id)
    {
        MySqlSession session = sessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User result = mapper.getUserById(id);
        session.close();
        return result;
    }

    @Override
    public boolean saveUser(User user)
    {
        MySqlSession session = sessionFactory.openSession();
        boolean result = session.saveOrUpdate(user);
        session.close();
        return result;
    }
}
