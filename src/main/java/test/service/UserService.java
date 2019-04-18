package test.service;

import test.domain.User;

import java.util.List;

/**
 * @作者：刘时明
 * @时间:2018/12/29-21:04
 * @说明：
 */
public interface UserService
{
    List<User> getAll();

    User login(String userName, String passWord);

    User getUserById(Long id);

    boolean saveUser(User user);
}
