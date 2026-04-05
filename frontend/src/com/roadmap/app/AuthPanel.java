package com.roadmap.app;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class AuthPanel extends JPanel {

    private MainApp mainApp;
    
    // Login components
    private JTextField loginEmailField = new JTextField(15);
    private JPasswordField loginPasswordField = new JPasswordField(15);
    
    // Register components
    private JTextField regNameField = new JTextField(15);
    private JTextField regEmailField = new JTextField(15);
    private JPasswordField regPasswordField = new JPasswordField(15);
    private JComboBox<String> regGoalCombo = new JComboBox<>(new String[]{"Software Developer", "Data Scientist", "Web Developer", "Android Developer"});

    public AuthPanel(MainApp parent) {
        this.mainApp = parent;
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Login Panel construction
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; loginPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; loginPanel.add(loginEmailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; loginPanel.add(loginPasswordField, gbc);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; loginPanel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> doLogin());

        // Register Panel construction
        JPanel regPanel = new JPanel(new GridBagLayout());
        regPanel.setBorder(BorderFactory.createTitledBorder("Register"));
        GridBagConstraints gbcReg = new GridBagConstraints();
        gbcReg.insets = new Insets(10, 10, 10, 10);
        gbcReg.fill = GridBagConstraints.HORIZONTAL;

        gbcReg.gridx = 0; gbcReg.gridy = 0; regPanel.add(new JLabel("Name:"), gbcReg);
        gbcReg.gridx = 1; gbcReg.gridy = 0; regPanel.add(regNameField, gbcReg);

        gbcReg.gridx = 0; gbcReg.gridy = 1; regPanel.add(new JLabel("Email:"), gbcReg);
        gbcReg.gridx = 1; gbcReg.gridy = 1; regPanel.add(regEmailField, gbcReg);
        
        gbcReg.gridx = 0; gbcReg.gridy = 2; regPanel.add(new JLabel("Password:"), gbcReg);
        gbcReg.gridx = 1; gbcReg.gridy = 2; regPanel.add(regPasswordField, gbcReg);
        
        gbcReg.gridx = 0; gbcReg.gridy = 3; regPanel.add(new JLabel("Career Goal:"), gbcReg);
        gbcReg.gridx = 1; gbcReg.gridy = 3; regPanel.add(regGoalCombo, gbcReg);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(39, 174, 96));
        btnRegister.setForeground(Color.WHITE);
        gbcReg.gridx = 0; gbcReg.gridy = 4; gbcReg.gridwidth = 2; regPanel.add(btnRegister, gbcReg);

        btnRegister.addActionListener(e -> doRegister());

        centerPanel.add(loginPanel);
        centerPanel.add(regPanel);

        JLabel title = new JLabel("Student Career Roadmap Platform", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void doLogin() {
        JSONObject payload = new JSONObject();
        payload.put("email", loginEmailField.getText());
        payload.put("password", new String(loginPasswordField.getPassword()));

        JSONObject response = ApiClient.post("/login", payload);
        if (response.getInt("statusCode") == 200) {
            JSONObject user = response.getJSONObject("data").getJSONObject("user");
            mainApp.setSession(user.getInt("id"), user.getString("name"), user.getString("goal"));
            mainApp.navigateTo("Dashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doRegister() {
        JSONObject payload = new JSONObject();
        payload.put("name", regNameField.getText());
        payload.put("email", regEmailField.getText());
        payload.put("password", new String(regPasswordField.getPassword()));
        payload.put("goal", regGoalCombo.getSelectedItem().toString());

        JSONObject response = ApiClient.post("/register", payload);
        if (response.getInt("statusCode") == 200) {
            JOptionPane.showMessageDialog(this, "Registration Successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
