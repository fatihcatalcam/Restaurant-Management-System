package com.example.foodordering;

import javax.swing.*;

import com.example.foodordering.GUI.LoginGUI;
import com.example.foodordering.GUI.MainMenuGUI;

public class Main {
    private static User currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
        MainMenuGUI mainMenuGUI = new MainMenuGUI(currentUser);
        mainMenuGUI.setVisible(true);
    }
}

