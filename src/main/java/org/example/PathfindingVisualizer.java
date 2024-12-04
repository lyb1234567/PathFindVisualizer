package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主窗口类，负责设置用户界面
 */
public class PathfindingVisualizer extends JFrame {
    private GridPanel gridPanel;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> algorithmComboBox;
    private JLabel statusLabel;
    private JLabel visitedLabel;      // 显示已访问格子数
    private JLabel pathLengthLabel;   // 显示最短路径格子数

    public PathfindingVisualizer() {
        setTitle("PathFinder Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 850);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 初始化网格面板
        gridPanel = new GridPanel(20, 20);
        add(gridPanel, BorderLayout.CENTER);

        // 初始化控制面板
        JPanel controlPanel = new JPanel();
        startButton = new JButton("开始搜索");
        resetButton = new JButton("重置棋盘");
        algorithmComboBox = new JComboBox<>(new String[]{"A* 算法"});
        statusLabel = new JLabel("状态: 等待操作");
        visitedLabel = new JLabel("已访问格子数: 0");       // 初始化已访问格子数标签
        pathLengthLabel = new JLabel("最短路径格子数: 0"); // 初始化最短路径格子数标签

        // 添加组件到控制面板
        controlPanel.add(new JLabel("算法:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        controlPanel.add(statusLabel);
        controlPanel.add(visitedLabel);
        controlPanel.add(pathLengthLabel);

        add(controlPanel, BorderLayout.SOUTH);

        // 添加按钮监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                resetButton.setEnabled(false);
                visitedLabel.setText("已访问格子数: 0");       // 重置已访问计数
                pathLengthLabel.setText("最短路径格子数: 0"); // 重置路径长度计数
                gridPanel.startPathfinding(new AStarPathfinder(gridPanel, statusLabel, visitedLabel, pathLengthLabel, startButton, resetButton));
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridPanel.resetGrid();
                statusLabel.setText("状态: 棋盘已重置");
                visitedLabel.setText("已访问格子数: 0");       // 重置已访问计数
                pathLengthLabel.setText("最短路径格子数: 0"); // 重置路径长度计数
            }
        });
    }
}
