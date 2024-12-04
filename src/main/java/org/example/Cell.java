package org.example;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private int row;
    private int col;
    private boolean isStart = false;
    private boolean isEnd = false;
    private boolean isObstacle = false;
    private boolean isVisited = false;
    private boolean isPath = false;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    // 新增方法：检查是否为起点
    public boolean isStart() {
        return isStart;
    }

    // 新增方法：检查是否为终点
    public boolean isEnd() {
        return isEnd;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
        if (isStart) {
            this.isEnd = false;
            this.isObstacle = false;
        }
        updateColor();
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
        if (isEnd) {
            this.isStart = false;
            this.isObstacle = false;
        }
        updateColor();
    }

    public void toggleObstacle() {
        if (!isStart && !isEnd) {
            isObstacle = !isObstacle;
            updateColor();
        }
    }

    public void setVisited(boolean isVisited) {
        if (!isStart && !isEnd && !isObstacle) {
            this.isVisited = isVisited;
            updateColor();
        }
    }

    public void setPath(boolean isPath) {
        if (!isStart && !isEnd && !isObstacle) {
            this.isPath = isPath;
            updateColor();
        }
    }

    public void reset() {
        isStart = false;
        isEnd = false;
        isObstacle = false;
        isVisited = false;
        isPath = false;
        updateColor();
    }

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
