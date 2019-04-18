package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间:2019/1/1-21:08
 * @说明：
 */
public interface MyDeque<E> extends MyQueue<E>
{
    void addFirst(E e);

    void addLast(E e);

    boolean offerFirst(E e);

    boolean offerLast(E e);

    E removeFirst();

    E removeLast();

    E pollFirst();

    E pollLast();

    E getFirst();

    E getLast();

    E peekFirst();

    E peekLast();

    boolean removeFirstOccurrence(Object o);

    boolean removeLastOccurrence(Object o);

    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();

    boolean addAll(MyCollection<? extends E> c);

    void push(E e);

    E pop();

    boolean remove(Object o);

    boolean contains(Object o);

    int size();

    Iterator<E> iterator();
}
