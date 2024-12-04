package org.example;

import javax.swing.*;
import java.util.*;

/**
 * A* 算法实现类，用于查找最短路径
 */
public class AStarPathfinder implements Runnable {
    private GridPanel gridPanel;
    private JLabel statusLabel;
    private JLabel visitedLabel;      // 显示已访问格子数
    private JLabel pathLengthLabel;   // 显示最短路径格子数
    private JButton startButton;
    private JButton resetButton;
    private int visitedCount = 0;     // 已访问格子计数器
    private int pathLength = 0;       // 最短路径格子计数器

    /**
     * 构造方法
     *
     * @param gridPanel        网格面板
     * @param statusLabel      状态标签
     * @param visitedLabel     已访问格子数标签
     * @param pathLengthLabel  最短路径格子数标签
     * @param startButton      开始按钮
     * @param resetButton      重置按钮
     */
    public AStarPathfinder(GridPanel gridPanel, JLabel statusLabel, JLabel visitedLabel, JLabel pathLengthLabel, JButton startButton, JButton resetButton) {
        this.gridPanel = gridPanel;
        this.statusLabel = statusLabel;
        this.visitedLabel = visitedLabel;
        this.pathLengthLabel = pathLengthLabel;
        this.startButton = startButton;
        this.resetButton = resetButton;
    }

    @Override
    public void run() {
        Cell start = gridPanel.getStartCell();
        Cell end = gridPanel.getEndCell();

        if (start == null || end == null) {
            JOptionPane.showMessageDialog(gridPanel, "请设置起点和终点。", "错误", JOptionPane.ERROR_MESSAGE);
            enableButtons();
            return;
        }

        statusLabel.setText("状态: 搜索中...");

        // 优先队列，按照 F 值排序
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Cell, Cell> cameFrom = new HashMap<>();
        Map<Cell, Integer> gScore = new HashMap<>();

        Node startNode = new Node(start, 0, heuristic(start, end));
        openSet.add(startNode);
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Cell current = currentNode.cell;

            if (current == end) {
                reconstructPath(cameFrom, current);
                statusLabel.setText("状态: 路径已找到！");
                enableButtons();
                return;
            }

            if (!current.isVisited()) { // 确保每个格子只计数一次
                current.setVisited(true);
                visitedCount++;
                updateVisitedLabel(visitedCount);
            }

            for (Cell neighbor : getNeighbors(current)) {
                if (neighbor.isObstacle()) continue;

                int tentativeG = gScore.get(current) + 1;

                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    int fScore = tentativeG + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, tentativeG, fScore));

                    if (!neighbor.isEnd() && !neighbor.isVisited()) { // 仅在未访问且非终点时计数
                        neighbor.setVisited(true);
                        visitedCount++;
                        updateVisitedLabel(visitedCount);
                    }
                }
            }

            try {
                Thread.sleep(50); // 控制动画速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        statusLabel.setText("状态: 未找到路径。");
        enableButtons();
    }

    /**
     * 回溯路径并更新路径长度
     *
     * @param cameFrom 回溯映射
     * @param current  当前格子
     */
    private void reconstructPath(Map<Cell, Cell> cameFrom, Cell current) {
        pathLength = 0; // 重置路径长度计数
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            if (!current.isStart()) {
                current.setPath(true);
                pathLength++;
                updatePathLengthLabel(pathLength);
                try {
                    Thread.sleep(50); // 控制动画速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 启发式函数，使用曼哈顿距离
     *
     * @param a 格子 A
     * @param b 格子 B
     * @return 曼哈顿距离
     */
    private int heuristic(Cell a, Cell b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }

    /**
     * 获取邻居格子
     *
     * @param cell 当前格子
     * @return 邻居格子列表
     */
    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();
        Cell[][] grid = gridPanel.getGrid();

        // 上
        if (row > 0) neighbors.add(grid[row - 1][col]);
        // 下
        if (row < grid.length - 1) neighbors.add(grid[row + 1][col]);
        // 左
        if (col > 0) neighbors.add(grid[row][col - 1]);
        // 右
        if (col < grid[0].length - 1) neighbors.add(grid[row][col + 1]);

        return neighbors;
    }

    /**
     * 启用开始和重置按钮
     */
    private void enableButtons() {
        SwingUtilities.invokeLater(() -> {
            startButton.setEnabled(true);
            resetButton.setEnabled(true);
        });
    }

    /**
     * 更新已访问格子数标签
     *
     * @param count 已访问格子数
     */
    private void updateVisitedLabel(int count) {
        SwingUtilities.invokeLater(() -> {
            visitedLabel.setText("已访问格子数: " + count);
        });
    }

    /**
     * 更新最短路径格子数标签
     *
     * @param count 最短路径格子数
     */
    private void updatePathLengthLabel(int count) {
        SwingUtilities.invokeLater(() -> {
            pathLengthLabel.setText("最短路径格子数: " + count);
        });
    }

    /**
     * 节点类，包含格子及其 G 和 F 值
     */
    private class Node {
        Cell cell;
        int g; // 从起点到当前节点的实际代价
        int f; // 总估价（F = G + H）

        public Node(Cell cell, int g, int f) {
            this.cell = cell;
            this.g = g;
            this.f = f;
        }
    }
}
