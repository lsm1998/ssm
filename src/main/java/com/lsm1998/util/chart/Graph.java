package com.lsm1998.util.chart;

import com.lsm1998.util.MyList;

/**
 * @作者：刘时明
 * @时间:2019/1/1-22:08
 * @说明：
 */
public interface Graph<V>
{
    int getSize();

    MyList<V> getVertices();

    V getVertex(int index);

    int getIndex(V v);

    MyList<Integer> getNeighbors(int index);

    int getDegree(int v);

    void printEdges();

    void clear();

    boolean addVertex(V vertex);

    boolean addEdge(int u, int v);

    AbstractGraph<V>.Tree dfs(int v);

    AbstractGraph<V>.Tree bfs(int v);
}
