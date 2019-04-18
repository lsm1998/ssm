package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：18-12-19-下午2:03
 * @说明：自定义集合框架顶层接口
 */
public interface MyCollection<E> extends Iterable<E>
{
    /**
     * 返回集合大小
     *
     * @return
     */
    int size();

    /**
     * 返回集合是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 查找元素
     *
     * @param e
     * @return
     */
    boolean contains(E e);

    /**
     * 返回迭代器
     *
     * @return
     */
    Iterator<E> iterator();

    /**
     * 返回集合数组
     *
     * @return
     */
    Object[] toArray();

    /**
     * 返回集合数组
     *
     * @param a
     * @param <T>
     * @return
     */
    <T> T[] toArray(T[] a);

    /**
     * 新增元素，返回是否成功
     *
     * @param e
     * @return
     */
    boolean add(E e);

    /**
     * 删除元素，返回是否成功
     *
     * @param o
     * @return
     */
    boolean remove(Object o);

    /**
     * 判断集合包含关系
     *
     * @param c
     * @return
     */
    boolean containsAll(MyCollection<?> c);

    /**
     * 新增多个元素
     *
     * @param c
     * @return
     */
    boolean addAll(MyCollection<? extends E> c);

    /**
     * 删除多个元素
     *
     * @param c
     * @return
     */
    boolean removeAll(MyCollection<?> c);

    /**
     * 调用者与传入的集合取交集，并把交集赋给调用者，如果没有交集则返回false
     *
     * @param c
     * @return
     */
    boolean retainAll(MyCollection<?> c);

    /**
     * 清空元素
     */
    void clear();
}
