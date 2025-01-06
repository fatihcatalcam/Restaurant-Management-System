package com.example.foodordering.GUI;

import javax.swing.*;
import com.example.foodordering.User;
import com.example.foodordering.UserFileHandler;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User currentUser;
    private static final long serialVersionUID = 1L;

    public LoginGUI() {""
        setTitle("Doy Doy - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel titleLabel = new JLabel("Doy Doy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        customizeButton(loginButton);
        customizeButton(registerButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean loginSuccessful = performLogin(username, password);

            if (loginSuccessful) {
                openMainMenu();
            } else {
                JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> openRegisterPage());

        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        setLocationRelativeTo(null);
    }

    private boolean performLogin(String username, String password) {
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(LoginGUI.this, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        User user = UserFileHandler.loadUser(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        } else {
            JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void openMainMenu() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenuGUI mainMenu = new MainMenuGUI(currentUser);
                mainMenu.setVisible(true);
                dispose();
            }
        });
    }

    private void openRegisterPage() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RegisterGUI registerPage = new RegisterGUI();
                registerPage.setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            }
        });
    }
}
