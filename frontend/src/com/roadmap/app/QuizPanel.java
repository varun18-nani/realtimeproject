package com.roadmap.app;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QuizPanel extends JPanel implements Refreshable {

    private MainApp mainApp;
    private JPanel questionsPanel;
    private ArrayList<ButtonGroup> answerGroups;
    private ArrayList<String[]> correctAnswers;
    private JButton btnSubmit;

    public QuizPanel(MainApp parent) {
        this.mainApp = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Evaluation Quiz", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        btnSubmit = new JButton("Submit Quiz");
        JButton btnBack = new JButton("Back to Dashboard");

        btnSubmit.addActionListener(e -> evaluateQuiz());
        btnBack.addActionListener(e -> mainApp.navigateTo("Dashboard"));

        bottomPanel.add(btnSubmit);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onShow() {
        loadData();
    }

    private void loadData() {
        questionsPanel.removeAll();
        answerGroups = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        btnSubmit.setEnabled(true);

        try {
            String goal = mainApp.getCurrentUserGoal();
            String encodedGoal = URLEncoder.encode(goal, StandardCharsets.UTF_8.toString());

            JSONObject response = ApiClient.get("/quiz/" + encodedGoal);
            if (response.getInt("statusCode") == 200) {
                JSONArray questions = response.getJSONArray("data_array");

                for (int i = 0; i < questions.length(); i++) {
                    JSONObject q = questions.getJSONObject(i);
                    String questionText = q.getString("question");
                    JSONArray options = q.getJSONArray("options");
                    String answer = q.getString("answer");

                    JPanel qPanel = new JPanel(new BorderLayout());
                    qPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 20, 10, 20),
                            BorderFactory.createEtchedBorder()
                    ));

                    JLabel lblQ = new JLabel("Q" + (i + 1) + ". " + questionText);
                    lblQ.setFont(new Font("Arial", Font.BOLD, 14));
                    qPanel.add(lblQ, BorderLayout.NORTH);

                    JPanel optionsPanel = new JPanel();
                    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
                    ButtonGroup bg = new ButtonGroup();
                    
                    for (int j = 0; j < options.length(); j++) {
                        JRadioButton radio = new JRadioButton(options.getString(j));
                        bg.add(radio);
                        optionsPanel.add(radio);
                    }

                    qPanel.add(optionsPanel, BorderLayout.CENTER);
                    questionsPanel.add(qPanel);

                    answerGroups.add(bg);
                    correctAnswers.add(new String[]{answer}); // Store in array since Java lambda needs effectively final
                }
            } else {
                questionsPanel.add(new JLabel("Failed to load quiz."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        revalidate();
        repaint();
    }

    private void evaluateQuiz() {
        int score = 0;
        int total = correctAnswers.size();

        for (int i = 0; i < total; i++) {
            ButtonGroup bg = answerGroups.get(i);
            String correct = correctAnswers.get(i)[0];
            
            String selected = null;
            for (java.util.Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selected = button.getText();
                    break;
                }
            }

            if (selected != null && selected.equals(correct)) {
                score++;
            }
        }

        JOptionPane.showMessageDialog(this, "You scored " + score + " out of " + total + "!", "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        btnSubmit.setEnabled(false);
    }
}
