package com.roadmap.app;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RoadmapPanel extends JPanel implements Refreshable {

    private MainApp mainApp;
    private JTable table;
    private DefaultTableModel tableModel;

    public RoadmapPanel(MainApp parent) {
        this.mainApp = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Personalized Roadmap & Schedule", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Day", "Topic", "Status"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnMarkDone = new JButton("Mark Topic as Done");
        JButton btnBack = new JButton("Back to Dashboard");

        btnMarkDone.addActionListener(e -> markSelectedAsDone());
        btnBack.addActionListener(e -> mainApp.navigateTo("Dashboard"));

        bottomPanel.add(btnMarkDone);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0); // Clear existing
        try {
            String goal = mainApp.getCurrentUserGoal();
            String encodedGoal = URLEncoder.encode(goal, StandardCharsets.UTF_8.toString());

            JSONObject response = ApiClient.get("/roadmap/" + encodedGoal);
            if (response.getInt("statusCode") == 200) {
                JSONObject data = response.getJSONObject("data");
                JSONArray schedule = data.getJSONArray("schedule");

                for (int i = 0; i < schedule.length(); i++) {
                    JSONObject item = schedule.getJSONObject(i);
                    tableModel.addRow(new Object[]{
                            "Day " + item.getInt("day"),
                            item.getString("topic"),
                            item.getString("status")
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load roadmap.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markSelectedAsDone() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String topic = (String) tableModel.getValueAt(selectedRow, 1);
            
            // Send progress to backend
            JSONObject payload = new JSONObject();
            payload.put("user_id", mainApp.getCurrentUserId());
            payload.put("course", topic);
            payload.put("progress", 100);

            JSONObject response = ApiClient.post("/progress", payload);
            if(response.getInt("statusCode") == 200) {
                 tableModel.setValueAt("done", selectedRow, 2);
                 JOptionPane.showMessageDialog(this, "Progress updated for: " + topic);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a topic first.");
        }
    }
}
