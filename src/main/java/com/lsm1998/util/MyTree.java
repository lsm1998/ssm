package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2018/12/22-15:25
 * @说明：树结构的顶层接口
 */
public interface MyTree<E> extends Iterable<E>
{
    /**
     * 查找元素
     * @param e
     * @return
     */
    boolean search(E e);

    /**
     * 插入元素
     * @param e
     * @return
     */
    boolean insert(E e);

    /**
     * 删除元素
     * @param e
     * @return
     */
    boolean delete(E e);

    /**
     * 中序遍历
     */
    void inorder();

    /**
     * 后序遍历
     */
    void postorder();

    /**
     * 先序遍历
     */
    void preorder();

    /**
     * 获取大小
     * @return
     */
    int getSize();

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 获取最大值
     * @return
     */
    E getMax();

    /**
     * 获取最小值
     * @return
     */
    E getMin();

    /**
     * 清空元素
     */
    void clear();
}
