package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2019/1/1-18:44
 * @说明：Map集合顶层接口
 */
public interface MyMap<K, V>
{
    void clear();

    boolean containsKey(K key);

    boolean containsValue(V value);

    MySet<MyEntry<K, V>> entrySet();

    V get(K key);

    boolean isEmpty();

    MySet<K> keySet();

    MyCollection<V> values();

    V put(K key, V value);

    void remove(K key);

    int size();

    class MyEntry<K, V>
    {
        private K key;
        private V value;

        public MyEntry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public void setKey(K key)
        {
            this.key = key;
        }

        public void setValue(V value)
        {
            this.value = value;
        }

        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }

        @Override
        public String toString()
        {
            return "{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
