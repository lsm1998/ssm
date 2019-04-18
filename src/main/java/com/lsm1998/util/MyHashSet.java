package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间:2018/12/23-0:58
 * @说明：
 */
public class MyHashSet<E> implements MySet<E>
{
    private int size = 0;
    /**
     * 默认初始容量
     */
    private static final int DEFAULT_INITAL_CAPACITY = 4;
    /**
     * 最大容量
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 存储元素的链表
     */
    private MyLinkedList<E>[] table;
    /**
     * 默认负载因子
     */
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75F;
    /**
     * 当前负载因子
     */
    private float loadFactorThreshold;
    /**
     * 当前容量
     */
    private int capacity;

    public MyHashSet()
    {
        this(DEFAULT_INITAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashSet(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashSet(int initialCapacity, float loadFactorThreshold)
    {
        if (initialCapacity > MAXIMUM_CAPACITY)
        {
            this.capacity = MAXIMUM_CAPACITY;
        } else
        {
            this.capacity = trimToPowerOf2(initialCapacity);
        }
        this.loadFactorThreshold = loadFactorThreshold;
        table = new MyLinkedList[capacity];
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
    public boolean contains(Object e)
    {
        int linkedIndex = hash(e.hashCode());
        if (table[linkedIndex] != null)
        {
            MyLinkedList<E> linkedList = table[linkedIndex];
            for (E temp : linkedList)
            {
                if (temp.equals(e))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new MyHashSetIterator(this);
    }

    @Override
    public Object[] toArray()
    {
        E[] es = (E[]) new Object[size];
        int index = 0;
        for (E e : this)
        {
            es[index++] = e;
        }
        return es;
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        int index = 0;
        for (E e : this)
        {
            a[index++] = (T) e;
        }
        return a;
    }

    @Override
    public boolean add(E e)
    {
        if (contains(e))
        {
            return false;
        }
        if (size + 1 > capacity * loadFactorThreshold)
        {
            if (capacity == MAXIMUM_CAPACITY) throw new RuntimeException("超过最大容量");
            rehash();
        }
        int linkedIndex = hash(e.hashCode());
        if (table[linkedIndex] == null)
        {
            table[linkedIndex] = new MyLinkedList<>();
        }
        table[linkedIndex].add(e);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object e)
    {
        if (!contains(e))
        {
            return false;
        }
        int linkedIndex = hash(e.hashCode());
        if (table[linkedIndex] != null)
        {
            MyLinkedList<E> linkedList = table[linkedIndex];
            for (E temp : linkedList)
            {
                if (temp.equals(e))
                {
                    linkedList.remove(e);
                    break;
                }
            }
        }
        size--;
        return true;
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
        size = 0;
        removeEntries();
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

    /**
     * 把容量调整到2的倍数
     *
     * @param initialCapacity
     * @return
     */
    private int trimToPowerOf2(int initialCapacity)
    {
        int capacity = 1;
        while (capacity < initialCapacity)
        {
            capacity <<= 1;
        }
        return capacity;
    }

    private void removeEntries()
    {
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                table[i].clear();
            }
        }
    }

    private int hash(int hashCode)
    {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    private static int supplementalHash(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private void rehash()
    {
        MyHashSet<E> temp = this;
        capacity <<= 1;
        table = new MyLinkedList[capacity];
        size = 0;
        for (E e : temp)
        {
            add(e);
        }
    }

    public MyList<E> setToList()
    {
        MyList<E> list = new MyArrayList<>();
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                MyLinkedList<E> linkedList = table[i];
                for (E e : linkedList)
                {
                    list.add(e);
                }
            }
        }
        return list;
    }

    private class MyHashSetIterator implements Iterator<E>
    {
        private MyList<E> list;
        private int current = 0;
        private MyHashSet<E> set;

        public MyHashSetIterator(MyHashSet<E> set)
        {
            this.set = set;
            list = setToList();
        }

        @Override
        public boolean hasNext()
        {
            return current < list.size();
        }

        @Override
        public E next()
        {
            return list.get(current++);
        }

        @Override
        public void remove()
        {
            set.remove(list.get(current));
            list.remove(current);
        }
    }
}
