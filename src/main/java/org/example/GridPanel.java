package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {
    private int rows;
    private int cols;
    private Cell[][] grid;
    private Cell startCell = null;
    private Cell endCell = null;

    public GridPanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setLayout(new GridLayout(rows, cols));
        grid = new Cell[rows][cols];
        initializeGrid();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 处理单元格点击事件
                Cell cell = (Cell) getComponentAt(e.getPoint());
                if (cell != null) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
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
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        // 设置障碍物
                        cell.toggleObstacle();
                    }
                }
            }
        });
    }

    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = new Cell(row, col);
                grid[row][col] = cell;
                add(cell);
            }
        }
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void resetGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col].reset();
            }
        }
        startCell = null;
        endCell = null;
    }

    public void startPathfinding(AStarPathfinder pathfinder) {
        new Thread(pathfinder).start();
    }
}
