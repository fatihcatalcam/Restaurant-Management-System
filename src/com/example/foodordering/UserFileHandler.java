package com.example.foodordering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserFileHandler {
    private static final String USER_FILE = "src/data/users.txt";

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getFirstName() + "," + user.getLastName() + "," + user.getEmail() + "," +
                    user.getUsername() + "," + user.getPassword() + "," + (user.isAdmin() ? "1" : "0"));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Kullanıcı kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    public static User loadUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[3].equals(username) && parts[4].equals(password)) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    user.setAdmin(parts[5].equals("1"));
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("Kullanıcı yüklenirken hata oluştu: " + e.getMessage());
        }
        return null;
    }

	public static User loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
