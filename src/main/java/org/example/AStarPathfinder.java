package org.example;

import javax.swing.*;
import java.util.*;

public class AStarPathfinder implements Runnable {
    private GridPanel gridPanel;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton resetButton;

    public AStarPathfinder(GridPanel gridPanel, JLabel statusLabel, JButton startButton, JButton resetButton) {
        this.gridPanel = gridPanel;
        this.statusLabel = statusLabel;
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

            current.setVisited(true);

            for (Cell neighbor : getNeighbors(current)) {
                if (neighbor.isObstacle()) continue;

                int tentativeG = gScore.get(current) + 1;

                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    int fScore = tentativeG + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, tentativeG, fScore));

                    if (!neighbor.isEnd()) {
                        neighbor.setVisited(true);
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

    private void reconstructPath(Map<Cell, Cell> cameFrom, Cell current) {
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            if (!current.isStart()) {
                current.setPath(true);
            }
            try {
                Thread.sleep(50); // 控制动画速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int heuristic(Cell a, Cell b) {
        // 使用曼哈顿距离作为启发式函数
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }

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

    private void enableButtons() {
        SwingUtilities.invokeLater(() -> {
            startButton.setEnabled(true);
            resetButton.setEnabled(true);
        });
    }

    private class Node {
        Cell cell;
        int g; // 代价
        int f; // 总估价

        public Node(Cell cell, int g, int f) {
            this.cell = cell;
            this.g = g;
            this.f = f;
        }
    }
}
