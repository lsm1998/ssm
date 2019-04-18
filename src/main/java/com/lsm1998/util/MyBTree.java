package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2018/12/22-15:25
 * @说明：自定义实现二叉树（使用循环的方式，递归较耗性能）
 */
public class MyBTree<E extends Comparable<? super E>>
{
    private Node<E> root;
    private int size = 0;

    public MyBTree()
    {
        this.root = null;
    }

    public MyBTree(E root)
    {
        this.root = new Node<>(root, null, null);
    }

    /**
     * 新增一个节点
     *
     * @param data
     */
    public void insert(E data)
    {
        if (root == null)
        {
            root = new Node<>(data, null, null);
            size++;
            return;
        }
        Node<E> temp = root;
        while (true)
        {
            if (temp.data.compareTo(data) > 0)
            {
                if (temp.left == null)
                {
                    temp.left = new Node<>(data, null, null);
                    size++;
                    break;
                } else
                {
                    temp = temp.left;
                }
            } else if (temp.data.compareTo(data) < 0)
            {
                if (temp.rigth == null)
                {
                    temp.rigth = new Node<>(data, null, null);
                    size++;
                    break;
                } else
                {
                    temp = temp.rigth;
                }
            } else
            {
                System.out.println("重复值：temp=" + temp.data);
                break;
            }
        }
    }

    public E getMin()
    {

        return getMin(root);
    }

    private E getMin(Node<E> node)
    {
        if (size > 0)
        {
            Node<E> temp = node;
            while (temp.left != null)
            {
                temp = temp.left;
            }
            return temp.data;
        }
        return null;
    }

    public E getMax()
    {
        return getMax(root);
    }

    private E getMax(Node<E> node)
    {
        if (size > 0)
        {
            Node<E> temp = node;
            while (temp.rigth != null)
            {
                temp = temp.rigth;
            }
            return temp.data;
        }
        return null;
    }

    public E getRoot()
    {
        return root.data;
    }

    public boolean findData(E data)
    {
        if (size > 0)
        {
            Node<E> temp = root;
            while (temp != null)
            {
                if (temp.data.compareTo(data) < 0)
                {
                    temp = temp.rigth;
                } else if (temp.data.compareTo(data) > 0)
                {
                    temp = temp.left;
                } else
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean remove(E data)
    {
        if (findData(data))
        {
            size--;
            // 删除的是根节点
            if (root.data.compareTo(data) == 0)
            {
                if (root.left != null)
                {
                    Node<E> temp = root.left;
                    root.left.rigth = root.rigth;
                    root = temp;
                } else
                {
                    root = root.rigth;
                }
                return true;
            }
            // 保存父节点
            Node<E> superNode = root;
            Node<E> temp = root;
            while (temp != null)
            {
                if (temp.data.compareTo(data) < 0)
                {
                    superNode = temp;
                    temp = temp.rigth;
                } else if (temp.data.compareTo(data) > 0)
                {
                    superNode = temp;
                    temp = temp.left;
                } else
                {
                    String flag = "left";
                    // 确定删除节点是其父节点的左子节点还是右子节点
                    if (superNode.left == null)
                    {
                        flag = "rigth";
                    }else if(superNode.rigth == null)
                    {
                        flag = "left";
                    }else
                    {
                        if(temp.data.compareTo(superNode.rigth.data) == 0)
                        {
                            flag = "rigth";
                        }
                    }
                    // 既有左子节点，又有右子节点，用小的节点顶替删除节点
                    if (temp.left != null && temp.rigth != null)
                    {
                        if (flag.equals("left"))
                        {
                            superNode.left = temp.rigth;
                            temp.rigth.left = temp.left;
                        } else
                        {
                            superNode.rigth = temp.left;
                            temp.left.rigth = temp.rigth;
                        }
                        temp.left = null;
                        temp.rigth = null;
                    } else
                    {
                        if (flag.equals("left"))
                        {
                            if (temp.left != null)
                            {
                                superNode.left = temp.left;
                                temp.left = null;
                            } else if (temp.rigth != null)
                            {
                                superNode.left = temp.rigth;
                                temp.rigth = null;
                            } else
                            {
                                superNode.left = null;
                            }
                        } else
                        {
                            if (temp.left != null)
                            {
                                superNode.rigth = temp.left;
                                temp.left = null;
                            } else if (temp.rigth != null)
                            {
                                superNode.rigth = temp.rigth;
                                temp.rigth = null;
                            } else
                            {
                                superNode.rigth = null;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        System.out.println("节点不存在：" + data);
        return false;
    }

    public int size()
    {
        return size;
    }

    public void disPlay()
    {
        if (root != null)
            disPlay(root);
    }

    private void disPlay(Node<E> root)
    {
        if (root.left != null)
            disPlay(root.left);
        System.out.println(root.data);
        if (root.rigth != null)
            disPlay(root.rigth);
    }

    private class Node<E>
    {
        E data;
        Node<E> left;
        Node<E> rigth;

        public Node(E data, Node<E> left, Node<E> rigth)
        {
            this.data = data;
            this.left = left;
            this.rigth = rigth;
        }
    }
}
