package com.lsm1998.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间：18-12-19-下午2:40
 * @说明：自定义基于数组实现的线性表
 */
public class MyArrayList<E> implements MyList<E>, Serializable, Iterable<E>
{
    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 存储元素的数组
     */
    private Object[] elementData;

    /**
     * 当前大小
     */
    private int size;

    /**
     * 当前容量
     */
    private int capacity;

    /**
     * 默认构造方法
     */
    public MyArrayList()
    {
        elementData = new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    /**
     * 指定初始容量的构造方法
     *
     * @param initialCapacity
     */
    public MyArrayList(int initialCapacity)
    {
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException("线性表长度不可以小于0");
        }
        elementData = new Object[initialCapacity];
        capacity = initialCapacity;
    }

    @Override
    public boolean add(int index, E e)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        if (index >= capacity)
        {
            capacityAdd();
        }
        elementData[index] = e;
        size++;
        return true;
    }

    private void capacityAdd()
    {
        capacity = capacity * 2 + 1;
        Object[] temp = new Object[capacity];
        for (int i = 0; i < size; i++)
        {
            temp[i] = elementData[i];
        }
        elementData = temp;
    }

    @Override
    public boolean remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        for (int i = index; i < size; i++)
        {
            elementData[i] = elementData[i + 1];
        }
        size--;
        return true;
    }

    @Override
    public E get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E e)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        elementData[index] = e;
        return e;
    }

    @Override
    public int indexOf(Object o)
    {
        for (int i = 0; i < size; i++)
        {
            if (elementData[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        for (int i = size - 1; i >= 0; i--)
        {
            if (elementData[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        for (int i = 0; i < size; i++)
        {
            if (elementData[i].equals(o))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray()
    {
        return elementData;
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        a = (T[]) elementData;
        return a;
    }

    @Override
    public boolean add(E e)
    {
        return add(size, e);
    }

    @Override
    public boolean remove(Object o)
    {
        return remove(indexOf(o));
    }

    @Override
    public boolean containsAll(MyCollection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(MyCollection<? extends E> c)
    {
        for (E e : c)
        {
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(MyCollection<?> c)
    {
        Iterator<?> iterator = c.iterator();
        boolean result = false;
        while (iterator.hasNext())
        {
            int index;
            if ((index = indexOf(iterator.next())) != -1)
            {
                remove(index);
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(MyCollection<?> c)
    {
        return false;
    }

    @Override
    public void clear()
    {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }

    /**
     * 实现增强for循环需要Iterator类型
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        private int current = 0;

        @Override
        public void remove()
        {
            MyArrayList.this.remove(--current);
        }

        @Override
        public boolean hasNext()
        {
            return current < size;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) elementData[current++];
        }
    }

    @Override
    public String toString()
    {
        StringBuffer result = new StringBuffer("[");
        this.forEach(e -> result.append(e + ","));
        if (result.length() > 1)
            result.delete(result.length() - 1, result.length());
        return result.append("]").toString();
    }
}
