package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PathfindingVisualizer extends JFrame {
    private GridPanel gridPanel;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> algorithmComboBox;
    private JLabel statusLabel;

    public PathfindingVisualizer() {
        setTitle("最短路径算法可视化");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 850);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gridPanel = new GridPanel(20, 20);
        add(gridPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        startButton = new JButton("开始搜索");
        resetButton = new JButton("重置棋盘");
        algorithmComboBox = new JComboBox<>(new String[]{"A* 算法"});
        statusLabel = new JLabel("状态: 等待操作");

        controlPanel.add(new JLabel("算法:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(startButton);
        controlPanel.add(resetButton);
        controlPanel.add(statusLabel);

        add(controlPanel, BorderLayout.SOUTH);

        // 添加按钮监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                resetButton.setEnabled(false);
                gridPanel.startPathfinding(new AStarPathfinder(gridPanel, statusLabel, startButton, resetButton));
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridPanel.resetGrid();
                statusLabel.setText("状态: 棋盘已重置");
            }
        });
    }
}
