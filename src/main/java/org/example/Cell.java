package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * 格子类，表示网格中的每个格子
 */
public class Cell extends JPanel {
    private int row;
    private int col;
    private boolean isStart = false;
    private boolean isEnd = false;
    private boolean isObstacle = false;
    private boolean isVisited = false; // 标志，表示格子是否已被访问
    private boolean isPath = false;     // 标志，表示格子是否在最终路径上

    /**
     * 构造方法
     *
     * @param row 行号
     * @param col 列号
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    // 获取行号
    public int getRow() {
        return row;
    }

    // 获取列号
    public int getCol() {
        return col;
    }

    // 是否为障碍物
    public boolean isObstacle() {
        return isObstacle;
    }

    // 检查格子是否已被访问
    public boolean isVisited() {
        return isVisited;
    }

    // 设置格子为已访问
    public void setVisited(boolean isVisited) {
        if (!isStart && !isEnd && !isObstacle) {
            this.isVisited = isVisited;
            updateColor();
        }
    }

    // 检查是否为起点
    public boolean isStart() {
        return isStart;
    }

    // 检查是否为终点
    public boolean isEnd() {
        return isEnd;
    }

    // 设置起点
    public void setStart(boolean isStart) {
        this.isStart = isStart;
        if (isStart) {
            this.isEnd = false;
            this.isObstacle = false;
            this.isVisited = false; // 重置访问状态
            this.isPath = false;     // 重置路径状态
        }
        updateColor();
    }

    // 设置终点
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
        if (isEnd) {
            this.isStart = false;
            this.isObstacle = false;
            this.isVisited = false; // 重置访问状态
            this.isPath = false;     // 重置路径状态
        }
        updateColor();
    }

    // 切换障碍物状态
    public void toggleObstacle() {
        if (!isStart && !isEnd) {
            isObstacle = !isObstacle;
            if (isObstacle) {
                isVisited = false; // 如果成为障碍物，重置访问状态
                isPath = false;     // 如果成为障碍物，重置路径状态
            }
            updateColor();
        }
    }

    // 设置路径状态
    public void setPath(boolean isPath) {
        if (!isStart && !isEnd && !isObstacle) {
            this.isPath = isPath;
            updateColor();
        }
    }

    // 重置格子状态
    public void reset() {
        isStart = false;
        isEnd = false;
        isObstacle = false;
        isVisited = false;
        isPath = false;
        updateColor();
    }

    // 更新格子的颜色
    private void updateColor() {
        if (isStart) {
            setBackground(Color.GREEN);
        } else if (isEnd) {
            setBackground(Color.RED);
        } else if (isObstacle) {
            setBackground(Color.BLACK);
        } else if (isPath) {
            setBackground(Color.YELLOW);
        } else if (isVisited) {
            setBackground(Color.CYAN);
        } else {
            setBackground(Color.WHITE);
        }
        repaint();
    }
}
