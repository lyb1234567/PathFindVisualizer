package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PathfindingVisualizer visualizer = new PathfindingVisualizer();
            visualizer.setVisible(true);
        });
    }
}
