package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 网格面板类，负责创建和管理网格
 */
public class GridPanel extends JPanel {
    private int rows;
    private int cols;
    private Cell[][] grid;
    private Cell startCell = null;
    private Cell endCell = null;

    /**
     * 构造方法
     *
     * @param rows 行数
     * @param cols 列数
     */
    public GridPanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setLayout(new GridLayout(rows, cols));
        grid = new Cell[rows][cols];
        initializeGrid();

        // 添加鼠标监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 处理格子点击事件
                Component clickedComponent = getComponentAt(e.getPoint());
                if (clickedComponent instanceof Cell) {
                    Cell cell = (Cell) clickedComponent;
                    if (SwingUtilities.isLeftMouseButton(e) && !e.isShiftDown()) {
                        // 设置起点
                        if (startCell != null) {
                            startCell.setStart(false);
                        }
                        startCell = cell;
                        cell.setStart(true);
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        // 设置终点
                        if (endCell != null) {
                            endCell.setEnd(false);
                        }
                        endCell = cell;
                        cell.setEnd(true);
                    } else if (SwingUtilities.isLeftMouseButton(e) && e.isShiftDown()) {
                        // 使用 Shift + 左键点击来添加或移除障碍物
                        cell.toggleObstacle();
                    }
                }
            }
        });
    }

    /**
     * 初始化网格
     */
    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = new Cell(row, col);
                grid[row][col] = cell;
                add(cell);
            }
        }
    }

    /**
     * 获取起点格子
     *
     * @return 起点格子
     */
    public Cell getStartCell() {
        return startCell;
    }

    /**
     * 获取终点格子
     *
     * @return 终点格子
     */
    public Cell getEndCell() {
        return endCell;
    }

    /**
     * 获取整个网格
     *
     * @return 网格二维数组
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * 重置网格
     */
    public void resetGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col].reset();
            }
        }
        startCell = null;
        endCell = null;
    }

    /**
     * 开始路径查找
     *
     * @param pathfinder 路径查找算法实例
     */
    public void startPathfinding(AStarPathfinder pathfinder) {
        new Thread(pathfinder).start();
    }
}
