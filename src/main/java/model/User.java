package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
