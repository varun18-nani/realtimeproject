package com.roadmap.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class DashboardPanel extends JPanel implements Refreshable {

    private MainApp mainApp;
    private JLabel welcomeLabel;
    private JLabel goalLabel;

    public DashboardPanel(MainApp parent) {
        this.mainApp = parent;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 250)); // Light modern background

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(40, 44, 52));

        goalLabel = new JLabel("Current Goal: ", SwingConstants.CENTER);
        goalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        goalLabel.setForeground(new Color(100, 108, 120));

        headerPanel.add(welcomeLabel);
        headerPanel.add(goalLabel);

        // Center Panel with 3D Animated Cards
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 50));
        centerPanel.setOpaque(false);

        // Create Animated Cards
        AnimatedCard btnRoadmap = new AnimatedCard("Roadmap & Schedule", "Track your daily milestones", new Color(41, 128, 185));
        AnimatedCard btnQuiz = new AnimatedCard("Skill Evaluation", "Test your knowledge", new Color(39, 174, 96));
        AnimatedCard btnProgress = new AnimatedCard("My Progress", "View completion metrics", new Color(142, 68, 173));

        btnRoadmap.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { mainApp.navigateTo("Roadmap"); }
        });
        btnQuiz.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { mainApp.navigateTo("Quiz"); }
        });
        btnProgress.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { mainApp.navigateTo("Progress"); }
        });

        centerPanel.add(btnRoadmap);
        centerPanel.add(btnQuiz);
        centerPanel.add(btnProgress);

        // Bottom panel for logout
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 30));
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> mainApp.navigateTo("Auth"));
        
        bottomPanel.add(btnLogout);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        welcomeLabel.setText("Welcome, " + mainApp.getCurrentUserName() + "!");
        goalLabel.setText("Current Career Focus: " + mainApp.getCurrentUserGoal());
    }

    /**
     * Custom component that animates a 3D elevation effect when hovered.
     */
    private class AnimatedCard extends JPanel {
        private String title;
        private String subtitle;
        private Color themeColor;
        
        private int elevation = 5; // Initial shadow/elevation depth
        private Timer hoverTimer;
        private boolean isHovered = false;

        public AnimatedCard(String title, String subtitle, Color themeColor) {
            this.title = title;
            this.subtitle = subtitle;
            this.themeColor = themeColor;
            
            setPreferredSize(new Dimension(220, 160));
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Animation Timer (60 fps)
            hoverTimer = new Timer(16, e -> {
                if(isHovered && elevation < 15) {
                    elevation += 2;
                    repaint();
                } else if (!isHovered && elevation > 5) {
                    elevation -= 2;
                    repaint();
                } else {
                    hoverTimer.stop();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    hoverTimer.start();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    hoverTimer.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth() - 20;
            int height = getHeight() - 20;
            int x = 10;
            // The Y coordinate shifts based on elevation to simulate lifting
            int y = 15 - elevation/2;

            // 1. Draw 3D Shadow
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fill(new RoundRectangle2D.Float(x, y + elevation, width, height, 20, 20));

            // 2. Draw Card Main Body
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(x, y, width, height, 20, 20));
            
            // Draw gradient top accent line
            GradientPaint gp = new GradientPaint(x, y, themeColor.brighter(), x + width, y, themeColor.darker());
            g2.setPaint(gp);
            g2.fill(new RoundRectangle2D.Float(x, y, width, 8, 20, 20));
            // Fill underneath corners to make it flat at bottom of the accent
            g2.fillRect(x, y + 4, width, 4);

            // 3. Draw Text
            g2.setColor(new Color(50, 50, 50));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            FontMetrics fm = g2.getFontMetrics();
            int titleWidth = fm.stringWidth(title);
            g2.drawString(title, x + (width - titleWidth) / 2, y + 70);

            g2.setColor(new Color(120, 120, 120));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            fm = g2.getFontMetrics();
            int subWidth = fm.stringWidth(subtitle);
            g2.drawString(subtitle, x + (width - subWidth) / 2, y + 100);

            g2.dispose();
        }
    }
}
