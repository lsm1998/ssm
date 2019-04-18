package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2019/1/1-18:53
 * @说明：
 */
public class MyHashMap<K, V> implements MyMap<K, V>
{
    private int size = 0;
    /**
     * 初始容量
     */
    private static int DEFAULT_INITIAL_CAPACITY = 4;

    /**
     * 最大容量
     */
    private static int MAXIMUM__CAPACITY = 1 << 30;
    /**
     * 当前容量
     */
    private int capacity;
    /**
     * 默认负载因子
     */
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75F;
    /**
     * 当前负载因子
     */
    private float loadFactorThreshold;

    /**
     * 开放链地址使用的集合
     */
    private MyLinkedList<MyEntry<K, V>>[] table;

    public MyHashMap()
    {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyHashMap(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactorThreshold)
    {
        if (initialCapacity > MAXIMUM__CAPACITY)
        {
            this.capacity = MAXIMUM__CAPACITY;
        } else
        {
            this.capacity = trimToPowerOf2(initialCapacity);
        }
        this.loadFactorThreshold = loadFactorThreshold;
        table = new MyLinkedList[capacity];
    }

    @Override
    public void clear()
    {
        size = 0;
        removeEntries();
    }

    @Override
    public boolean containsKey(K key)
    {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value)
    {
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                MyLinkedList<MyEntry<K, V>> linkedList = table[i];
                for (MyEntry<K, V> entry : linkedList)
                {
                    if (entry.getValue().equals(value))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public MySet<MyEntry<K, V>> entrySet()
    {
        MySet<MyEntry<K, V>> set = new MyHashSet<>();
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                MyLinkedList<MyEntry<K, V>> linkedList = table[i];
                for (MyEntry<K, V> entry : linkedList)
                {
                    set.add(entry);
                }
            }
        }
        return set;
    }

    @Override
    public V get(K key)
    {
        int linkedIndex = hash(key.hashCode());
        if (table[linkedIndex] != null)
        {
            MyLinkedList<MyEntry<K, V>> linkedList = table[linkedIndex];
            for (MyEntry<K, V> entry : linkedList)
            {
                if (entry.getKey().equals(key))
                    return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public MySet<K> keySet()
    {
        MySet<K> set = new MyHashSet<>();
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                MyLinkedList<MyEntry<K, V>> linkedList = table[i];
                for (MyEntry<K, V> entry : linkedList)
                {
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }

    @Override
    public MyCollection<V> values()
    {
        MyCollection<V> collection = new MyArrayList<>();
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null)
            {
                MyLinkedList<MyEntry<K, V>> linkedList = table[i];
                for (MyEntry<K, V> entry : linkedList)
                {
                    collection.add(entry.getValue());
                }
            }
        }
        return collection;
    }

    @Override
    public V put(K key, V value)
    {
        if (get(key) != null)
        {
            int linkedIndex = hash(key.hashCode());
            MyLinkedList<MyEntry<K, V>> linkedList = table[linkedIndex];
            for (MyEntry<K, V> entry : linkedList)
            {
                if (entry.getKey().equals(key))
                {
                    V temp = entry.getValue();
                    entry.setValue(value);
                    return temp;
                }
            }
        }
        if (size >= capacity * loadFactorThreshold)
        {
            if (capacity == MAXIMUM__CAPACITY) throw new RuntimeException("已经超过了最大容量");
            rehash();
        }
        int linkedIndex = hash(key.hashCode());
        if (table[linkedIndex] == null)
        {
            table[linkedIndex] = new MyLinkedList<>();
        }
        table[linkedIndex].add(new MyEntry<>(key, value));
        size++;
        return value;
    }

    @Override
    public void remove(K key)
    {
        if (containsKey(key))
        {
            int linkedIndex = hash(key.hashCode());
            if (table[linkedIndex] != null)
            {
                MyLinkedList<MyEntry<K, V>> linkedList = table[linkedIndex];
                for (MyEntry<K, V> entry : linkedList)
                {
                    if (entry.getKey().equals(key))
                    {
                        linkedList.remove(key);
                        size--;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int size()
    {
        return size;
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

    /**
     * 扩容方法
     */
    private void rehash()
    {
        MySet<MyEntry<K, V>> set = entrySet();
        capacity <<= 1;
        table = new MyLinkedList[capacity];
        size = 0;
        for (MyEntry<K, V> entry : set)
        {
            put(entry.getKey(), entry.getValue());
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < capacity; i++)
        {
            if (table[i] != null && table[i].size() > 0)
            {
                for (MyEntry<K, V> entry : table[i])
                {
                    sb.append(entry + ",");
                }
            }
        }
        if (sb.length() > 1)
            sb.delete(sb.length() - 1, sb.length());
        sb.append("]");
        return sb.toString();
    }
}
