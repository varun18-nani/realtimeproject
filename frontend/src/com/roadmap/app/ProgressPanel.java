package com.roadmap.app;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class ProgressPanel extends JPanel implements Refreshable {

    private MainApp mainApp;
    private JProgressBar progressBar;
    private DefaultListModel<String> listModel;
    private JList<String> completedList;
    private JLabel progressLabel;

    public ProgressPanel(MainApp parent) {
        this.mainApp = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Learning Progress", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Create Progress Bar
        JPanel progressSection = new JPanel(new BorderLayout());
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 18));
        progressBar.setPreferredSize(new Dimension(300, 40));
        
        progressLabel = new JLabel("Total Completion: 0%", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        progressSection.add(progressLabel, BorderLayout.NORTH);
        progressSection.add(progressBar, BorderLayout.CENTER);
        centerPanel.add(progressSection, BorderLayout.NORTH);

        // Completed topics list
        JPanel listSection = new JPanel(new BorderLayout());
        listSection.add(new JLabel("Completed Topics:"), BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        completedList = new JList<>(listModel);
        completedList.setFont(new Font("Arial", Font.PLAIN, 14));
        listSection.add(new JScrollPane(completedList), BorderLayout.CENTER);

        centerPanel.add(listSection, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.addActionListener(e -> mainApp.navigateTo("Dashboard"));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        loadData();
    }

    private void loadData() {
        listModel.clear();
        progressBar.setValue(0);
        
        try {
            int userId = mainApp.getCurrentUserId();
            JSONObject response = ApiClient.get("/progress/" + userId);
            
            if (response.getInt("statusCode") == 200) {
                JSONArray data = response.getJSONArray("data_array");
                
                int completedCount = data.length();
                for (int i = 0; i < completedCount; i++) {
                    JSONObject item = data.getJSONObject(i);
                    listModel.addElement(item.getString("course") + " - 100%");
                }

                // Assume 6 total topics per roadmap for simple calculation
                int totalTopics = 6; 
                int percent = (int) (((double) completedCount / totalTopics) * 100);
                if (percent > 100) percent = 100;
                
                progressBar.setValue(percent);
                progressLabel.setText("Total Completion: " + percent + "%");
                
            } else {
                listModel.addElement("Failed to load progress data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            listModel.addElement("Error connecting to server.");
        }
    }
}
