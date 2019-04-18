package com.lsm1998.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间:2018/12/23-1:01
 * @说明：
 */
public class MyLinkedList<E> implements MyList<E>
{
    private int size;
    /**
     * 头部
     */
    private Node<E> first;

    /**
     * 尾部
     */
    private Node<E> last;

    public MyLinkedList()
    {

    }

    public MyLinkedList(E data)
    {
        first = new Node<>(data, null, null);
        last = new Node<>(null, first, null);
        first.next = last;
    }

    public boolean addFirst(E data)
    {
        add(0, data);
        return true;
    }

    public boolean addLast(E data)
    {
        add(size, data);
        return true;
    }

    public boolean removeFirst()
    {
        if (size == 0)
        {
            return false;
        } else
        {
            remove(0);
            return true;
        }
    }

    public boolean removeLast()
    {
        if (size == 0)
        {
            return false;
        } else
        {
            remove(size - 1);
            return true;
        }
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
        return false;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new MyLinkedListIterator();
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
    public boolean add(E e)
    {
        return add(size, e);
    }

    @Override
    public boolean remove(Object e)
    {
        return false;
    }

    @Override
    public void clear()
    {
        first = new Node<>(null, null, null);
        last = first;
        size = 0;
    }

    @Override
    public E get(int index)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        int count = 0;
        Node<E> temp;
        if (index <= size / 2)
        {
            temp = first;
            while (++count <= index)
            {
                temp = temp.next;
            }
        } else
        {
            temp = last;
            while (++count < size - index)
            {
                temp = temp.prev;
            }
        }
        return temp.data;
    }

    @Override
    public E set(int index, E element)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        int count = 0;
        Node<E> temp;
        if (index <= size / 2)
        {
            temp = first;
            while (++count <= index)
            {
                temp = temp.next;
            }
        } else
        {
            temp = last;
            while (++count < size - index)
            {
                temp = temp.prev;
            }
        }
        temp.data = element;
        return temp.data;
    }

    @Override
    public int indexOf(Object o)
    {
        for (int i = 0; i < size; i++)
        {
            if (o.equals(get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        for (int i = size - 1; i >= 0; i--)
        {
            if (o.equals(get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(int index, E e)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        if (first == null)
        {
            first = new Node<>(e, null, null);
            last = new Node<>(null, first, null);
        } else
        {
            int count = 0;
            Node<E> temp = first;
            while (temp.next != null && ++count < index)
            {
                temp = temp.next;
            }
            if (first.next == null)
            {
                temp.prev = first;
                first.prev = null;
            }
            if (index == 0)
            {
                Node<E> head = new Node<>(e, null, null);
                head.next = first;
                first.prev = head;
                first = head;
            } else if (index == size)
            {
                temp.next = new Node<>(e, temp, null);
                last = temp.next;
            } else
            {
                temp.next = new Node<>(e, temp, temp.next);
                if (temp.next.next != null)
                {
                    temp.next.next.prev = temp.next;
                }
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("数组越界");
        }
        if (size == 1)
        {
            clear();
            return true;
        }
        if (index == 0)
        {
            first = first.next;
            first.prev.next = first;
        } else if (index == size - 1)
        {
            last.prev = last.prev.prev;
            last.prev.next = last.prev.next.next;
        } else
        {
            Node<E> temp = first;
            int count = 0;
            while (temp.next != null && ++count <= index)
            {
                temp = temp.next;
            }
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            temp.next = null;
            temp.prev = null;
        }
        size--;
        return true;
    }

    public void display()
    {
        System.out.println("头节点=" + first.data);
        System.out.println("尾节点=" + last.data);

        System.out.println("正序遍历");
        Node<E> temp = first;
        while (temp != null)
        {
            System.out.println(temp.data);
            temp = temp.next;
        }

        System.out.println("反序遍历");
        temp = last;
        while (temp != null)
        {
            System.out.println(temp.data);
            temp = temp.prev;
        }
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

    private class MyLinkedListIterator implements Iterator<E>
    {
        private int current = 0;

        @Override
        public void remove()
        {
            MyLinkedList.this.remove(--current);
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
            return get(current++);
        }
    }

    private class Node<E>
    {
        Node<E> next;
        Node<E> prev;
        E data;

        public Node(E data, Node<E> prev, Node<E> next)
        {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        this.forEach(e -> sb.append(e + ","));
        if (sb.length() > 1)
            sb.delete(sb.length() - 1, sb.length());
        return sb.append("]").toString();
    }
}
