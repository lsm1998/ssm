package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间：18-12-19-下午2:28
 * @说明：自定义线性表顶层接口
 */
public interface MyList<E> extends MyCollection<E>
{
    /**
     * 新增一个元素到指定位置
     *
     * @param index
     * @param e
     * @return
     */
    boolean add(int index, E e);

    /**
     * 删除指定位置的元素
     *
     * @param index
     * @return
     */
    boolean remove(int index);

    /**
     * 获取指定位置的元素
     *
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 更新指定位置的元素
     *
     * @param index
     * @param e
     * @return
     */
    E set(int index, E e);

    /**
     * 返回指定元素的最小下标
     *
     * @param o
     * @return
     */
    int indexOf(Object o);

    /**
     * 返回指定元素的最大下标
     *
     * @param o
     * @return
     */
    int lastIndexOf(Object o);
}
