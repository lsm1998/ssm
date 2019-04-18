package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间：2019/1/5-15:52
 * @说明：
 */
public class SortUtil
{
    public static void main(String[] args)
    {
        MyList<Integer> list=new MyArrayList<>();
        list.add(3);
        list.add(5);
        list.add(8);
        list.add(4);
        list.add(7);
        list.add(0);
        System.out.println(list);
        System.out.println(selectSort(list));
    }

    /**
     * 冒泡排序
     * @param list
     * @param <E>
     * @return
     */
    public static <E extends Comparable<E>> MyList<E> bubblSort(MyList<E> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = 0; j < list.size()-i-1; j++)
            {
                if(list.get(j).compareTo(list.get(j+1))>0)
                {
                    E temp=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }
        return list;
    }

    /**
     * 插入排序
     * @param list
     * @param <E>
     * @return
     */
    public static <E extends Comparable<E>> MyList<E> insertSort(MyList<E> list)
    {
        for (int i = 0; i < list.size() - 1; i++)
        {
            int k = i;
            for (int j = i; j < list.size(); j++)
            {
                if (list.get(j).compareTo(list.get(k))<0)
                {
                    k = j;
                }
            }
            E temp = list.get(i);
            list.set(i,list.get(k));
            list.set(k,temp);
        }
        return list;
    }

    /**
     * 选择排序
     * @param list
     * @return
     */
    public static <E extends Comparable<E>> MyList<E> selectSort(MyList<E> list)
    {
        for (int i = 0; i < list.size() - 1; i++)
        {
            int k = i;
            for (int j = i; j < list.size(); j++)
            {
                if (list.get(j).compareTo(list.get(k))<0)
                {
                    k = j;
                }
                E temp = list.get(i);
                list.set(i,list.get(k));
                list.set(k,temp);
            }
        }
        return list;
    }

    public static <E extends Comparable<E>> MyList<E> mergeSort(MyList<E> list)
    {
        if(list.size()>1)
        {

        }
        return list;
    }

    public static <E extends Comparable<E>> MyList<E> mergeSort(MyList<E> leftList,MyList<E> rightList)
    {
        MyList<E> list=new MyArrayList<>();
        int index1 = 0;
        int index2 = 0;
        int index3 = 0;
        while (index1 < leftList.size() && index2 < rightList.size())
        {
            if(leftList.get(index1).compareTo(rightList.get(index2))<0)
            {
            }
            //temp[index3++] = arr1[index1] < arr2[index2] ? arr1[index1++] : arr2[index2++];
        }
        return list;
    }
}
