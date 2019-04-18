package test.mapper;

import com.lsm1998.ibatis.annotation.MyInsert;
import com.lsm1998.ibatis.annotation.MyParam;
import com.lsm1998.ibatis.annotation.MySelect;
import test.domain.User;

import java.util.List;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:32
 * @说明：
 */
public interface UserMapper
{
    @MyInsert("insert into shop_user(nickName,userName,passWord,age) values(#{user.nickName},#{user.userName},#{user.passWord},#{user.age})")
    int saveUser(@MyParam("user") User user);

    @MySelect("select * from shop_user where userName=#{name} and passWord=#{pass}")
    User login(@MyParam("name") String userName, @MyParam("pass") String passWord);

    @MySelect("select * from shop_user where id>=#{start} and id<=#{end}")
    List<User> findByIdRange(@MyParam("start") int start, @MyParam("end") int end);

    @MySelect("select * from shop_user")
    List<User> getAll();

    @MySelect("select * from shop_user where id=#{id}")
    User getUserById(@MyParam("id") Long id);
}
