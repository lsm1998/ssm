package com.lsm1998.util.chart;

/**
 * @作者：刘时明
 * @时间:2019/1/1-21:50
 * @说明：
 */
public class TestGraph
{
    public static void main(String[] args)
    {
        String[] vertices = {
                "长沙", "怀化", "北京",
                "上海", "南京", "东京",
                "纽约", "伦敦", "马尔代夫",
                "香港", "澳门", "广州"
        };
        int[][] edges = {
                {0, 1}, {0, 3}, {0, 5},
                {1, 0}, {1, 2}, {1, 3},
                {2, 1}, {2, 3}, {2, 4}, {2, 10},
                {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
                {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
                {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
                {6, 5}, {6, 7},
                {7, 4}, {7, 5}, {7, 6}, {7, 8},
                {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
                {9, 8}, {9, 11},
                {10, 2}, {10, 4}, {10, 8}, {10, 11},
                {11, 8}, {11, 8}, {11, 10}
        };

        Graph<String> graph1 = new UnweightedGraph<>(vertices, edges);
        System.out.println("顶点数量："+graph1.getSize());

        System.out.println("城市2=" + graph1.getVertex(1));

        System.out.println(graph1.getIndex("广州"));
        System.out.println(graph1.getIndex("广州2"));

        graph1.printEdges();
    }
}
