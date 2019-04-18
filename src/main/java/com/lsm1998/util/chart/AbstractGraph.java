package com.lsm1998.util.chart;

import com.lsm1998.util.MyArrayList;
import com.lsm1998.util.MyLinkedList;
import com.lsm1998.util.MyList;

import java.util.LinkedList;
import java.util.List;

/**
 * @作者：刘时明
 * @时间:2019/1/1-22:23
 * @说明：
 */
public abstract class AbstractGraph<V> implements Graph<V>
{
    protected MyList<V> vertices = new MyArrayList<>();
    protected MyList<MyList<Edge>> neighbors = new MyArrayList<>();

    protected AbstractGraph()
    {

    }

    protected AbstractGraph(V[] vertices, int[][] edges)
    {
        for (int i = 0; i < vertices.length; i++)
        {
            addVertex(vertices[i]);
        }
        createAdjacencyLists(edges, vertices.length);
    }

    protected AbstractGraph(MyList<V> vertices, MyList<Edge> edges)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            addVertex(vertices.get(i));
        }
        createAdjacencyLists(edges, vertices.size());
    }

    protected AbstractGraph(MyList<Edge> edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            addVertex((V)Integer.valueOf(i));
        }
        createAdjacencyLists(edges, vertices.size());
    }

    protected AbstractGraph(int[][] edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            addVertex((V)Integer.valueOf(i));
        }
        createAdjacencyLists(edges, vertices.size());
    }

    private void createAdjacencyLists(MyList<Edge> edges, int length)
    {
        for (Edge e : edges)
        {
            addEdge(e.u, e.v);
        }
    }


    private void createAdjacencyLists(int[][] edges, int length)
    {
        for (int i = 0; i < edges.length; i++)
        {
            addEdge(edges[i][0], edges[i][1]);
        }
    }

    @Override
    public boolean addVertex(V vertex)
    {
        if (!vertices.contains(vertex))
        {
            vertices.add(vertex);
            neighbors.add(new MyArrayList<>());
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean addEdge(int u, int v)
    {
        return addEdge(new Edge(u, v));
    }

    protected boolean addEdge(Edge e)
    {
        if (e.u < 0 || e.u > getSize() - 1) throw new IllegalArgumentException("找不到索引：" + e.u);
        if (e.v < 0 || e.v > getSize() - 1) throw new IllegalArgumentException("找不到索引：" + e.u);
        if (!neighbors.get(e.u).contains(e))
        {
            neighbors.get(e.u).add(e);
            return true;
        }
        return false;
    }

    @Override
    public int getSize()
    {
        return vertices.size();
    }

    @Override
    public int getIndex(V v)
    {
        return vertices.indexOf(v);
    }

    @Override
    public void clear()
    {
        vertices.clear();
        neighbors.clear();
    }

    public MyList<V> getVertices()
    {
        return vertices;
    }

    @Override
    public MyList<Integer> getNeighbors(int index)
    {
        MyList<Integer> result = new MyArrayList<>();
        for (Edge e : neighbors.get(index))
        {
            result.add(e.v);
        }
        return result;
    }

    @Override
    public int getDegree(int v)
    {
        return neighbors.get(v).size();
    }

    @Override
    public void printEdges()
    {
        for (int u = 0; u < neighbors.size(); u++)
        {
            System.out.println(getVertex(u) + "(" + u + "):");
            for (Edge e : neighbors.get(u))
            {
                System.out.println("(" + getVertex(e.u) + "," + getVertex(e.v) + ")");
            }
            System.out.println();
        }
    }

    @Override
    public V getVertex(int index)
    {
        return vertices.get(index);
    }

    public static class Edge
    {
        public int u;
        public int v;

        public Edge(int u, int v)
        {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object obj)
        {
            return u == ((Edge) obj).u && v == ((Edge) obj).v;
        }
    }

    public class Tree
    {
        private int root;
        private int[] parent;
        private MyList<Integer> searchOrder;

        public Tree(int root, int[] parent, MyList<Integer> searchOrder)
        {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        public int getRoot()
        {
            return root;
        }

        public int[] getParent()
        {
            return parent;
        }

        public MyList<Integer> getSearchOrder()
        {
            return searchOrder;
        }

        public int getNumberOfVerticesFound()
        {
            return searchOrder.size();
        }

        public MyList<V> getPath(int index)
        {
            MyArrayList<V> path = new MyArrayList<>();
            do
            {
                path.add(vertices.get(index));
                index = parent[index];
            } while (index != -1);
            return path;
        }

        public void printPath(int index)
        {
            MyList<V> path = getPath(index);
            System.out.println("a path from " + vertices.get(root) + " to " + vertices.get(index) + " : ");
            for (int i = path.size() - 1; i >= 0; i--)
            {
                System.out.println(path.get(i) + " ");
            }
        }

        public void printTree()
        {
            System.out.println("Root is:" + vertices.get(root));
            System.out.println("Edges:");
            for (int i = 0; i < parent.length; i++)
            {
                if (parent[i] != -1)
                {
                    System.out.println("(" + vertices.get(parent[i]) + "," + vertices.get(i) + ")");
                }
            }
            System.out.println();
        }
    }

    @Override
    public Tree dfs(int v)
    {
        MyList<Integer> searchOrder = new MyArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
        {
            parent[i] = -1;
        }
        boolean[] isVisited = new boolean[vertices.size()];
        dfs(v, parent, searchOrder, isVisited);
        return new Tree(v, parent, searchOrder);
    }

    private void dfs(int u, int[] parent, MyList<Integer> searchOrder, boolean[] isVisited)
    {
        searchOrder.add(u);
        isVisited[u] = true;
        for (Edge e : neighbors.get(u))
        {
            if (!isVisited[e.v])
            {
                parent[e.v] = u;
                dfs(e.v, parent, searchOrder, isVisited);
            }
        }
    }

    @Override
    public Tree bfs(int v)
    {
        MyList<Integer> searchOrder = new MyArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
        {
            parent[i]=-1;
        }
        LinkedList <Integer> queue=new LinkedList<>();
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v);
        isVisited[v]=true;
        while (!queue.isEmpty())
        {
            int u=queue.poll();
            searchOrder.add(u);
            for (Edge e:neighbors.get(u))
            {
                if(!isVisited[e.v])
                {
                    queue.offer(e.v);
                    parent[e.v]=u;
                    isVisited[e.v]=true;
                }
            }
        }
        return new Tree(v, parent, searchOrder);
    }
}
