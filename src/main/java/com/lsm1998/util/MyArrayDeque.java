package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间:2019/1/1-21:20
 * @说明：
 */
public class MyArrayDeque<E> implements MyQueue<E>
{
    private Object[] elements;
    private int head;
    private int tail;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    @Override
    public boolean add(E e)
    {
        return false;
    }

    @Override
    public boolean offer(E e)
    {
        return false;
    }

    @Override
    public E remove()
    {
        return null;
    }

    @Override
    public E element()
    {
        return null;
    }

    @Override
    public E peek()
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean contains(E e)
    {
        return false;
    }

    @Override
    public Iterator<E> iterator()
    {
        return null;
    }

    @Override
    public Object[] toArray()
    {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return null;
    }

    @Override
    public boolean remove(Object o)
    {
        return false;
    }

    @Override
    public boolean containsAll(MyCollection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(MyCollection<? extends E> c)
    {
        return false;
    }

    @Override
    public boolean removeAll(MyCollection<?> c)
    {
        return false;
    }

    @Override
    public boolean retainAll(MyCollection<?> c)
    {
        return false;
    }

    @Override
    public void clear()
    {

    }
}
