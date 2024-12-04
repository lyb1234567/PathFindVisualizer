package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DijkstraVisualization extends JPanel {

    // 图中的节点和边
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    // 路径结果
    private final Map<Node, Double> shortestPaths = new HashMap<>();
    private final Map<Node, Node> previousNodes = new HashMap<>();

    // 定义节点类
    static class Node {
        int x, y;
        String name;

        public Node(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return name;
        }
    }

    // 定义边类
    static class Edge {
        Node from, to;
        double weight;

        public Edge(Node from, Node to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    // 构造函数，设置初始节点和边
    public DijkstraVisualization() {
        Node nodeA = new Node("A", 50, 50);
        Node nodeB = new Node("B", 200, 50);
        Node nodeC = new Node("C", 200, 200);
        Node nodeD = new Node("D", 50, 200);

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);

        edges.add(new Edge(nodeA, nodeB, 1));
        edges.add(new Edge(nodeB, nodeC, 3));
        edges.add(new Edge(nodeC, nodeD, 1));
        edges.add(new Edge(nodeD, nodeA, 4));
        edges.add(new Edge(nodeA, nodeC, 2));

        // 初始化最短路径和前驱节点
        for (Node node : nodes) {
            shortestPaths.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }
    }

    // Dijkstra 算法计算最短路径
    public void dijkstra(Node start) {
        shortestPaths.put(start, 0.0);

        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(shortestPaths::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // 更新相邻节点的最短路径
            for (Edge edge : edges) {
                if (edge.from == current) {
                    Node neighbor = edge.to;
                    double newDist = shortestPaths.get(current) + edge.weight;
                    if (newDist < shortestPaths.get(neighbor)) {
                        shortestPaths.put(neighbor, newDist);
                        previousNodes.put(neighbor, current);
                        pq.add(neighbor);
                    }
                }
            }
        }
    }

    // 绘制图形界面
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 绘制节点
        for (Node node : nodes) {
            g2d.setColor(Color.RED);
            g2d.fillOval(node.x - 15, node.y - 15, 30, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawString(node.name, node.x - 10, node.y - 20);
        }

        // 绘制边
        for (Edge edge : edges) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);
            g2d.drawString(String.valueOf(edge.weight),
                    (edge.from.x + edge.to.x) / 2,
                    (edge.from.y + edge.to.y) / 2);
        }

        // 绘制最短路径
        Node current = nodes.get(0); // 默认从第一个节点开始
        while (previousNodes.get(current) != null) {
            Node prev = previousNodes.get(current);
            g2d.setColor(Color.BLUE);
            g2d.drawLine(current.x, current.y, prev.x, prev.y);
            current = prev;
        }
    }

    // 主方法：创建界面并运行算法
    public static void main(String[] args) {
        DijkstraVisualization panel = new DijkstraVisualization();
        JFrame frame = new JFrame("Dijkstra Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel);
        frame.setVisible(true);

        // 计算最短路径
        Node startNode = panel.nodes.get(0); // 从节点 A 开始
        panel.dijkstra(startNode);

        // 触发重新绘制图形
        panel.repaint();
    }
}
