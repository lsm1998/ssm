package com.lsm1998.util.chart;

import com.lsm1998.util.MyList;

/**
 * @作者：刘时明
 * @时间:2019/1/1-23:19
 * @说明：
 */
public class UnweightedGraph<V> extends AbstractGraph<V>
{
    public UnweightedGraph()
    {
    }

    public UnweightedGraph(V[] vertices,int[][] edges)
    {
        super(vertices,edges);
    }

    public UnweightedGraph(MyList<V> vertices, MyList<Edge> edges)
    {
        super(vertices,edges);
    }

    public UnweightedGraph(MyList<Edge> edges, int numberOfVertices)
    {
        super(edges,numberOfVertices);
    }

    public UnweightedGraph(int[][] edges, int numberOfVertices)
    {
        super(edges,numberOfVertices);
    }
}
