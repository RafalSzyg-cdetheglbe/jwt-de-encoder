package com.JwtDecryptor.app;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.JwtDecryptor.app.JWTdecoder.decodeJWT;
import static com.JwtDecryptor.app.JWTgenerator.generateJWTToken;

public class AppRunner {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("JWT Token Decoder/Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField(20);

        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(roleLabel);
        inputPanel.add(roleField);

        JTextArea inputTextArea = new JTextArea(5, 40);
        JTextPane outputTextArea = new JTextPane();
        outputTextArea.setEditable(false);

        JButton generateButton = new JButton("Generate JWT Token");
        JButton decodeButton = new JButton("Decode JWT");
        JButton clearButton = new JButton("Clear Output");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String role = roleField.getText();
                String jwtToken = generateJWTToken(email, role);
                inputTextArea.setText(jwtToken);
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jwtToken = inputTextArea.getText().trim();
                String decodedPayload = decodeJWT(jwtToken);
                displayColorizedPayload(decodedPayload, outputTextArea);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputTextArea.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(clearButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(outputTextArea), BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void displayColorizedPayload(String payload, JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet redStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(redStyle, Color.RED);

        SimpleAttributeSet blueStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(blueStyle, Color.BLUE);

        Pattern algPattern = Pattern.compile("\"alg\":\\s*\"([^\"]+)\"");
        Matcher matcher = algPattern.matcher(payload);

        try {
            int currentIndex = 0;

            while (matcher.find()) {
                int start = matcher.start() - 1;
                int end = matcher.end() + 1;
                doc.insertString(currentIndex, payload.substring(currentIndex, start), blueStyle);
                doc.insertString(currentIndex + start, payload.substring(start, end), redStyle);
                currentIndex = end;
            }

            doc.insertString(currentIndex, payload.substring(currentIndex), blueStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}