package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间:2019/1/1-16:19
 * @说明：
 */
public class MyAVLTree<E extends Comparable<E>>  extends MyBST<E>
{
    public MyAVLTree()
    {
    }

    public MyAVLTree(E[] arr)
    {
        super(arr);
    }

    public boolean insert(E e)
    {
        boolean result=super.insert(e);
        if(result)
            balancePath(e);
        else
            return false;
        return true;
    }

    private void balancePath(E e)
    {
        MyList<TreeNode<E>> path=path(e);
        for (int i = path.size()-1; i >=0 ; i--)
        {
            AVLTreeNode<E> newNode=new AVLTreeNode<>(path.get(i).element);
            //AVLTreeNode<E> A=(AVLTreeNode<E>)path.get(i);
            AVLTreeNode<E> A=newNode;
            // 节点高度赋值
            updateHeight(A);
            // AVLTreeNode<E> parentOfA=(A.element==root.element)?null:(AVLTreeNode<E>)(path.get(i-1));
            AVLTreeNode<E> parentOfA=(A.element==root.element)?null:new AVLTreeNode<>(path.get(i-1).element);
            switch (balanceFactor(A))
            {
                case -2:
                    if(balanceFactor((AVLTreeNode<E>)A.left)<=0)
                        balanceLL(A,parentOfA);
                    else
                        balanceLR(A,parentOfA);
                    break;
                case 2:
                    if(balanceFactor((AVLTreeNode<E>)A.left)>=0)
                        balanceRR(A,parentOfA);
                    else
                        balanceRL(A,parentOfA);
                    break;
            }
        }
    }

    private void updateHeight(AVLTreeNode<E> node)
    {
        if(node.left==null&&node.right==null)
            node.height=0;
        else if(node.left==null)
            node.height=1+((AVLTreeNode<E>)node.right).height;
        else if(node.right==null)
            node.height=1+((AVLTreeNode<E>)node.left).height;
        else
            node.height=1+Math.max(((AVLTreeNode<E>)node.right).height,((AVLTreeNode<E>)node.left).height);
    }

    private int balanceFactor(AVLTreeNode<E> node)
    {
        if(node.right==null)
            return -node.height;
        else if(node.left==null)
            return +node.height;
        else
            return  ((AVLTreeNode<E>)node.right).height-((AVLTreeNode<E>)node.left).height;
    }

    private void balanceLL(TreeNode<E> A,TreeNode<E> parentOfA)
    {
        TreeNode<E> B=A.left;
        if(A==root)
        {
            root=B;
        }else
        {
            if(parentOfA.left==A)
                parentOfA.left=B;
            else
                parentOfA.right=B;
        }
        A.left=B.right;
        B.right=A;
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
    }

    private void balanceLR(TreeNode<E> A,TreeNode<E> parentOfA)
    {
        TreeNode<E> B=A.left;
        TreeNode<E> C=B.right;
        if(A==root)
        {
            root=C;
        }else
        {
            if(parentOfA.left==A)
                parentOfA.left=C;
            else
                parentOfA.right=C;
        }
        A.left=C.right;
        B.right=C.left;
        C.left=B;
        C.right=A;
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
        updateHeight((AVLTreeNode<E>)C);
    }

    private void balanceRR(TreeNode<E> A,TreeNode<E> parentOfA)
    {
        TreeNode<E> B=A.right;
        if(A==root)
        {
            root=B;
        }else
        {
            if(parentOfA.left==A)
                parentOfA.left=B;
            else
                parentOfA.right=B;
        }
        A.right=B.left;
        B.left=A;
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
    }

    private void balanceRL(TreeNode<E> A,TreeNode<E> parentOfA)
    {
        TreeNode<E> B=A.right;
        TreeNode<E> C=B.left;
        if(A==root)
        {
            root=C;
        }else
        {
            if(parentOfA.left==A)
                parentOfA.left=C;
            else
                parentOfA.right=C;
        }
        A.right=C.left;
        B.left=C.right;
        C.right=B;
        C.left=A;
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
        updateHeight((AVLTreeNode<E>)C);
    }

    public boolean delete(E e)
    {
        if(root!=null)
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
            if(temp!=null)
            {
                if(temp.left==null)
                {
                    if(parent==null)
                    {
                        root=temp.right;
                    }else
                    {
                        if(e.compareTo(temp.element)<0)
                            parent.left=temp.right;
                        else
                            parent.right=temp.right;
                        balancePath(parent.element);
                    }
                }else
                {
                    TreeNode<E> parentOfRightMost=temp;
                    TreeNode<E> rightMost=temp.left;
                    while (rightMost.right!=null)
                    {
                        parentOfRightMost=rightMost;
                        rightMost=rightMost.right;
                    }
                    temp.element=rightMost.element;
                    if(parentOfRightMost.right==rightMost)
                        parentOfRightMost.right=rightMost.left;
                    else
                        parentOfRightMost.left=rightMost.left;
                    balancePath(parentOfRightMost.element);
                }
                size--;
                return true;
            }
        }
        return false;
    }

    private static class AVLTreeNode<E extends Comparable<E>> extends TreeNode<E>
    {
        protected int height=0;

        public AVLTreeNode(E e)
        {
            super(e);
        }
    }
}
