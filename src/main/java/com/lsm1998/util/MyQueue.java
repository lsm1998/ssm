package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2018/12/22-21:48
 * @说明：
 */
public interface MyQueue<E> extends MyCollection<E>
{
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E element();

    E peek();
}
