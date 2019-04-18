package test.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @作者：刘时明
 * @时间：19-1-8-下午3:10
 * @说明：
 */
public class Result extends ConcurrentHashMap<String,Object>
{
    private static Result result=new Result();
    private static Lock lock=new ReentrantLock();

    public static Result of(int code,String msg,String error)
    {
        try
        {
            lock.lock();
            result.clear();
            result.put("code",code);
            result.put("msg",msg);
            result.put("error",error);
            return result;
        }finally
        {
            lock.unlock();
        }
    }

    public static Result of(int code,String msg)
    {
        try
        {
            lock.lock();
            result.clear();
            result.put("code",code);
            result.put("msg",msg);
            return result;
        }finally
        {
            lock.unlock();
        }
    }
}
