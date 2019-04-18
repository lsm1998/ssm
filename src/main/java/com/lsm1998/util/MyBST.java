package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间:2019/1/1-12:07
 * @说明：
 */
public class MyBST<E extends Comparable<E>> implements MyTree<E>
{
    protected TreeNode<E> root;
    protected int size;

    public MyBST()
    {

    }

    public MyBST(E[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            insert(arr[i]);
        }
    }

    @Override
    public boolean isEmpty()
    {
        return getSize()==0;
    }

    @Override
    public boolean search(E e)
    {
        TreeNode<E> temp = root;
        while (temp != null)
        {
            if (e.compareTo(temp.element) < 0)
            {
                temp = temp.left;
            } else if (e.compareTo(temp.element) > 0)
            {
                temp = temp.right;
            } else
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean insert(E e)
    {
        if (root == null)
        {
            root = new TreeNode<>(e);
        } else
        {
            TreeNode<E> temp = root;
            while (temp != null)
            {
                if (e.compareTo(temp.element) < 0)
                {
                    if (temp.left != null)
                        temp = temp.left;
                    else
                        temp.left=new TreeNode<>(e);
                } else if (e.compareTo(temp.element) > 0)
                {
                    if (temp.right != null)
                        temp = temp.right;
                    else
                        temp.right=new TreeNode<>(e);
                } else
                {
                    return false;
                }
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean delete(E e)
    {
        TreeNode<E> parent=null;
        TreeNode<E> temp=root;
        while (temp!=null)
        {
            if(e.compareTo(temp.element)<0)
            {
                parent=temp;
                temp=temp.left;
            }else if(e.compareTo(temp.element)>0)
            {
                parent=temp;
                temp=temp.right;
            }else
            {
                break;
            }
        }
        if(temp==null)
        {
            return false;
        }
        // 删除节点是否存在左节点
        // 如果不存在，则把父节点的左（右）子节点赋给自己的右节点
        // 如果存在，则
        if(temp.left==null)
        {
            // 是否删除的是根节点
            if(parent==null)
            {
                root=temp.right;
            }else
            {
                // 判断自己是父节点的左子节点还是右子节点
                if(e.compareTo(parent.element)<0)
                    parent.left=temp.right;
                else
                    parent.right=temp.right;
            }
        }else
        {
            TreeNode<E> parentOfRigthMost=temp;
            TreeNode<E> rigthMost=temp.left;
            while (rigthMost.right!=null)
            {
                parentOfRigthMost=rigthMost;
                rigthMost=rigthMost.right;
            }
            temp.element=rigthMost.element;
            if(parentOfRigthMost.right==rigthMost)
                parentOfRigthMost.right=rigthMost.left;
            else
                parentOfRigthMost.left=rigthMost.left;
        }
        size--;
        return true;
    }

    @Override
    public void inorder()
    {
        if (root != null)
            inorder(root);
    }

    private void inorder(TreeNode<E> root)
    {
        if (root.left != null)
            inorder(root.left);
        System.out.println(root.element);
        if (root.right != null)
            inorder(root.right);
    }

    @Override
    public void postorder()
    {
        if (root != null)
            postorder(root);
    }

    private void postorder(TreeNode<E> root)
    {
        if (root.left != null)
            inorder(root.left);
        if (root.right != null)
            inorder(root.right);
        System.out.println(root.element);
    }

    @Override
    public void preorder()
    {
        if (root != null)
            preorder(root);
    }

    private void preorder(TreeNode<E> root)
    {
        System.out.println(root.element);
        if (root.left != null)
            inorder(root.left);
        if (root.right != null)
            inorder(root.right);
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public E getMax()
    {
        if(root!=null)
        {
            return getMax(root);
        }
        return null;
    }

    private E getMax(TreeNode<E> node)
    {
        if(node.right!=null)
            return getMax(node.right);
        else
            return node.element;
    }

    @Override
    public E getMin()
    {
        if(root!=null)
        {
            return getMin(root);
        }
        return null;
    }

    private E getMin(TreeNode<E> node)
    {
        if(node.left!=null)
            return getMax(node.left);
        else
            return node.element;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new InorderIterator();
    }

    public static class TreeNode<E extends Comparable<E>>
    {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E element)
        {
            this.element = element;
        }
    }

    public MyList<TreeNode<E>> path(E e)
    {
        MyList<TreeNode<E>> list=new MyArrayList<>();
        TreeNode<E> temp=root;
        while (temp!=null)
        {
            list.add(temp);
            if(e.compareTo(temp.element)<0)
            {
                temp=temp.left;
            }else if(e.compareTo(temp.element)>0)
            {
                temp=temp.right;
            }else
            {
                break;
            }
        }
        return list;
    }

    public MyList<E> toList()
    {
        MyList<E> list=new MyArrayList<>();
        toList(root,list);
        return list;
    }

    private void toList(TreeNode<E> node,MyList<E> list)
    {
        if(node.left!=null)
            toList(node.left,list);
        list.add(node.element);
        if(node.right!=null)
            toList(node.right,list);
    }

    private class InorderIterator implements Iterator<E>
    {
        private MyList<E> list=new MyArrayList<>();
        private int current=0;

        public InorderIterator()
        {
            inorder(root);
        }

        private void inorder()
        {
            inorder(root);
        }

        private void inorder(TreeNode<E> root)
        {
            if(root==null)
                return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override
        public boolean hasNext()
        {
            return current<list.size();
        }

        @Override
        public E next()
        {
            return list.get(current++);
        }

        @Override
        public void remove()
        {
            delete(list.get(current));
            list.clear();
            inorder();
        }
    }

    @Override
    public void clear()
    {
        size=0;
        root=null;
    }
}
