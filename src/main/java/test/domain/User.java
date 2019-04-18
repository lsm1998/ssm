package test.domain;

import com.lsm1998.ibatis.annotation.MyColumn;
import com.lsm1998.ibatis.annotation.MyEntry;
import com.lsm1998.ibatis.annotation.MyId;
import com.lsm1998.ibatis.annotation.MyTable;

import java.io.Serializable;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:33
 * @说明：
 */
@MyEntry
@MyTable(name = "shop_user", update = true)
public class User implements Serializable
{
    @MyId
    private Long id;
    @MyColumn(value = "userName", index = true, unique = true, length = 55, type = "varchar")
    private String userName;
    @MyColumn(value = "passWord", index = true, length = 55, type = "varchar")
    private String passWord;
    private String nickName;
    private Integer age;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                '}';
    }
}
