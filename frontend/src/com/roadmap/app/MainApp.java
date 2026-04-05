package com.roadmap.app;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Global User state
    private int currentUserId = -1;
    private String currentUserGoal = "";
    private String currentUserName = "";

    public MainApp() {
        setTitle("Student Career Roadmap Platform");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center screen

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add Panels
        mainPanel.add(new AuthPanel(this), "Auth");
        mainPanel.add(new DashboardPanel(this), "Dashboard");
        mainPanel.add(new RoadmapPanel(this), "Roadmap");
        mainPanel.add(new QuizPanel(this), "Quiz");
        mainPanel.add(new ProgressPanel(this), "Progress");

        add(mainPanel);
    }

    public void navigateTo(String screenName) {
        cardLayout.show(mainPanel, screenName);
        
        // Let the panel know it's being shown to refresh data
        for (Component comp : mainPanel.getComponents()) {
            if (comp.isVisible() && comp instanceof Refreshable) {
                ((Refreshable) comp).onShow();
            }
        }
    }

    // Getters and Setters for global state
    public void setSession(int userId, String name, String goal) {
        this.currentUserId = userId;
        this.currentUserName = name;
        this.currentUserGoal = goal;
    }
    
    public void setCurrentGoal(String goal) {
        this.currentUserGoal = goal;
    }

    public int getCurrentUserId() { return currentUserId; }
    public String getCurrentUserGoal() { return currentUserGoal; }
    public String getCurrentUserName() { return currentUserName; }

    public static void main(String[] args) {
        // Set nice Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}

// Interface for screens that need to load data when they become visible
interface Refreshable {
    void onShow();
}
