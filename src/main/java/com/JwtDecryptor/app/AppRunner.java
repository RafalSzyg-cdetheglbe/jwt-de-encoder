package com.JwtDecryptor.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.JwtDecryptor.app.JWTdecoder.decodeJWT;
import static com.JwtDecryptor.app.JWTgenerator.generateJWTToken;

public class AppRunner
{

    public static void main(String[] args) {
        JFrame frame = new JFrame("JWT Token Decoder/Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);



        JTextArea inputTextArea = new JTextArea(5, 40);

        JTextArea outputTextArea = new JTextArea(5, 40);
        outputTextArea.setEditable(false);

        JButton generateButton = new JButton("Generate JWT Token");
        JButton decodeButton = new JButton("Decode JWT");


        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String username = usernameField.getText();
                String jwtToken = generateJWTToken(email, username);
                inputTextArea.setText(jwtToken);
            }
        });


        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jwtToken = inputTextArea.getText().trim();
                String decodedPayload = decodeJWT(jwtToken);
                outputTextArea.setText(decodedPayload);
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(generateButton);
        buttonsPanel.add(decodeButton);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);

        panel.add(inputPanel, BorderLayout.EAST);
        panel.add(new JScrollPane(inputTextArea), BorderLayout.NORTH);
       panel.add(buttonsPanel, BorderLayout.CENTER);
       panel.add(new JScrollPane(outputTextArea), BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

}
