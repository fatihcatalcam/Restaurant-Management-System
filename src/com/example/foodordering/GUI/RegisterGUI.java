package com.example.foodordering.GUI;

import javax.swing.*;

import com.example.foodordering.User;
import com.example.foodordering.UserFileHandler;

import java.awt.*;
import java.awt.event.*;

public class RegisterGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterGUI() {
        setTitle("Doy Doy - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        emailField = new JTextField(10);
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        JButton registerButton = new JButton("Register");
        

        customizeButton(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Please enter your First name.", "Name Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                
                if (lastName.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Please enter your Last name.", "Name Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Please enter your Email.", "Email Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Please enter your username.", "username Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                

                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Please enter a password.", "Password Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

            
                User newUser = new User(firstName, lastName, email, username, password);
                UserFileHandler.saveUser(newUser);

                openLoginPage();
            }
        });


        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        setLocationRelativeTo(null);
    }

    private void openLoginPage() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginGUI loginPage = new LoginGUI();
                loginPage.setVisible(true);
                dispose();
            }
        });
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
